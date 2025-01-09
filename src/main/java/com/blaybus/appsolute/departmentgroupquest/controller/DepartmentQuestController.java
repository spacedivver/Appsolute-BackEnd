package com.blaybus.appsolute.departmentgroupquest.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.departmentgroupquest.service.DepartmentGroupQuestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department-quest")
public class DepartmentQuestController {

    private final DepartmentGroupQuestService departmentGroupQuestService;

    @Authenticated
    @GetMapping
    public ResponseEntity<ReadDepartmentGroupQuestResponse> getDepartmentGroupQuest(HttpServletRequest req, LocalDateTime date) {
        return ResponseEntity.ok(departmentGroupQuestService.getDepartmentGroupQuest(Long.parseLong(req.getAttribute("id").toString()), date));
    }

}
