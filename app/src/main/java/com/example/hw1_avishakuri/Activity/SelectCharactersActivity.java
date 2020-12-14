package com.example.hw1_avishakuri.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hw1_avishakuri.Class.Characters;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Other.MyScreenUtils;
import com.example.hw1_avishakuri.R;
import com.example.hw1_avishakuri.Controller.InitGameViewController;
import com.google.gson.Gson;

public class SelectCharactersActivity extends BaseActivity {
private InitGameViewController initGameViewController;
private Characters tempCharc;
private Player player1;
private  Player player2;
private  boolean turnChoice = true;// if true turn player1 choose charc else turn player2 choose
private ImageView selectCharc_IMG_player1;
private ImageView selectCharc_IMG_player2;
private Button selectCharc_BTN_return;
private Gson gson ;
private boolean boolSound = true;
private boolean boolAutoGame = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_characters);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        isDoubleBackPressToClose=true;
        initGameViewController = new InitGameViewController(this);
        initGameViewController.initCharcacters();
        tempCharc = initGameViewController.getTheCharacters();
        initView();
        selectCharc_BTN_return.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                returnListiner();
            }
        });
    }

    private void returnListiner() {//return to page menu move the choice of player that user want
        if(boolSound)
            playSound(R.raw.tiny_button_push);
        gson= new Gson();
        Intent intent = new Intent( SelectCharactersActivity.this, MenuActivity.class);
        String p1 = gson.toJson(player1);
        String p2 = gson.toJson(player2);
        intent.putExtra("EXTRA_KEY_MY_PLAYER1",p1);
        intent.putExtra("EXTRA_KEY_MY_PLAYER2",p2);
        intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
        intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
        startActivity(intent);
    }

    private void initView() {
        selectCharc_IMG_player1 = findViewById(R.id.starter_IMG_player1);
        selectCharc_IMG_player2 = findViewById(R.id.starter_IMG_player2);
        selectCharc_BTN_return = findViewById(R.id.starter_BTN_return);
        boolAutoGame =getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound=getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
    }


    @SuppressLint("ResourceType")
    public void clickOnImage(View view){//choice the characters and show them to user
        if(boolSound)
            playSound(R.raw.tiny_button_push);
        ImageView img = (ImageView)view;
        String name = (String)img.getTag();
        if (turnChoice == true) {
            player1 = tempCharc.getPlayerByName(name);
            selectCharc_IMG_player1.setImageResource(player1.getIdImage());
            turnChoice = false;
        }
        else {
            player2 = tempCharc.getPlayerByName(name);
            selectCharc_IMG_player2.setImageResource(player2.getIdImage());
            turnChoice = true;
        }

    }
    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(this,id);
        mp.start();
    }
}
