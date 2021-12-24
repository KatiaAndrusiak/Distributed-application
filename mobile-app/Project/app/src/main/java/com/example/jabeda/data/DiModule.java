package com.example.jabeda.data;

public class DiModule {
    private static ProblemRepository repository;

    public static ProblemRepository getRepository() {
        if (repository == null) {
            repository = new ProblemRepository();
        }
        return repository;
    }


}
