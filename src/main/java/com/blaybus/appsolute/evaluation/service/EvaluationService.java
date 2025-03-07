package com.blaybus.appsolute.evaluation.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.evaluation.domain.entity.Evaluation;
import com.blaybus.appsolute.evaluation.domain.entity.EvaluationGrade;
import com.blaybus.appsolute.evaluation.domain.request.CreateEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.request.DeleteEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.request.ReadEvaluationRequest;
import com.blaybus.appsolute.evaluation.domain.response.ReadEvaluationResponse;
import com.blaybus.appsolute.evaluation.domain.type.GradeType;
import com.blaybus.appsolute.evaluation.domain.type.PeriodType;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationGradeRepository;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationRepository;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import com.blaybus.appsolute.fcm.service.MessageService;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EvaluationService {

    private final JpaEvaluationRepository evaluationRepository;
    private final JpaEvaluationGradeRepository evaluationGradeRepository;
    private final JpaUserRepository userRepository;
    private final MessageService messageService;
    private final FcmTokenService tokenService;

    public void createOrUpdateEvaluation(CreateEvaluationRequest request) {
        Integer year = LocalDateTime.now().getYear();
        GradeType gradeType = GradeType.valueOf(request.grade().replace("등급", ""));
        String employeeNumber = request.employeeNumber();
        PeriodType period = PeriodType.valueOf(request.period());
        String notes = request.notes();
        String periodString;

        if(period == PeriodType.SECOND_HALF) {
            periodString = "하반기";
        } else {
            periodString = "상반기";
        }

        User user = userRepository.findByEmployeeNumber(employeeNumber)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        EvaluationGrade evaluationGrade = evaluationGradeRepository.findByEvaluationGradeName(gradeType)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 등급이 없습니다.", 404, LocalDateTime.now())
                ));

        Evaluation evaluation = evaluationRepository.findByUserAndYearAndPeriodType(user, year, period)
                        .orElseGet(() -> evaluationRepository.save(Evaluation.builder()
                                .year(year)
                                .user(user)
                                .periodType(period)
                                .evaluationGrade(evaluationGrade)
                                .notes(notes)
                                .build()));

        evaluation.updateEvaluationGrade(evaluationGrade);

        List<ReadFcmTokenResponse> tokenList = tokenService.getFcmTokens(user.getId());

        for(ReadFcmTokenResponse token : tokenList) {
            messageService.sendMessageTo(user, token.fcmToken(), "경험치를 획득하였습니다.",
                    year + "년도" + periodString + "인사평가로 " + evaluation.getEvaluationGrade().getEvaluationGradePoint() + "경험치를 획득하였습니다", null);
        }
    }

    public void deleteEvaluation(DeleteEvaluationRequest request) {

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 사용자가 없습니다.", 404, LocalDateTime.now())
                        ));

        evaluationRepository.deleteByUserAndYearAndPeriodType(user, LocalDateTime.now().getYear(), request.period());
    }

    public ReadEvaluationResponse getEvaluation(Long userId, ReadEvaluationRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        Evaluation evaluation = evaluationRepository.findByUserAndYearAndPeriodType(user, request.year(), request.periodType())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저의 인사평가가 없습니다.", 404, LocalDateTime.now())
                ));

        return ReadEvaluationResponse.fromEvaluationGrade(evaluation.getEvaluationGrade());
    }
}
