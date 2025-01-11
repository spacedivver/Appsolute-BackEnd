package com.blaybus.appsolute.departmentgroupquest.controller;

import com.blaybus.appsolute.commons.annotation.Authenticated;
import com.blaybus.appsolute.departmentgroupquest.domain.request.UpdateDepartmentGroupQuestRequest;
import com.blaybus.appsolute.departmentgroupquest.domain.response.ReadDepartmentGroupQuestResponse;
import com.blaybus.appsolute.departmentgroupquest.service.DepartmentGroupQuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department-quest")
@Tag(name = "직무 퀘스트 API", description = "직무 퀘스트 API")
public class DepartmentQuestController {

    private final DepartmentGroupQuestService departmentGroupQuestService;

    @Operation(summary = "날짜로 유저의 직무 퀘스트를 가져옵니다.", description = "유저의 직무 퀘스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "직무 퀘스트 조회 성공", content = @Content(schema = @Schema(implementation = ReadDepartmentGroupQuestResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @Authenticated
    @GetMapping
    public ResponseEntity<ReadDepartmentGroupQuestResponse> getDepartmentGroupQuest(HttpServletRequest req, LocalDateTime date) {
        return ResponseEntity.ok(departmentGroupQuestService.getDepartmentGroupQuest(Long.parseLong(req.getAttribute("id").toString()), date));
    }

    @Operation(summary = "google sheet에서 직무 퀘스트를 업데이트합니다.", description = "google sheet에서 직무 퀘스트를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Void> createOrUpdateDepartmentQuest(UpdateDepartmentGroupQuestRequest request) {
        departmentGroupQuestService.createOrUpdateXP(request);
        return ResponseEntity.ok().build();
    }
}
