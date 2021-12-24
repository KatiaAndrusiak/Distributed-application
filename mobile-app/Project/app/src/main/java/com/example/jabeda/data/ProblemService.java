package com.example.jabeda.data;

import com.example.jabeda.entity.Problem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProblemService {
    @GET("/problems")
    Call<List<ProblemResponse>> getProblems();

    @POST("/request/problems")
    Call<Void> createProblem(@Body Problem body);

}
