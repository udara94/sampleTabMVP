package com.example.sample.domain;

import com.example.sample.model.entities.response.SampleResponse;

import io.reactivex.Single;

public interface SampleService extends Service {
    Single<SampleResponse> sampleService(String search);
}
