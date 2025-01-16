package com.blaybus.appsolute.level.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.blaybus.appsolute.level.domain.entity.Level;
import com.blaybus.appsolute.level.domain.request.CreateLevelRequest;
import com.blaybus.appsolute.level.domain.request.DeleteLevelRequest;
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

        Level previousLevel = jpaLevelRepository.findByLevelName(request.previousLevel())
                .orElse(null);

        Long maxPoint = Long.MAX_VALUE;

        Level nextLevel = jpaLevelRepository.findByLevelName(request.nextLevel())
                .orElse(null);

        Level level = jpaLevelRepository.save(request.toEntity(maxPoint));

        if(nextLevel != null) {
            level.updateNextLevel(nextLevel);
            level.updateMaxPoint(nextLevel.getLevelAchievement() -1);
        }

        if(previousLevel != null) {
            previousLevel.updateMaxPoint(request.levelAchievement() -1);
            previousLevel.updateNextLevel(level);
        }
    }

    public void deleteLevel(DeleteLevelRequest request) {
        Level level = jpaLevelRepository.findByLevelName(request.levelName())
                .orElseThrow(() -> new ApplicationException(
                        ErrorStatus.toErrorStatus("해당하는 레벨이 없습니다.", 404, LocalDateTime.now())
                ));

        Level previousLevel = jpaLevelRepository.findByNextLevel(level)
                .orElse(null);

        if(previousLevel != null) {
            previousLevel.updateMaxPoint(Long.MAX_VALUE);
            previousLevel.updateNextLevel(null);
        }

        jpaLevelRepository.delete(level);
    }
}
