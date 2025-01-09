package com.blaybus.appsolute.departmentgroupquest.service;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.departmentgroupquest.domain.entity.DepartmentGroupQuest;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestStatusType;
import com.blaybus.appsolute.departmentgroupquest.domain.type.QuestType;
import com.blaybus.appsolute.departmentgroupquest.repository.JpaDepartmentGroupQuestRepository;
import com.blaybus.appsolute.googlesheet.service.GoogleSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentGroupQuestSheetService {

    private final JpaDepartmentGroupQuestRepository departmentGroupQuestRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final GoogleSheetService sheetService;

    public void syncGroupQuestSheet() {
        List<List<Object>> values = sheetService.readSpreadsheet("직무별 퀘스트!F11:H11");

        List<Object> firstRow = values.getFirst();
        String departmentName = (String)firstRow.get(0);
        String departmentGroupName = (String)firstRow.get(1);
        DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName(departmentName, departmentGroupName)
                        .orElseGet(() -> departmentGroupRepository.save(toDepartmentGroup(departmentName, departmentGroupName)));
        QuestType questType = "주".equals((String)firstRow.get(2)) ? QuestType.WEEKLY : QuestType.MONTHLY;


        values = sheetService.readSpreadsheet("직무별 퀘스트!L14");
        String cellValue = (String)values.getFirst().getFirst();
        int year = 2000 + Integer.parseInt(cellValue.split("-")[0]);

        values = sheetService.readSpreadsheet("직무별 퀘스트!B11:C11");

        Long maxPoint = Long.parseLong((String)values.getFirst().getFirst());
        Long midPoint = Long.parseLong((String)values.getFirst().get(1));

        values = sheetService.readSpreadsheet("직무별 퀘스트!F14:H65");
        List<List<Object>> xpList = sheetService.readSpreadsheet("직무별 퀘스트!B14:D65");

        departmentGroupQuestRepository.deleteByDepartmentGroupAndYear(departmentGroup, year);

        if(QuestType.WEEKLY == questType) {

            for(int i = 1; i <= 52; i++) {
                Double maxThreshold = (Double)values.get(i).get(0);
                Double midThreshold = (Double)values.get(i).get(1);
                int week = (Integer)values.get(i).get(2);
                long xp = (Long)xpList.get(i).get(2);

                QuestStatusType status;
                if(xp == maxPoint) {
                    status = QuestStatusType.MAX_COMPLETE;
                } else if (xp == midPoint) {
                    status = QuestStatusType.MEDIUM_COMPLETE;
                } else {
                    status = QuestStatusType.INCOMPLETE;
                }

                departmentGroupQuestRepository.save(getDepartmentGroupQuestWeekly(questType, maxThreshold, midThreshold, midPoint, maxPoint, departmentGroup, year, week, xp, status));

            }
        } else {
            for(int i = 1; i <=12; i++) {
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
