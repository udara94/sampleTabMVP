package com.example.sample.model.rest;

import com.example.sample.model.entities.response.SampleResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface SampleAPI {


    @GET("jobtypes")
    Single<SampleResponse> getJobTypesAPI(
            @Query("jobField") String jobField
    );
}
