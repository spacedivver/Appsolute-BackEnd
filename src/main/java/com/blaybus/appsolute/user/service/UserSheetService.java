package com.blaybus.appsolute.user.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.googlesheet.service.GoogleSheetService;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.level.domain.repository.JpaLevelRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.entity.Xp;
import com.blaybus.appsolute.xp.domain.entity.XpDetail;
import com.blaybus.appsolute.xp.repository.JpaXpRepository;
import com.blaybus.appsolute.xp.repository.JpaXpDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSheetService {

    private final JpaUserRepository userRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaXpRepository xpRepository;
    private final JpaLevelRepository levelRepository;
    private final GoogleSheetService sheetService;
    private final JpaXpDetailRepository xpDetailRepository;

    private static final String range = "구성원 정보!B10:V";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void syncInitialUserData() {

        userRepository.deleteAll();

        List<List<Object>> sheetData = sheetService.readSpreadsheet(range);

        for(List<Object> row : sheetData) {

            DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName((String) row.get(3), (String) row.get(4))
                    .orElseGet( () ->
                            departmentGroupRepository.save(parseToDepartmentGroup(row)
                    ));

            Level level = levelRepository.findByLevelName((String)row.get(5))
                    .orElseThrow(() -> new ApplicationException(
                                    ErrorStatus.toErrorStatus("존재하지 않는 레벨입니다.", 404, LocalDateTime.now())
                            ));

            Long totalXP = Long.parseLong(row.get(9).toString().replace(",", ""));

            if(level.getLevelAchievement() > totalXP && totalXP > level.getMaxPoint()) {
                throw new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 레벨이 아닙니다.", 400, LocalDateTime.now())
                );
            }

            User user = userRepository.save(parseToUser(row, departmentGroup, level));

            for (int year = 2023; year >= 2013; year--) {
                int index = 2023 - year + 10;

                if (row.size() > index && row.get(index) != null) {
                    long xpPoint = Long.parseLong(row.get(index).toString().replace(",", ""));

                    Xp xp = Xp.builder()
                            .user(user)
                            .year(year)
                            .build();

                    xpRepository.save(xp);

                    XpDetail xpDetail = XpDetail.builder()
                            .point(xpPoint)
                            .createdAt(LocalDateTime.of(year, 1,1,0, 0))
                            .xp(xp)
                            .build();

                    xpDetailRepository.save(xpDetail);
                }
            }
        }
    }

    public void updateSpreadsheetPassword(String employeeNumber, String newPassword) {

        List<List<Object>> sheetData = sheetService.readSpreadsheet("구성원 정보!B10:B");

        int rowIndex = -1;
        for (int i = 0; i < sheetData.size(); i++) {
            List<Object> row = sheetData.get(i);

            if (!row.isEmpty() && row.getFirst() != null && row.getFirst().toString().equals(employeeNumber)) {
                rowIndex = i + 10;
                break;
            }
        }

        if (rowIndex == -1) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("스프레드 시트에서 데이터를 찾을 수 없습니다.", 404, LocalDateTime.now())
            );
        }

        String range = "구성원 정보!J" + rowIndex;

        sheetService.updateSheet(range, newPassword);
    }

    private static DepartmentGroup parseToDepartmentGroup(List<Object> row) {
        return DepartmentGroup.builder()
                .departmentName((String) row.get(3))
                .departmentGroupName((String) row.get(4))
                .build();
    }

    private User parseToUser(List<Object> row, DepartmentGroup departmentGroup, Level level) {
        try {
            return User.builder()
                    .employeeNumber((String)row.get(0))
                    .userName((String) row.get(1))
                    .joiningDate(sdf.parse((String)row.get(2)))
                    .departmentGroup(departmentGroup)
                    .level(level)
                    .userId(row.get(6).toString())
                    .initialPassword(row.get(7).toString())
                    .changedPassword(row.get(8) == null ? null : (String)row.get(8))
                    .characters(null)
                    .build();
        } catch (ParseException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("date 변경도중 오류가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
    }
}
