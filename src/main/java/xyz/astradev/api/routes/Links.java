package xyz.astradev.api.routes;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import xyz.astradev.api.API;
import xyz.astradev.api.exceptions.RateLimitExceededException;
import xyz.astradev.api.exceptions.ValidationFailureException;
import xyz.astradev.objects.FileScanOutput;
import xyz.astradev.objects.LinkOutput;

import java.io.IOException;
import java.util.regex.Matcher;


public class Links implements API<LinkOutput> {
    public LinkOutput get(String url) throws IOException, ValidationFailureException, RateLimitExceededException {
        Matcher detectUrlMismatch = validation.detectURL.matcher(url);
        if (!detectUrlMismatch.find()){
            throw new ValidationFailureException("No Url was detected within the provided string: " + url);
        }
        String linkCleanup = url.split("\\?")[0];
        Request request = new Request.Builder().url(baseURL + "link?domain=" + linkCleanup).build();
        try (Response response = client.newCall(request).execute()) {
            int code = response.code();
            ResponseBody body = response.body();
            if (body == null) {
                throw new IOException("URL GET RETURNED A NULL VALUE");
            }
            switch (code) {
                case 429 -> throw new RateLimitExceededException("");
                case 200 -> {
                    String output = body.string();
                    return gson.fromJson(output, LinkOutput.class);
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
