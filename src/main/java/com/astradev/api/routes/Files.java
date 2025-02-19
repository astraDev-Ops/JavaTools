package com.astradev.api.routes;

import com.astradev.api.API;
import com.astradev.api.exceptions.RateLimitExceededException;
import com.astradev.api.exceptions.ValidationFailureException;
import com.astradev.objects.FileScanOutput;
import com.astradev.objects.HashList;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.regex.Matcher;

public class Files implements API<FileScanOutput> {
    public FileScanOutput post(HashList data) throws IllegalAccessException, IOException, ValidationFailureException, RateLimitExceededException {
        if (data.sha256() == null || data.sha512() == null) {
            throw new IllegalAccessException("You cannot send blank data to this endpoint.");
        }
        Matcher detectHexViolations512 = validation.detectInvalidHexChars.matcher(data.sha512());
        Matcher detectHexViolations256 = validation.detectInvalidHexChars.matcher(data.sha256());
        if (detectHexViolations256.hasMatch() || detectHexViolations512.hasMatch()) {
            throw new ValidationFailureException("Invalid non standard HEX chars present.");
        }
        String json = gson.toJson(data);
        RequestBody postData = RequestBody.create(json, jsonType);
        Request request = new Request.Builder()
                .url(baseURL + "file")
                .post(postData)
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            int code = response.code();
            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("FILE GET RETURNED A NULL VALUE");
            }
            switch (code) {
                case 429 -> throw new RateLimitExceededException("");
                case 200 -> {
                    String output = body.string();
                    return gson.fromJson(output, FileScanOutput.class);
                }
                case 500 -> throw new IOException("An unexpected server error occurred");
            }
            if (!response.isSuccessful()) {
                throw new IOException("Response: " + response.code()); //catch all
            }
            throw new IOException("Unexpected response received with code: " + code);
        }
    }
}