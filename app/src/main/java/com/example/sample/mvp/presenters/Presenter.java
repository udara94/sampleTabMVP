package com.example.sample.mvp.presenters;


import com.example.sample.mvp.views.View;

public interface Presenter {

    void onCreate();
    void onStart();
    void onDestroy();
    void attachView(View v);
    void onStop();
}
