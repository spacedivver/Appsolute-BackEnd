package com.blaybus.appsolute.googlesheet.service;

import com.blaybus.appsolute.commons.exception.ApplicationException;
import com.blaybus.appsolute.commons.exception.payload.ErrorStatus;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoogleSheetService {

    @Value("${google.spreadsheet.id}")
    private String spreadsheetId;

    private final Sheets googleSheet;

    public List<List<Object>> readSpreadsheet(String range) {
        ValueRange response = null;
        try {
            response = googleSheet.spreadsheets().values()
                    .get(spreadsheetId, range)
                    .execute();
        } catch (IOException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("통신 중 오류가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
        return response.getValues();
    }

    public void updateSheet(String range, String data) {

        ValueRange body = new ValueRange().setValues(List.of(List.of(data)));

        try {
            googleSheet.spreadsheets().values()
                    .update(spreadsheetId, range, body)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("spread sheet 연결 중 문제가 발생하였습니다.", 500, LocalDateTime.now())
            );
        }
    }
}
