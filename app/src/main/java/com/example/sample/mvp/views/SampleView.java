package com.example.sample.mvp.views;

import com.example.sample.model.entities.response.SampleResponse;

public interface SampleView extends View {
    void showSampleResponse(SampleResponse sampleResponse);
}
