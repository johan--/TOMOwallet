package com.tomoapp.tomowallet.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tomoapp.tomowallet.R;

/**
 * Created by macbook on 12/21/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected MainApplication application;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        application = MainApplication.get();
        MainApplication.setCurrentActivity(this);
    }


    protected void setCurrentActivity(BaseActivity activity){
        MainApplication.setCurrentActivity(activity);
    }

    protected void showLoadingDialog(){

    }


    protected void hideLoadingDialog(){

    }

    protected MainApplication getCurrentApplication() {
        return application == null ? MainApplication.get() : application;
    }

    public void setStatusbarColor(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(color));
        }
    }

    public void setStatusbarDefaultColor(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}
