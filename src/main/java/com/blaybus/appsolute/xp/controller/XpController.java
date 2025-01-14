package com.blaybus.appsolute.xp.controller;

import com.blaybus.appsolute.xp.domain.request.CreateXpRequest;
import com.blaybus.appsolute.xp.domain.request.DeleteXpRequest;
import com.blaybus.appsolute.xp.domain.request.UpdateXpRequest;
import com.blaybus.appsolute.xp.service.XpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/xp")
public class XpController {

    private final XpService xpService;

    @PostMapping("/year")
    public ResponseEntity<Void> createYearXp(@RequestBody CreateXpRequest request) {
        xpService.createXp(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/year")
    public ResponseEntity<Void> updateYearXp(@RequestBody UpdateXpRequest request) {
        xpService.updateYearXp(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/year")
    public ResponseEntity<Void> deleteYearXp(@RequestBody DeleteXpRequest request) {
        xpService.deleteYearXp(request);
        return ResponseEntity.ok().build();
    }
}
