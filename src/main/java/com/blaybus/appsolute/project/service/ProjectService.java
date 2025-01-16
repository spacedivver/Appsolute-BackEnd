package com.blaybus.appsolute.project.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.fcm.domain.response.ReadFcmTokenResponse;
import com.blaybus.appsolute.fcm.service.FcmTokenService;
import com.blaybus.appsolute.fcm.service.MessageService;
import com.blaybus.appsolute.project.domain.entity.Project;
import com.blaybus.appsolute.project.domain.request.ProjectRequest;
import com.blaybus.appsolute.project.domain.response.ProjectResponse;
import com.blaybus.appsolute.project.repository.JpaProjectRepository;
import com.blaybus.appsolute.user.domain.entity.User;
import com.blaybus.appsolute.user.repository.JpaUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final JpaProjectRepository jpaProjectRepository;
    private final JpaUserRepository userRepository;
    private final FcmTokenService tokenService;
    private final MessageService messageService;

    public List<ProjectResponse> getProjectByUser(String userId) {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        return jpaProjectRepository.findByUserId(user.getId()).stream()
                .map(ProjectResponse::fromEntity)
                .collect(Collectors.toList());
    }


    public void updateProject(ProjectRequest projectRequest) {

        User user = userRepository.findByEmployeeNumber(projectRequest.getEmployeeNumber())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 사용자가 없습니다.", 404, LocalDateTime.now())
                ));

        Project project = jpaProjectRepository.findByUserIdAndProjectName(user.getId(), projectRequest.getProjectName())
                .orElse(null);

        if(project == null) {
            project = Project.builder()
                    .userId(user.getId())
                    .month(projectRequest.getMonth())
                    .day(projectRequest.getDay())
                    .projectName(projectRequest.getProjectName())
                    .grantedPoint(0L)
                    .note(projectRequest.getNote())
                    .year(LocalDate.now().getYear())
                    .build();
        }

        project.updateGrantedPoint(projectRequest.getGrantedPoint());
        project.updateMonth(projectRequest.getMonth());
        project.updateDay(projectRequest.getDay());
        project.updateProjectName(projectRequest.getProjectName());
        project.updateNote(projectRequest.getNote());

        String title = "경험치 획득!";
        String message=project.getMonth()+"월"+project.getDay()+"일"+project.getProjectName()
                +"관련 전사 프로젝트"+ project.getGrantedPoint() + "경험치를 획득하였습니다.";

        List<ReadFcmTokenResponse> tokens = tokenService.getFcmTokens(user.getId());

        if (!tokens.isEmpty()) {
            ReadFcmTokenResponse token = tokens.get(0);
            messageService.sendMessageTo(user, token.fcmToken(), title, message, null);
        }

        jpaProjectRepository.save(project);
    }

}