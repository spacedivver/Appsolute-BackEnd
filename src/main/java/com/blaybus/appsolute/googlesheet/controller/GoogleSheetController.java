package com.blaybus.appsolute.googlesheet.controller;

import com.blaybus.appsolute.googlesheet.service.GoogleSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoogleSheetController {

    private final GoogleSheetService googleSheetService;

    String range = "참고. 올해 경험치!A1:C4";

    @GetMapping("/sheet")
    public ResponseEntity<List<List<Object>>> getSpreadSheetNames() throws IOException {
        return ResponseEntity.ok(googleSheetService.readSpreadsheet(range));
    }
}
