package com.blaybus.appsolute.level.service;

import com.blaybus.appsolute.level.domain.response.ReadLevelResponse;
import com.blaybus.appsolute.level.repository.JpaLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LevelService {

    private final JpaLevelRepository jpaLevelRepository;

    public List<ReadLevelResponse> getAllLevels() {
        return jpaLevelRepository.findAll().stream().map(ReadLevelResponse::fromEntity).toList();
    }
}
