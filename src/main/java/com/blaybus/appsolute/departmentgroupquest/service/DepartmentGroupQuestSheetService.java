package com.blaybus.appsolute.departmentgroupquest.service;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentGroupQuestRepository;
import com.blaybus.appsolute.googlesheet.service.GoogleSheetService;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import com.blaybus.appsolute.xp.repository.JpaXpRepository;
import com.blaybus.appsolute.xp.repository.JpaXpDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DepartmentGroupQuestSheetService {

    private final JpaDepartmentGroupQuestRepository departmentGroupQuestRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaXpDetailRepository xpDetailRepository;
    private final JpaXpRepository xpRepository;
    private final JpaUserRepository userRepository;
    private final GoogleSheetService sheetService;

    public void syncGroupQuestSheet() {
        List<List<Object>> values = sheetService.readSpreadsheet("직무별 퀘스트!F11:H11");

        List<Object> firstRow = values.getFirst();
        String departmentName = (String)firstRow.get(0);
        String departmentGroupName = (String)firstRow.get(1);
        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(departmentName, departmentGroupName)
                        .orElseGet(() -> departmentGroupRepository.save(toDepartmentGroup(departmentName, departmentGroupName)));
        QuestType questType = "주".equals((String)firstRow.get(2)) ? QuestType.WEEKLY : QuestType.MONTHLY;

        List<User> userList = userRepository.findByDepartmentGroup(departmentGroup);

        values = sheetService.readSpreadsheet("직무별 퀘스트!L14");
        String cellValue = (String)values.getFirst().getFirst();
        int year = 2000 + Integer.parseInt(cellValue.split("-")[0]);

        values = sheetService.readSpreadsheet("직무별 퀘스트!B11:C11");

        long maxPoint = Long.parseLong((String)values.getFirst().getFirst());
        long midPoint = Long.parseLong((String)values.getFirst().get(1));

        values = sheetService.readSpreadsheet("직무별 퀘스트!F14:H65");
        List<List<Object>> xpList = sheetService.readSpreadsheet("직무별 퀘스트!B14:D65");

        departmentGroupQuestRepository.deleteByDepartmentGroupAndYear(departmentGroup, year);

        if(QuestType.WEEKLY == questType) {

            for(int i = 0; i <= 51; i++) {
                Double maxThreshold = Double.parseDouble(values.get(i).get(0).toString());
                Double midThreshold = Double.parseDouble(values.get(i).get(1).toString());
                int week = Integer.parseInt(values.get(i).get(2).toString());
                long point = Long.parseLong(xpList.get(i).get(1).toString());

                QuestStatusType status;
                if(point == maxPoint) {
                    status = QuestStatusType.MAX_COMPLETE;
                } else if (point == midPoint) {
                    status = QuestStatusType.MEDIUM_COMPLETE;
                } else {
                    status = QuestStatusType.INCOMPLETE;
                }

                departmentGroupQuestRepository.save(getDepartmentGroupQuestWeekly(questType, maxThreshold, midThreshold, midPoint, maxPoint, departmentGroup, year, week, point, status));

                LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);

                WeekFields weekFields = WeekFields.of(Locale.KOREA);

//                LocalDate endOfWeek = firstDayOfYear
//                        .with(weekFields.weekOfYear(), week)
//                        .with(weekFields.dayOfWeek(), 7);

//                for(User user : userList) {
//
//                    Xp xp = xpRepository.findByUserAndYear(user, year)
//                            .orElseGet(() -> xpRepository.save(
//                                    Xp.builder()
//                                            .year(year)
//                                            .user(user)
//                                            .build()
//                            ));
//
//                    XpDetail xpDetail = XpDetail.builder()
//                            .point(point)
//                            .createdAt(endOfWeek.atStartOfDay())
//                            .xp(xp)
//                            .build();
//
//                    xpDetailRepository.save(xpDetail);
//                }
            }
        } else {
            for(int i = 0; i <=11; i++) {
                Double maxThreshold = (Double)values.get(i).get(0);
                Double midThreshold = (Double)values.get(i).get(1);
                int month = (Integer)values.get(i).get(2);
                long xp = (Long)xpList.get(i).get(2);

                QuestStatusType status;
                if(xp == maxPoint) {
                    status = QuestStatusType.MAX_COMPLETE;
                } else if (xp == midPoint) {
                    status = QuestStatusType.MEDIUM_COMPLETE;
                } else {
                    status = QuestStatusType.INCOMPLETE;
                }

                departmentGroupQuestRepository.save(getDepartmentGroupQuestMonthly(questType, maxThreshold, midThreshold, midPoint, maxPoint, departmentGroup, year, month, xp, status));
            }
        }
    }

    private static DepartmentGroupQuest getDepartmentGroupQuestWeekly(QuestType questType, Double maxThreshold, Double midThreshold, Long midPoint, Long maxPoint, DepartmentGroup departmentGroup, int year, int week, long xp, QuestStatusType status) {
        return DepartmentGroupQuest.builder()
                .departmentQuestType(questType)
                .maxThreshold(maxThreshold)
                .mediumThreshold(midThreshold)
                .mediumPoint(midPoint)
                .maxPoint(maxPoint)
                .departmentGroup(departmentGroup)
                .year(year)
                .week(week)
                .nowXP(xp)
                .departmentGroupQuestStatus(status)
                .build();
    }

    private static DepartmentGroupQuest getDepartmentGroupQuestMonthly(QuestType questType, Double maxThreshold, Double midThreshold, Long midPoint, Long maxPoint, DepartmentGroup departmentGroup, int year, int month, long xp, QuestStatusType status) {
        return DepartmentGroupQuest.builder()
                .departmentQuestType(questType)
                .maxThreshold(maxThreshold)
                .mediumThreshold(midThreshold)
                .mediumPoint(midPoint)
                .maxPoint(maxPoint)
                .departmentGroup(departmentGroup)
                .year(year)
                .month(month)
                .nowXP(xp)
                .departmentGroupQuestStatus(status)
                .build();
    }

    private static DepartmentGroup toDepartmentGroup(String departmentName, String departmentGroupName) {
        return com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup.builder()
                .departmentName(departmentName)
                .departmentGroupName(departmentGroupName)
                .build();
    }

}
