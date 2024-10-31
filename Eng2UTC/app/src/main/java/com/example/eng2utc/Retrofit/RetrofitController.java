package com.example.eng2utc.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {

    private static final String BASE_URL = "http://10.0.2.2:8000"; // Thay đổi theo URL của bạn
    private static Retrofit retrofit = null;

    // Phương thức để lấy instance của Retrofit
    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Phương thức để lấy ApiService
    public static ApiService getApiService() {
        return getInstance().create(ApiService.class);
    }
}
