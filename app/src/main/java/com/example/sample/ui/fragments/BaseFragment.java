package com.example.sample.ui.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.sample.R;
import com.example.sample.model.rest.SampleAPIService;
import com.example.sample.mvp.presenters.Presenter;
import com.example.sample.ui.activity.MainActivity;
import com.example.sample.utils.AppScheduler;
import com.example.sample.utils.IScheduler;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    protected SampleAPIService sampleAPIService;
    protected IScheduler scheduler;
    protected Presenter presenter;

    protected Toolbar mToolBar;
    protected RelativeLayout loadingView;
    private ProgressDialog progressDialog = null;
    private KProgressHUD pd = null;

    public static AlertDialog myAlertDialog;
    public static AlertDialog myAlertDialogOne;
    public static AlertDialog myAlertDialogTwo;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduler = new AppScheduler();
        sampleAPIService = new SampleAPIService();
        initializePresenter();
        if (presenter != null) presenter.onCreate();
        mToolBar = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        getActionBarToolbar(view);
        setupLoading(view);
        setUpToolBar();
        setUpUI();

    }

    @Override
    public void onDestroyView() {
        setProgressDialog(false, null);
        progressDialog = null;

        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void initializePresenter();

    protected abstract void setUpUI();

    protected abstract void setUpToolBar();

    protected Toolbar getActionBarToolbar(View v) {
        mToolBar = (Toolbar) v.findViewById(R.id.toolbar);
        if (mToolBar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(mToolBar);
            mToolBar.setContentInsetsAbsolute(0, 0); /** remove actionbar unnecessary left margin */
        }
        return mToolBar;
    }

    protected void setupLoading(View v) {
        loadingView = (RelativeLayout) v.findViewById(R.id.rl_progress);
    }

    public void setLoading(boolean isLoading) {
        if (loadingView != null) loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    public void setProgressDialog(boolean isLoading, String msg) {
        try {
            if (isLoading) {
                if(msg == null || msg.isEmpty())msg = "Please wait";
                if (pd != null) pd.show();
                else pd = KProgressHUD.create(getActivity())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setLabel(msg)
                        .setAnimationSpeed(1)
                        .setDimAmount(0.3f)
                        .show();

            } else {
                if (pd != null) pd.dismiss();
            }
        } catch (Exception e) {
            Log.e("BaseFragment", "setProgressDialog: " + e.toString());
        }
    }

    protected void showTopSnackBar(String message, int bColor) {
        Snackbar snack = Snackbar.make(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snack.getView();
        snackbarView.setBackgroundColor(bColor);
        snack.show();
    }

    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    protected void showAlertDialog(boolean setCancelable, String title, String message, DialogInterface.OnClickListener positiveListener) {
        if(myAlertDialogTwo != null) return;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setCancelable(setCancelable)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myAlertDialogTwo = null;
                    }
                });
        if (!getActivity().isFinishing()) myAlertDialogTwo = alertDialog.show();
    }

    protected DialogInterface.OnClickListener defaultDialogClickListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        };
    }

    @Override
    public void onDestroy() {
        if(pd != null)pd.dismiss();
        super.onDestroy();
    }
}
