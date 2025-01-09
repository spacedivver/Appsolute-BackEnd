package com.blaybus.appsolute.departmentgroupquest.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.request.UpdateDepartmentGroupQuestRequest;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentGroupQuestRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentGroupQuestService {

    private final JpaDepartmentGroupQuestRepository departmentGroupQuestRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaUserRepository userRepository;

    public void updateXP(UpdateDepartmentGroupQuestRequest request) {
        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(request.department(), request.departmentGroup())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 소속, 그룹이 없습니다.", 404, LocalDateTime.now())
                ));

        DepartmentGroupQuest departmentGroupQuest;

        if(request.questType() == QuestType.MONTHLY) {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndMonth(departmentGroup, request.year(), request.period())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 퀘스트가 없습니다.", 404, LocalDateTime.now())
                    ));
        } else {
            departmentGroupQuest = departmentGroupQuestRepository.findByDepartmentGroupAndYearAndWeek(departmentGroup, request.year(), request.period())
                    .orElseThrow(() -> new ApplicationException(
                            ErrorStatus.toErrorStatus("해당하는 퀘스트가 없습니다.", 404, LocalDateTime.now())
                    ));
        }

        departmentGroupQuest.updateNowXP(request.xp());
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
}
