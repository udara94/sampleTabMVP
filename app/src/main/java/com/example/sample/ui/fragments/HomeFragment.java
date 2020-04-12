package com.example.sample.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sample.R;
import com.example.sample.ui.activity.MainActivity;
import com.example.sample.utils.BaseBackPressedListener;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements BaseBackPressedListener.OnBackPressedListener {

    final String TAG = HomeFragment.this.getClass().getSimpleName();

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public static HomeFragment homeFragment;

    public static String getTAG() {
        return "HomeFragment";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            Log.d(TAG, "onCreateView");
            ButterKnife.bind(this, rootView);
            homeFragment = this;
            ((MainActivity) getActivity()).setOnBackPressedListener(this);

        } catch (Exception e) {
            Log.e(TAG, "onCreateView: " + e.toString());
        }
        return rootView;
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void setUpUI() {

    }

    @Override
    protected void setUpToolBar() {

    }

    @Override
    public void doBack() {

    }
}
