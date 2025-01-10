package com.blaybus.appsolute.departmentgroupquest.controller;

import com.blaybus.appsolute.departmentgroup.domain.DepartmentGroup;
import com.blaybus.appsolute.departmentgroupquest.domain.request.UpdateDepartmentGroupQuestRequest;
import com.blaybus.appsolute.departmentgroupquest.service.DepartmentGroupQuestService;
import com.blaybus.appsolute.departmentgroupquest.service.DepartmentGroupQuestSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department-quest/sheet")
public class DepartmentQuestSheetController {

    private final DepartmentGroupQuestSheetService departmentGroupQuestSheetService;

    @PostMapping("/sync")
    public ResponseEntity<Void> syncSheet() {
        departmentGroupQuestSheetService.syncGroupQuestSheet();
        return ResponseEntity.ok().build();
    }
}
