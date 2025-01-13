package com.blaybus.appsolute.commons.config;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;

@Configuration
public class GoogleSheetConfig {

    @Bean
    public Sheets googleSheet() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                        new FileInputStream("src/main/resources/googlesheet/google-account-key.json"))
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));

        return new Sheets.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials))
                .setApplicationName("blaybus-appsolute")
                .build();
    }
}
