package com.blaybus.appsolute.user.service;

import com.blaybus.appsolute.character.domain.entity.Characters;
import com.blaybus.appsolute.character.repository.JpaCharacterRepository;
import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.commons.jwt.JWTUtil;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentGroupQuestRepository;
import com.blaybus.appsolute.evaluation.domain.entity.Evaluation;
import com.blaybus.appsolute.evaluation.repository.JpaEvaluationRepository;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.level.repository.JpaLevelRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.domain.request.*;
import com.blaybus.appsolute.user.domain.response.LoginUserResponse;
import com.blaybus.appsolute.user.domain.response.ReadUserResponse;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import com.blaybus.appsolute.xp.repository.JpaXpRepository;
import com.blaybus.appsolute.xp.repository.JpaXpDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaLevelRepository levelRepository;
    private final JpaDepartmentGroupQuestRepository departmentGroupQuestRepository;
    private final UserSheetService userSheetService;
    private final JWTUtil jwtUtil;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        List<DepartmentGroupQuest> departmentGroupQuestList = departmentGroupQuestRepository.findByDepartmentGroup(user.getDepartmentGroup());

        long lastYearXpPoint = 0;

        List<Xp> lastYearXp = xpList.stream()
                .filter(xp -> xp.getYear() <= now.getYear() -1)
                .toList();

        for(Xp xp : lastYearXp) {
            List<XpDetail> xpDetailList = xpDetailRepository.findByXp(xp);

            for(XpDetail xpDetail : xpDetailList) {
                lastYearXpPoint += xpDetail.getPoint();
            }
        }

        List<Evaluation> thisYearEvaluation = evaluationList.stream()
                .filter(evaluation -> evaluation.getYear() == now.getYear())
                .toList();

        List<DepartmentGroupQuest> thisYearDepartmentGroupQuest = departmentGroupQuestList.stream()
                .filter(departmentGroupQuest -> departmentGroupQuest.getYear() == now.getYear())
                .toList();

        long thisYearTotalXpPoint = 0;
        long thisYearEvaluationXpPoint = 0;
        long thisYearDepartmentGroupQuestXpPoint = 0;

        for(Evaluation evaluation : thisYearEvaluation) {
            thisYearEvaluationXpPoint += evaluation.getEvaluationGrade().getEvaluationGradePoint();
        }

        for(DepartmentGroupQuest thisDepartmentGroupQuest : thisYearDepartmentGroupQuest) {
            if(thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.ONGOING) {
                thisYearDepartmentGroupQuestXpPoint += thisDepartmentGroupQuest.getMediumPoint();
            } else if (thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.COMPLETED) {
                thisYearDepartmentGroupQuestXpPoint += thisDepartmentGroupQuest.getMaxPoint();
            }
        }

        thisYearTotalXpPoint += thisYearEvaluationXpPoint + thisYearDepartmentGroupQuestXpPoint;
        long totalXP = lastYearXpPoint + thisYearTotalXpPoint;
        Level tempLevel = user.getLevel();
        long remainXP = tempLevel.getMaxPoint() + 1 - totalXP;
        boolean isLastLevel = false;

        //(레벨 최대 경험치+1) - 지금까지 얻은 총 경험치 = 남은 경험치
        while((tempLevel.getMaxPoint() + 1) <= totalXP) {
            tempLevel = tempLevel.getNextLevel();
            remainXP = tempLevel.getMaxPoint() + 1 - totalXP;
        }

        if(tempLevel.getNextLevel() == null) {
            remainXP = 0;
            isLastLevel = true;
        }


        return ReadUserResponse.from(user, lastYearXpPoint, thisYearTotalXpPoint, remainXP, thisYearEvaluationXpPoint, thisYearDepartmentGroupQuestXpPoint, isLastLevel, totalXP);
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

    public void deleteUser(DeleteUserRequest request) {
        userRepository.deleteByEmployeeNumber(request.employeeNumber());
    }

    public void createOrUpdateUser(CreateUserRequest request) {

        Level level = levelRepository.findByLevelName(request.level())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 레벨이 없습니다.", 404, LocalDateTime.now())
                ));

        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(request.departmentName(), request.departmentGroupName())
                .orElseGet(() -> departmentGroupRepository.save(
                        DepartmentGroup.builder()
                                .departmentGroupName(request.departmentGroupName())
                                .departmentName(request.departmentName())
                                .build()
                ));

        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                        .orElseGet(() -> {
                            try {
                                return userRepository.save(User.builder()
                                        .employeeNumber(request.employeeNumber())
                                        .userName(request.employeeName())
                                        .joiningDate(sdf.parse(request.joiningDate()))
                                        .departmentGroup(departmentGroup)
                                        .level(level)
                                        .userId(request.userId())
                                        .initialPassword(request.initialPassword())
                                        .characters(null)
                                        .build());
                            } catch (ParseException e) {
                                throw new ApplicationException(
                                        ErrorStatus.toErrorStatus("알 수 없는 오류가 발생하였습니다.", 500, LocalDateTime.now())
                                );
                            }
                        });

        user.updateEmployeeNumber(request.employeeNumber());
        user.updateUserName(request.employeeName());
        user.updateLevel(level);
        user.updateDepartmentGroup(departmentGroup);
        try {
            user.updateJoiningDate(sdf.parse(request.joiningDate()));
        } catch (ParseException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("알 수 없는 오류가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
        user.updateInitialPassword(request.initialPassword());
        user.updateUserId(request.userId());
    }

    public void updateLevelParam(UpdateLevelRequest request) {
        User user = userRepository.findByEmployeeNumber(request.employeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        Level level = levelRepository.findByLevelName(request.levelName())
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 레벨이 없습니다.", 404, LocalDateTime.now())
                        ));

        user.updateLevel(level);
    }

    //매년 1월 1일 새벽 4시에 레벨 업데이트
    @Scheduled(cron = "0 0 4 1 1 *")
    public void updateLevel() {

        LocalDateTime now = LocalDateTime.now();

        List<User> userList = userRepository.findAll();

        for(User user : userList) {
            List<Xp> xpList = xpRepository.findByUser_Id(user.getId());
            List<Evaluation> evaluationList = evaluationRepository.findByUser(user);
            List<DepartmentGroupQuest> departmentGroupQuestList = departmentGroupQuestRepository.findByDepartmentGroup(user.getDepartmentGroup());
            Level level = user.getLevel();

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

            for(DepartmentGroupQuest thisDepartmentGroupQuest : departmentGroupQuestList) {
                if(thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.ONGOING) {
                    lastYearXpPoint += thisDepartmentGroupQuest.getMediumPoint();
                } else if (thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.COMPLETED) {
                    lastYearXpPoint += thisDepartmentGroupQuest.getMaxPoint();
                }
            }

            while(lastYearXpPoint < level.getMaxPoint()) {
                level = level.getNextLevel();
            }

            user.updateLevel(level);
        }
    }

    //1월 1일 새벽 5시에 작년 관련 xp를 테이블로 옮깁니다.
    @Scheduled(cron = "0 0 5 1 1 *")
    public void updateXp() {

        LocalDateTime now = LocalDateTime.now();

        List<User> userList = userRepository.findAll();

        for(User user : userList) {
            List<Evaluation> evaluationList = evaluationRepository.findByUser(user);
            List<DepartmentGroupQuest> departmentGroupQuestList = departmentGroupQuestRepository.findByDepartmentGroup(user.getDepartmentGroup());

            long lastYearXpPoint = 0;

            List<Evaluation> lastYearEvaluation = evaluationList.stream()
                    .filter(evaluation -> evaluation.getYear() == now.getYear() -1)
                    .toList();

            List<DepartmentGroupQuest> departmentGroupQuest = departmentGroupQuestList.stream()
                    .filter(xp -> xp.getYear() == now.getYear() -1)
                    .toList();


            for(Evaluation lastEvaluation : lastYearEvaluation) {
                lastYearXpPoint += lastEvaluation.getEvaluationGrade().getEvaluationGradePoint();
            }

            for(DepartmentGroupQuest thisDepartmentGroupQuest : departmentGroupQuest) {
                if(thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.ONGOING) {
                    lastYearXpPoint += thisDepartmentGroupQuest.getMediumPoint();
                } else if (thisDepartmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.COMPLETED) {
                    lastYearXpPoint += thisDepartmentGroupQuest.getMaxPoint();
                }
            }

            Xp xp = Xp.builder()
                    .user(user)
                    .year(now.getYear()-1)
                    .build();

            xpRepository.save(xp);

            XpDetail xpDetail = XpDetail.builder()
                    .createdAt(LocalDateTime.now())
                    .xp(xp)
                    .point(lastYearXpPoint)
                    .build();

            xpDetailRepository.save(xpDetail);
        }
    }
}
