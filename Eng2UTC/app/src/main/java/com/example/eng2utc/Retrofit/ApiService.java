package com.example.eng2utc.Retrofit;

import com.example.eng2utc.Model.MemoryLevel;
import com.example.eng2utc.Model.PartDetailResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/tests/")
    Call<PartDetailResponse> getPartDetails(@Query("test_id") String testId);

    //with 2 input is user_id and string_date
    @GET("/users/attendings/streak")
    Call<Integer> getStreak(@Query("user_id") String userId, @Query("attending_date") String attendingDate);

    //with 2 input is user_id
    @GET("/users/user_vocabs/memory_level")
    Call<List<MemoryLevel>> getMemoryLevel(@Query("user_id") String userId);

    //with 2 input is user_id
    @GET("/users/user_vocabs/total_words")
    Call<Integer> getTotalWords(@Query("user_id") String userId);
}
