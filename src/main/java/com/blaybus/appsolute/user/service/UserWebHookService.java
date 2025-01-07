package com.blaybus.appsolute.user.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroup.repository.JpaDepartmentGroupRepository;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.level.domain.repository.JpaLevelRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import com.blaybus.appsolute.xp.domain.XP;
import com.blaybus.appsolute.xp.repository.JpaXPRepository;
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
public class UserWebHookService {

    private final JpaUserRepository userRepository;
    private final JpaDepartmentGroupRepository departmentGroupRepository;
    private final JpaXPRepository xpRepository;
    private final JpaLevelRepository levelRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void syncInitialUserData(List<List<Object>> sheetData) {
        for(List<Object> row : sheetData) {

            // 에외 처리 (row 값이 없을 때)

            DepartmentGroup departmentGroup = departmentGroupRepository.findByDepartmentNameAndDepartmentGroupName((String) row.get(3), (String) row.get(4))
                    .orElseGet( () ->
                            departmentGroupRepository.save(parseToDepartmentGroup(row)
                    ));

            Level level = levelRepository.findByLevelName((String)row.get(5))
                    .orElseThrow(() -> new ApplicationException(
                                    ErrorStatus.toErrorStatus("존재하지 않는 레벨입니다.", 404, LocalDateTime.now())
                            ));

            Long totalXP = Long.parseLong(row.get(6).toString());

            //레벨 자동으로 하려면 엑셀에서 함수로 해야될 거 같음
            if(level.getLevelAchievement() < totalXP && totalXP <= level.getMaxPoint()) {
                throw new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 레벨이 아닙니다.", 400, LocalDateTime.now())
                );
            }

            User user = userRepository.findByEmployeeNumber((String)row.get(0))
                    .orElseGet( () ->
                            userRepository.save(parseToUser(row, departmentGroup, level)
                    ));

            for (int year = 2023; year >= 2013; year--) {
                int index = 2023 - year + 11;

                if (row.size() > index && row.get(index) != null) {
                    long xpPoint = Long.parseLong(row.get(index).toString());

                    XP xp = XP.builder()
                            .user(user)
                            .year(year)
                            .point(xpPoint)
                            .build();

                    xpRepository.save(xp);
                }
            }
        }
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
                    ErrorStatus.toErrorStatus("유저로 변경 중 오류가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
    }
}
