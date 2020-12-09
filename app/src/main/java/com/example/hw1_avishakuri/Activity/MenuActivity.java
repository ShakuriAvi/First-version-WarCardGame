package com.example.hw1_avishakuri.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.R;
import com.example.hw1_avishakuri.Controller.InitGameViewController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hw1_avishakuri.Controller.Constants.SP_FILE;

public class MenuActivity extends BaseActivity {
    private InitGameViewController initGameViewController;
    private Button menu_BTN_game;
    private Button menu_BTN_characters;
    private Button menu_BTN_topTen;
    private Button menu_BTN_setting;
    private Player player1;
    private Player player2;
    private boolean boolAutoGame ;
    private boolean boolSound;
    private Gson gson;
    private ImageView menu_IMG_background;
    private ArrayList<Player> topTenWinner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        isDoubleBackPressToClose = true;
        loadData();
        gson = new Gson();
        initView();
        clickOnButton();
        initFromOtherActivity();

    }


    private void initFromOtherActivity() {
        initPlayerFromActivitySelectCharc();
        initSettingFromActivitySetting();
    }

    private void initSettingFromActivitySetting() {
        boolAutoGame = getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound = getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
    }

    private void initPlayerFromActivitySelectCharc() {

        String p1 = getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER1");
        player1 = gson.fromJson(p1,Player.class);
        String p2 = getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER2");
        player2 = gson.fromJson(p2,Player.class);
    }

    private void clickOnButton() {
        menu_BTN_game.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);

                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                Gson gson = new Gson();
                String p1 = gson.toJson(player1);
                String p2 = gson.toJson(player2);
                intent.putExtra("EXTRA_KEY_MY_PLAYER1",p1);
                intent.putExtra("EXTRA_KEY_MY_PLAYER2",p2);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                startActivity(intent);

            }
        });
        menu_BTN_characters.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(MenuActivity.this, SelectCharactersActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                startActivity(intent);
            }
        });
        menu_BTN_topTen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(MenuActivity.this, TopTenActivity.class);
                String arrayTopTen = gson.toJson(topTenWinner);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                intent.putExtra("EXTRA_KEY_WINNERS", arrayTopTen);
                startActivity(intent);
            }
        });
        menu_BTN_setting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(MenuActivity.this, SettingActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        menu_BTN_game = findViewById(R.id.menu_BTN_playGame);
        menu_BTN_characters = findViewById(R.id.menu_BTN_Characters);
        menu_BTN_topTen = findViewById(R.id.menu_BTN_showTopTen);
        menu_BTN_setting = findViewById(R.id.menu_BTN_setting);
        menu_IMG_background = findViewById(R.id.menu_IMG_background);
        Glide
                .with(this)
               .load(R.drawable.background_field)
                .into(menu_IMG_background);

    }
    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(this,id);
        mp.start();
    }
    private void loadData() {
        SharedPreferences prefs = getSharedPreferences(SP_FILE,MODE_PRIVATE);
        String json = prefs.getString("topTenArr",null);
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        Gson gson = new Gson();
//       SharedPreferences.Editor editor = prefs.edit();
//       editor.clear();
//       editor.commit();
        topTenWinner = gson.fromJson(json, type);
        if(topTenWinner == null){
            initArrayList();
        }

    }

    private void initArrayList() {
        topTenWinner = new ArrayList<Player>();
        for (int i = 0; i < 10; i++) {
            topTenWinner.add(i,null);
        }
    }

}
