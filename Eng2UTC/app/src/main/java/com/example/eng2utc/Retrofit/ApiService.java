package com.example.eng2utc.Retrofit;

import com.example.eng2utc.Model.MemoryLevel;
import com.example.eng2utc.Model.PartDetailResponse;


import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {
    //with 1 input is test_id
    @GET("/data/{test_id}")
    Call<PartDetailResponse> getPartDetails(@Path("test_id") String testId);

    //with 2 input is user_id and string_date
    @POST("users/attendings/streak")
    Call<Integer> postStreak(@Body RequestBody body);

    //with 2 input is user_id
    @POST("/users/uservocabs/memorylevel")
    Call<List<MemoryLevel>> postMemoryLevel(@Body RequestBody body);

    //with 2 input is user_id
    @POST("/users/uservocabs/totalwords")
    Call<Integer> postTotalWords(@Body RequestBody body);
}
