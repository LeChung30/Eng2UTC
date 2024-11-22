package com.example.eng2utc.Retrofit;

import com.example.eng2utc.Model.PartDetailResponse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/data/{test_id}")
    Call<PartDetailResponse> getPartDetails(@Path("test_id") String testId);
}
