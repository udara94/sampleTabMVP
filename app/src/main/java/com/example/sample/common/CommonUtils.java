package com.example.sample.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.sample.BaseApplication;
import com.example.sample.common.constants.ApplicationConstants;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CommonUtils {

    private static CommonUtils instance = null;

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;

    private CommonUtils() {}

    public static CommonUtils getInstance() {
        if (instance == null) instance = new CommonUtils();
        return instance;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) BaseApplication.getBaseApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public SimpleDateFormat getDateTimeFormatter() {
        return new SimpleDateFormat(ApplicationConstants.DEFAULT_DATE_AND_TIME_FORMAT, Locale.getDefault());
    }

    public Typeface getFont(Context mContext, String font) {
        switch (font) {
            case ApplicationConstants.FONT_ROBOTO:
                return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto.ttf");
            case ApplicationConstants.FONT_ROBOTO_BOLD:
                return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_bold.ttf");
            case ApplicationConstants.FONT_ROBOTO_MEDIUM:
                return Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_medium.ttf");
            case ApplicationConstants.FONT_LOBSTER:
                return Typeface.createFromAsset(mContext.getAssets(), "fonts/lobster.ttf");
            case ApplicationConstants.FONT_LOBSTER_REGULAR:
                return Typeface.createFromAsset(mContext.getAssets(), "fonts/lobster_regular.ttf");
            default:
                return null;
        }
    }
}
