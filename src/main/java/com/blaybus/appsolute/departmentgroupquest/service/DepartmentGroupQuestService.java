package com.blaybus.appsolute.departmentgroupquest.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentQuestDetail;
import com.blaybus.appsolute.departmentgroupquest.domain.request.UpdateDepartQuestDetailRequest;
import com.blaybus.appsolute.departmentgroupquest.domain.request.UpdateDepartmentGroupQuestRequest;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartQuestDetailResponse;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentQuestDetailResponse;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentGroupQuestRepository;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentQuestDetailRepository;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import com.blaybus.appsolute.fcm.service.MessageService;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentGroupQuestService {

    private final JpaDepartmentGroupQuestRepository departmentGroupQuestRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaDepartmentQuestDetailRepository departmentQuestDetailRepository;
    private final JpaUserRepository userRepository;
    private final FcmTokenService tokenService;
    private final MessageService messageService;

    public void createOrUpdateXP(UpdateDepartmentGroupQuestRequest request) {

        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(request.department(), request.departmentGroup())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 소속, 그룹이 없습니다.", 404, LocalDateTime.now())
                ));

        DepartmentGroupQuest departmentGroupQuest;
        QuestStatusType questStatus;

        if(Objects.equals(request.xp(), request.maxPoint())) {
            questStatus = QuestStatusType.COMPLETED;
        } else if (Objects.equals(request.xp(), request.mediumPoint())) {
            questStatus = QuestStatusType.ONGOING;
        } else {
            questStatus = QuestStatusType.READY;
        }

        String title = "경험치 획득!";
        String message;

        if(request.questType() == QuestType.WEEKLY) {
            message = request.year() + "년" + request.period() + "주차" + "직무별 퀘스트로" + request.xp() + "경험치를 획득하였습니다.";
        } else {
            message = request.year() + "년" + request.period() + "월" + "직무별 퀘스트로" + request.xp() + "경험치를 획득하였습니다.";
        }

        if(request.questType() == QuestType.MONTHLY) {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndMonth(departmentGroup, request.year(), request.period())
                    .orElseGet(
                            () -> departmentGroupQuestRepository.save(
                                    DepartmentGroupQuest.builder()
                                            .departmentQuestType(QuestType.MONTHLY)
                                            .maxThreshold(request.maxThreshold())
                                            .mediumThreshold(request.mediumThreshold())
                                            .departmentGroupQuestStatus(questStatus)
                                            .mediumPoint(request.mediumPoint())
                                            .maxPoint(request.maxPoint())
                                            .departmentGroup(departmentGroup)
                                            .year(request.year())
                                            .month(request.period())
                                            .nowXP(request.xp())
                                            .note(request.notes())
                                            .productivity(request.productivity())
                                            .build()
                            )
                    );
        } else {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndWeek(departmentGroup, request.year(), request.period())
                    .orElseGet(() -> departmentGroupQuestRepository.save(
                            DepartmentGroupQuest.builder()
                                    .departmentQuestType(QuestType.MONTHLY)
                                    .maxThreshold(request.maxThreshold())
                                    .mediumThreshold(request.mediumThreshold())
                                    .departmentGroupQuestStatus(questStatus)
                                    .mediumPoint(request.mediumPoint())
                                    .maxPoint(request.maxPoint())
                                    .departmentGroup(departmentGroup)
                                    .year(request.year())
                                    .week(request.period())
                                    .nowXP(request.xp())
                                    .note(request.notes())
                                    .productivity(request.productivity())
                                    .build()
                            )
                    );
        }

        departmentGroupQuest.updateNowXP(request.xp());
        departmentGroupQuest.updateProductivity(request.productivity());
        departmentGroupQuest.updateMaxThreshold(request.maxThreshold());
        departmentGroupQuest.updateMediumThreshold(request.mediumThreshold());

        if(!QuestStatusType.READY.equals(questStatus)) {
            List<User> userList = userRepository.findByDepartmentGroup(departmentGroup);

            for(User user : userList) {
                List<ReadFcmTokenResponse> tokenList = tokenService.getFcmTokens(user.getId());

                for(ReadFcmTokenResponse token : tokenList) {
                    messageService.sendMessageTo(user, token.fcmToken(), title, message, null);
                }
            }
        }
    }

    public void createOrUpdateDepartQuestDetail(UpdateDepartQuestDetailRequest request) {

        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(request.department(), request.departmentGroup())
                        .orElseThrow(() -> new ApplicationException(
                                ErrorStatus.toErrorStatus("해당하는 직무 그룹이 없습니다.", 404, LocalDateTime.now())
                        ));

        DepartmentGroupQuest departmentGroupQuest;

        if(request.questType() == QuestType.WEEKLY) {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndWeek(departmentGroup, request.year(), request.week())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 직무 그룹 퀘스트가 없습니다.", 404, LocalDateTime.now())
                    ));;
        } else {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndMonth(departmentGroup, request.year(), request.month())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 직무 그룹 퀘스트가 없습니다.", 404, LocalDateTime.now())
                    ));
        }

        DepartmentQuestDetail departmentQuestDetail = departmentQuestDetailRepository.findByDepartmentGroupQuest(departmentGroupQuest)
                .stream()
                .filter(detail -> detail.getDepartmentQuestDetailDate().equals(request.date()))
                .findAny()
                .orElseGet(() -> departmentQuestDetailRepository.save(
                        DepartmentQuestDetail.builder()
                                .departmentQuestDetailDate(request.date())
                                .revenue(request.revenue())
                                .laborCost(request.laborCost())
                                .designServiceFee(request.designServiceFee())
                                .employeeSalary(request.employeeSalary())
                                .retirementBenefit(request.retirementBenefit())
                                .socialInsuranceBenefit(request.socialInsuranceBenefit())
                                .departmentGroupQuest(departmentGroupQuest)
                                .build()
                ));

        departmentQuestDetail.updateAll(request.date(), request.revenue(), request.laborCost(), request.designServiceFee(), request.employeeSalary(), request.retirementBenefit(), request.socialInsuranceBenefit());
    }

    public ReadDepartmentGroupQuestResponse getDepartmentGroupQuest(Long userId, LocalDateTime date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 유저가 없습니다.", 404, LocalDateTime.now())
                ));

        DepartmentGroup departmentGroup = user.getDepartmentGroup();

        int year = date.getYear();

        List<DepartmentGroupQuest> departmentGroupQuestList = departmentGroupQuestRepository.findByDepartmentGroupAndYear(departmentGroup, year);

        QuestType questType = departmentGroupQuestList.getFirst().getDepartmentQuestType();

        if(questType == QuestType.WEEKLY) {
            WeekFields weekFields = WeekFields.of(Locale.KOREA);
            int weekOfYear = date.get(weekFields.weekOfYear());

            return departmentGroupQuestList.stream()
                    .filter(item -> item.getWeek() == weekOfYear)
                    .map(ReadDepartmentGroupQuestResponse::fromEntity)
                    .findFirst()
                    .orElse(null);
        } else {
            return departmentGroupQuestList.stream()
                    .filter(item -> item.getMonth() == date.getMonth().getValue())
                    .map(ReadDepartmentGroupQuestResponse::fromEntity)
                    .findFirst()
                    .orElse(null);
        }
    }

    public ReadDepartQuestDetailResponse getDepartmentDetailById(Long departmentGroupQuestId) {

        DepartmentGroupQuest departmentGroupQuest = departmentGroupQuestRepository.findById(departmentGroupQuestId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 직무별 퀘스트가 없습니다.", 404, LocalDateTime.now())
                ));

        long gainedXP = 0;

        if(departmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.COMPLETED) {
            gainedXP = departmentGroupQuest.getMaxPoint();
        } else if(departmentGroupQuest.getDepartmentGroupQuestStatus() == QuestStatusType.ONGOING) {
            gainedXP = departmentGroupQuest.getMediumPoint();
        }

        List<ReadDepartmentQuestDetailResponse> departmentQuestDetailList = departmentQuestDetailRepository.findByDepartmentGroupQuest(departmentGroupQuest)
                .stream().map(ReadDepartmentQuestDetailResponse::fromEntity).toList();

        return ReadDepartQuestDetailResponse.from(departmentQuestDetailList, gainedXP, departmentGroupQuest);
    }

    @Scheduled(cron = "0 0 4 * * 1")
    public void failedDepartmentGroupQuestWeek() {
        List<DepartmentGroup> departmentGroupList = departmentGroupRepository.findAll();

        LocalDateTime now = LocalDateTime.now();
        int currentWeek = now.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        int previousWeek = (currentWeek == 1) ? 52 : currentWeek - 1;

        for (DepartmentGroup departmentGroup : departmentGroupList) {
            DepartmentGroupQuest departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndWeek(departmentGroup, now.getYear(), previousWeek)
                    .orElse(null);

            if (departmentGroupQuest != null) {
                if (!Objects.equals(departmentGroupQuest.getNowXP(), departmentGroupQuest.getMaxPoint())
                        && !Objects.equals(departmentGroupQuest.getNowXP(), departmentGroupQuest.getMediumPoint())) {
                    departmentGroupQuest.updateStatus(QuestStatusType.FAILED);
                }
            }
        }
    }

    @Scheduled(cron = "0 0 4 1 * *")
    public void failedDepartmentGroupQuestMonth() {
        List<DepartmentGroup> departmentGroupList = departmentGroupRepository.findAll();

        LocalDateTime previousMonthDate = LocalDateTime.now().minusMonths(1);

        int previousMonth = previousMonthDate.getMonthValue();
        int previousYear = previousMonthDate.getYear();

        for (DepartmentGroup departmentGroup : departmentGroupList) {

            DepartmentGroupQuest previousMonthQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndMonth(departmentGroup, previousYear, previousMonth)
                    .orElse(null);

            if (previousMonthQuest != null) {
                if (!Objects.equals(previousMonthQuest.getNowXP(), previousMonthQuest.getMaxPoint())
                        && !Objects.equals(previousMonthQuest.getNowXP(), previousMonthQuest.getMediumPoint())) {
                    previousMonthQuest.updateStatus(QuestStatusType.FAILED);
                }
            }
        }
    }
}
