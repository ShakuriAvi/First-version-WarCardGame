package com.example.hw1_avishakuri.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.hw1_avishakuri.R;

public class SettingActivity extends BaseActivity {

    private Switch settinf_SWITCH_autoGame;
    private boolean boolAutoGame ;
    private Switch setting_SWITCH_sound;
    private boolean boolSound ;
    private Button setting_BTN_return;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MyScreenUtils.hideSystemUI(this);
        initView();
        clickOnButton();
    }

    private void clickOnButton() {
        setting_BTN_return.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view){
            Intent intent = new Intent(SettingActivity.this, MenuActivity.class);
            intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
            intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
            startActivity(intent);
            }
        });

        setting_SWITCH_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == false)
                {
                    setting_SWITCH_sound.setChecked(false);
                    boolSound=false;
                }
                else {
                    boolSound = true;
                    setting_SWITCH_sound.setChecked(true);
                }
            }
        });

        settinf_SWITCH_autoGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == false)
                {
                    settinf_SWITCH_autoGame.setChecked(false);
                    boolAutoGame=false;
                }
                else {
                    settinf_SWITCH_autoGame.setChecked(true);
                    boolAutoGame = true;
                }
            }
        });
    }

    private void initView() {
        settinf_SWITCH_autoGame = findViewById(R.id.setting_SWITCH_autoGame);
        settinf_SWITCH_autoGame.setChecked(getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true));
        boolAutoGame = getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        setting_SWITCH_sound = findViewById(R.id.setting_SWITCH_sound);
        setting_SWITCH_sound.setChecked(getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true));
        boolSound = (getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true));
        setting_BTN_return = findViewById(R.id.setting_BTN_return);
    }

}
