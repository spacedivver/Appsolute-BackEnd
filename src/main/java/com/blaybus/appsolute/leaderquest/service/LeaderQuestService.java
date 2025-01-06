package com.blaybus.appsolute.leaderquest.service;

import com.blaybus.appsolute.leaderquest.repository.LeaderQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaderQuestService {
    private final LeaderQuestRepository leaderQuestRepository;
}
