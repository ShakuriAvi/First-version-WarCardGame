package com.example.hw1_avishakuri.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.example.hw1_avishakuri.Controller.FinishGameViewController;
import com.example.hw1_avishakuri.R;

public class FinishActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finish);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        isDoubleBackPressToClose = true;
        FinishGameViewController finishGameViewController = new FinishGameViewController(this);
        finishGameViewController.playActivity();

    }


}