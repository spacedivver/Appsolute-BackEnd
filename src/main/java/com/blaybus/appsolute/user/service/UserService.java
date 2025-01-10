package com.blaybus.appsolute.user.service;

import com.blaybus.appsolute.character.domain.entity.Characters;
import com.blaybus.appsolute.character.repository.JpaCharacterRepository;
import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.commons.jwt.JWTUtil;
import com.blaybus.appsolute.evaluation.domain.entity.Evaluation;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.domain.request.LoginUserRequest;
import com.blaybus.appsolute.user.domain.request.UpdateCharacterRequest;
import com.blaybus.appsolute.user.domain.request.UpdatePasswordRequest;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import com.blaybus.appsolute.xp.repository.JpaXpRepository;
import com.blaybus.appsolute.xp.repository.JpaXpDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JpaUserRepository userRepository;
    private final JpaXpRepository xpRepository;
    private final JpaXpDetailRepository xpDetailRepository;
    private final JpaCharacterRepository characterRepository;
    private final JpaEvaluationRepository evaluationRepository;
    private final UserSheetService userSheetService;
    private final JWTUtil jwtUtil;

    public LoginUserResponse login(LoginUserRequest request) {

        Optional<User> userOptional = userRepository.findByUserIdAndChangedPassword(request.userId(), request.password());

        if(userOptional.isPresent()) {
            return LoginUserResponse.builder()
                    .jwtToken(jwtUtil.createJwt(userOptional.get().getId()))
                    .firstLogin(false)
                    .build();
        }

        User user = userRepository.findByUserIdAndInitialPassword(request.userId(), request.password())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("일치하지 않는 비밀번호입니다.", 404, LocalDateTime.now())
                ));

        if(!user.getChangedPassword().isEmpty()) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("일치하지 않는 비밀번호입니다.", 404, LocalDateTime.now())
            );
        }

        return LoginUserResponse.builder()
                .jwtToken(jwtUtil.createJwt(user.getId()))
                .firstLogin(true)
                .build();
    }

    @Transactional(readOnly = true)
    public ReadUserResponse getUserById(Long id) {

        LocalDateTime now = LocalDateTime.now();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        List<Xp> xpList = xpRepository.findByUser_Id(id);
        List<Evaluation> evaluationList = evaluationRepository.findByUser(user);

        long lastYearXpPoint = 0;

        List<Xp> lastYearXp = xpList.stream()
                .filter(xp -> xp.getYear() <= now.getYear() -1)
                .toList();

        List<Evaluation> lastYearEvaluation = evaluationList.stream()
                .filter(evaluation -> evaluation.getYear() <= now.getYear() -1)
                .toList();

        for(Xp xp : lastYearXp) {
            List<XpDetail> xpDetailList = xpDetailRepository.findByXp(xp);

            for(XpDetail xpDetail : xpDetailList) {
                lastYearXpPoint += xpDetail.getPoint();
            }
        }

        for(Evaluation lastEvaluation : lastYearEvaluation) {
            lastYearXpPoint += lastEvaluation.getEvaluationGrade().getEvaluationGradePoint();
        }


        Xp thisYearXp = xpList.stream()
                .filter(xp -> xp.getYear() == now.getYear())
                .findFirst()
                .orElse(null);

        List<Evaluation> thisYearEvaluation = evaluationList.stream()
                .filter(evaluation -> evaluation.getYear() == now.getYear())
                .toList();

        long thisYearXpPoint = 0;

        if(thisYearXp != null) {
            List<XpDetail> xpDetailList = xpDetailRepository.findByXp(thisYearXp);

            for(XpDetail xpDetail : xpDetailList) {
                thisYearXpPoint += xpDetail.getPoint();
            }
        }

        for(Evaluation evaluation : thisYearEvaluation) {
            thisYearXpPoint += evaluation.getEvaluationGrade().getEvaluationGradePoint();
        }

        return ReadUserResponse.from(user, lastYearXpPoint, thisYearXpPoint);
    }

    public void updatePassword(Long id, UpdatePasswordRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        user.updatePassword(request.password());

        userSheetService.updateSpreadsheetPassword(user.getEmployeeNumber(), request.password());
    }

    public void updateCharacter(Long id, UpdateCharacterRequest request) {

        Characters newCharacters = characterRepository.findById(request.characterId())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 캐릭터가 없습니다.", 404, LocalDateTime.now())
                ));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 회원이 없습니다.", 404, LocalDateTime.now())
                ));

        user.updateCharacters(newCharacters);
    }
}
