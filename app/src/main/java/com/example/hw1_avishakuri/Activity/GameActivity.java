package com.example.hw1_avishakuri.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.example.hw1_avishakuri.Class.Game;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Controller.InitGameViewController;
import com.example.hw1_avishakuri.R;
import com.google.gson.Gson;

public class GameActivity extends BaseActivity {
private Gson gson;
private Game game;//the rule of game: move two card, choose random player,etc...
private InitGameViewController initGameViewController;
private boolean boolSound;
private boolean boolAutoGame;
private boolean boolOnStop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        isDoubleBackPressToClose=true;
        initGame();
        initGameViewController.initView(this, game);
        initGameViewController.clickOnPlay(boolAutoGame, boolSound);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(initGameViewController.isClickled() && boolAutoGame == true)
            initGameViewController.startCounting();

    }


    private void initGame() {
        gson = new Gson();
        String p1 = getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER1");
        Player player1 = gson.fromJson(p1,Player.class);
        String p2 = getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER2");
        Player player2 = gson.fromJson(p2,Player.class);
        boolAutoGame = getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound = getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
        initGameViewController = new InitGameViewController(this);
        initGameViewController.initCharcacters();
        game = new Game(initGameViewController.getTheCharacters(), initGameViewController.getPackageCard(this),player1,player2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(boolAutoGame==true)
            initGameViewController.stopCounting();
    }
}