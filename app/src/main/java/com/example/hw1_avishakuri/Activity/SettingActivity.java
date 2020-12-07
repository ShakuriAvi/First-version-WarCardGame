package com.example.hw1_avishakuri.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.hw1_avishakuri.R;

public class SettingActivity extends BaseActivity {

    private Switch switchAutoGame;
    private boolean boolAutoGame = true;
    private Switch switchSound;
    private boolean boolSound = true;
    private Button btnReturn;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        clickOnButton();
    }

    private void clickOnButton() {
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view){
            Intent intent = new Intent(SettingActivity.this, StarterActivity.class);
            intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
            intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
            startActivity(intent);
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == false)
                {
                    switchSound.setChecked(false);
                    boolSound=false;
                }
                else {
                    boolSound = true;
                    switchSound.setChecked(true);
                }
            }
        });

        switchAutoGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == false)
                {
                    switchAutoGame.setChecked(false);
                    boolAutoGame=false;
                }
                else {
                    switchAutoGame.setChecked(true);
                    boolAutoGame = true;
                }
            }
        });
    }

    private void initView() {
        switchAutoGame = findViewById(R.id.setting_SWITCH_autoGame);
        switchAutoGame.setChecked(getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true));
        switchSound = findViewById(R.id.setting_SWITCH_sound);
        switchSound.setChecked(getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true));
        btnReturn = findViewById(R.id.setting_BTN_return);
    }

}
