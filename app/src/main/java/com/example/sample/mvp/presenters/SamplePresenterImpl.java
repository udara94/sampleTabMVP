package com.example.sample.mvp.presenters;

import android.app.Activity;
import android.util.Log;

import com.example.sample.common.constants.ApplicationConstants;
import com.example.sample.domain.SampleService;
import com.example.sample.domain.Service;
import com.example.sample.model.entities.response.SampleResponse;
import com.example.sample.model.rest.exception.RetrofitException;
import com.example.sample.mvp.views.SampleView;
import com.example.sample.mvp.views.View;
import com.example.sample.utils.IScheduler;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

public class SamplePresenterImpl extends BasePresenter implements  SamplePresenter{

    private final static String TAG = "SamplePresenterImpl";
    private SampleView mSampleView;

    public SamplePresenterImpl(Activity activityContext, Service pService, IScheduler scheduler, SampleView mSampleView) {
        super(activityContext, pService, scheduler);
    }

    @Override
    public void attachView(View v) {
        if(v instanceof SampleView){
            mSampleView = (SampleView) v;
            mView = mSampleView;
        }
    }

    @Override
    public void getSample(String sample) {
        disposable.add(getSampleObservable(sample).subscribeWith(getJobTypesSubscriber()));
    }

    private Single<SampleResponse> getSampleObservable(String sample){
        try {
            return getService().sampleService(sample)
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread());


        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }


    private DisposableSingleObserver<SampleResponse> getJobTypesSubscriber(){
        return  new DefaultSubscriber<SampleResponse>(this.mView){

            @Override
            public void onError(Throwable e) {
                try {
                    RetrofitException error = (RetrofitException) e;
                    if(error.getKind() == RetrofitException.Kind.EXPIRED){
                        SampleResponse exceptionResponse = new SampleResponse();
                        exceptionResponse.setSuccess(false);
                        exceptionResponse.setTokenExpired(true);
                        exceptionResponse.setMessage(ApplicationConstants.ERROR_MSG_TOKEN_EXPIRED);
                        mSampleView.showSampleResponse(exceptionResponse);
                    }else {
                        SampleResponse response = error.getErrorBodyAs(SampleResponse.class);
                        if(response == null){
                            response = new SampleResponse();
                            response.setMessage(getExceptionMessage(e));
                            response.setAPIError(false);
                        }else {
                            response.setAPIError(true);
                        }
                        response.setSuccess(false);
                        mSampleView.showSampleResponse(response);
                    }
                }catch (IOException ex){
                    SampleResponse exceptionResponse = new SampleResponse();
                    exceptionResponse.setSuccess(false);
                    exceptionResponse.setAPIError(false);
                    exceptionResponse.setMessage(ApplicationConstants.ERROR_MSG_REST_UNEXPECTED);
                    mSampleView.showSampleResponse(exceptionResponse);

                    ex.printStackTrace();
                }
                super.onError(e);
            }

            @Override
            public void onSuccess(SampleResponse response) {
                if(response != null){
                    response.setSuccess(true);
                    mSampleView.showSampleResponse(response);
                }
            }
        };
    }

    private SampleService getService (){
        return (SampleService) mService;
    }
}
