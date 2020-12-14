package com.example.hw1_avishakuri.Activity;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hw1_avishakuri.Other.MyScreenUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            MyScreenUtils.hideSystemUI(this);
        }
    }
    protected boolean isDoubleBackPressToClose = false;
    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    @Override
    public void onBackPressed() { // for return back click twice and show message to user
        if (isDoubleBackPressToClose) {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            }
            else {
                Toast.makeText(this, "Tap back button again to exit", Toast.LENGTH_SHORT).show();
            }

            mBackPressed = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
