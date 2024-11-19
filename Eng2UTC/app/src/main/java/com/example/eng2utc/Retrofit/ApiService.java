package com.example.eng2utc.Retrofit;

import com.example.eng2utc.Model.PartDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/tests/")
    Call<PartDetailResponse> getPartDetails(@Query("test_id") String testId);
}
