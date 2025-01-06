package com.blaybus.appsolute.leaderquest.controller;

import com.blaybus.appsolute.leaderquest.service.LeaderQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderquest")
public class LeaderQuestController {
    private final LeaderQuestService leaderQuestService;

}
