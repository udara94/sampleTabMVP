package com.example.sample.domain;

import com.example.sample.model.entities.response.SampleResponse;

import io.reactivex.Single;

public class SampleServiceImpl implements SampleService{

    @Override
    public Single<SampleResponse> sampleService(String search) {
        return null;
    }
}
