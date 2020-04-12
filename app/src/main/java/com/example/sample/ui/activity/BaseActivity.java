package com.example.sample.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.sample.R;
import com.google.android.material.snackbar.Snackbar;
import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class BaseActivity extends AppCompatActivity {

    final String TAG = BaseActivity.this.getClass().getSimpleName();
    //    protected Presenter presenter;
    protected Toolbar mToolBar;
    public AlertDialog myAlertDialog;

    public static BaseActivity baseActivity = null;

    public static boolean isAppWentToBg = true;

    public static boolean isWindowFocused = false;

    public static boolean isBackPressed = false;

    private KProgressHUD pd = null;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
        baseActivity = this;
    }

    protected Toolbar getActionBarToolbar() {
        if (mToolBar == null) {
            mToolBar = (Toolbar) findViewById(R.id.toolbar);
            if (mToolBar != null) {
                setSupportActionBar(mToolBar);
                ActionBar mActionBar = BaseActivity.this.getSupportActionBar();
                mActionBar.setDisplayShowHomeEnabled(false);
                mActionBar.setDisplayShowTitleEnabled(false);
                mActionBar.setDisplayShowCustomEnabled(true);

                // remove previously created actionbar
                mActionBar.invalidateOptionsMenu();

                /** remove actionbar unnecessary left margin */
                mToolBar.setContentInsetsAbsolute(0, 0);
            }
        }
        return mToolBar;
    }

    protected void showToast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_LONG).show();
    }
    protected void showTopSnackBar(String message, int bColor) {
        Snackbar snack = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackbarView = snack.getView();
        snackbarView.setBackgroundColor(bColor);
        snack.show();
    }

    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void showAlertDialog(String title, String message) {
        if(myAlertDialog != null)return;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(BaseActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myAlertDialog = null;
                    }
                });

        if (!BaseActivity.this.isFinishing())myAlertDialog = alertDialog.show();
    }

    protected DialogInterface.OnClickListener defaultDialogClickListener() {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        };
    }

    public void setProgressDialog(boolean isLoading, String msg) {
        try {
            if (isLoading) {
                if(msg == null || msg.isEmpty())msg = "Please wait";
                if (pd != null) pd.show();
                else pd = KProgressHUD.create(this)
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


    private void applicationWillEnterForeground() {
        if (isAppWentToBg) {
            isAppWentToBg = false;
        }
    }

    public void applicationdidenterbackground() {
        if (!isWindowFocused) {
            isAppWentToBg = true;
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart isAppWentToBg " + isAppWentToBg);
        applicationWillEnterForeground();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop ");
        applicationdidenterbackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pd != null)pd.dismiss();
    }

    @Override
    public void onBackPressed() {

        if (this instanceof MainActivity) {
        } else {
            isBackPressed = true;
        }
        Log.d(TAG, "onBackPressed " + isBackPressed + "" + this.getLocalClassName());
        super.onBackPressed();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isWindowFocused = hasFocus;
        if (isBackPressed && !hasFocus) {
            isBackPressed = false;
            isWindowFocused = true;
        }

        super.onWindowFocusChanged(hasFocus);
    }

}
