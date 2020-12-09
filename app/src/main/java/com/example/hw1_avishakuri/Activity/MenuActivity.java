package com.example.hw1_avishakuri.Activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.hw1_avishakuri.Controller.MenuGameViewController;
import com.example.hw1_avishakuri.R;

public class MenuActivity extends BaseActivity {
    private MenuGameViewController menuGameViewController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        isDoubleBackPressToClose = true;
        menuGameViewController = new MenuGameViewController(this);
        menuGameViewController.initMenu();
    }

}