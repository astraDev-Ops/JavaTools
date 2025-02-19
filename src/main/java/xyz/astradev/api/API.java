package xyz.astradev.api;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import xyz.astradev.Validation;

public interface API<T> {
    String baseURL = "https://api.astradev.xyz/v5/";
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
    Validation validation = new Validation();
}