package com.example.jabeda.data;

import com.example.jabeda.entity.Problem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProblemRepository {
    public final static String BASE_URL = "http://jabeda-env.eba-iqdahc9d.us-east-2.elasticbeanstalk.com";

    private List<String> categories = new ArrayList<>();
    private HashMap<String, List<String>> problemsMap = new HashMap<>();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    ProblemService service = retrofit.create(ProblemService.class);


    public void getProblems(Callback<List<ProblemResponse>> callback) {
        service.getProblems().enqueue(new Callback<List<ProblemResponse>>() {
            @Override
            public void onResponse(Call<List<ProblemResponse>> call, Response<List<ProblemResponse>> response) {
                callback.onResponse(call, response);
                if (response.body() != null) {
                    categories = new ArrayList<>();
                    problemsMap = new HashMap<>();
                    mapProblems(response);
                }
            }

            @Override
            public void onFailure(Call<List<ProblemResponse>> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    private void mapProblems(Response<List<ProblemResponse>> response) {
        Set<String> categorySet = new HashSet<>();
        for(ProblemResponse pr: response.body()) {
            String category_name = pr.category.name;
            problemsMap.computeIfAbsent(category_name, k -> new ArrayList<String>());

            Objects.requireNonNull(problemsMap.get(category_name)).add(pr.name);
            categorySet.add(category_name);
        }
        Collections.addAll(categories, categorySet.toArray(new String[]{}));
    }

    public void sendProblem(Problem problem, Callback<Void> callback) {
        service.createProblem(problem).enqueue(callback);
    }

    public List<String> getCategories() {
        return categories;

    }

    public List<String> getProblemsFor(String category) {
        return problemsMap.get(category);
    }
}
