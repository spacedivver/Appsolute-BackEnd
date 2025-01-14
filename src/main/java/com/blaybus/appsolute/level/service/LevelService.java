package com.blaybus.appsolute.level.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.level.domain.request.CreateLevelRequest;
import com.blaybus.appsolute.level.domain.response.ReadLevelResponse;
import com.blaybus.appsolute.level.repository.JpaLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LevelService {

    private final JpaLevelRepository jpaLevelRepository;

    public List<ReadLevelResponse> getAllLevels() {
        return jpaLevelRepository.findAll().stream().map(ReadLevelResponse::fromEntity).toList();
    }

    public void createLevel(CreateLevelRequest request) {
        Level level = jpaLevelRepository.save(request.toEntity());

        Level previousLevel = jpaLevelRepository.findByLevelName(request.previousLevel())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당 레벨이 없습니다.", 404, LocalDateTime.now())
                ));

        previousLevel.updateMaxPoint(request.levelAchievement() -1);
        previousLevel.updateNextLevel(level);
    }
}
