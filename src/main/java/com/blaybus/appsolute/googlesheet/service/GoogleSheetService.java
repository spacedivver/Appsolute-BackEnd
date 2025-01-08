package com.blaybus.appsolute.googlesheet.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleSheetService {

    @Value("${google.spreadsheet.id}")
    private String spreadsheetId;

    private final Sheets googleSheet;

    public List<List<Object>> readSpreadsheet(String range) throws IOException {
        ValueRange response = googleSheet.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }
}
