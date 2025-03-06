package com.ap.ai.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ApiClient {
    private static final String BASE_URL = "https://adminurban.com/up_backend/api/v1/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void login(String phone, String password, String deviceId, String deviceInfo, String token, AsyncHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("deviceType", "android");
        params.put("device_info", deviceInfo);
        params.put("device_id", deviceId);
        params.put("token", token);

        client.addHeader("Authorization", "UP!and$");
        client.addHeader("app_id", "1");
        
        client.post(BASE_URL + "mobile/signIn", params, responseHandler);
    }
} 