package com.example.hw1_avishakuri.Controller;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Activity.GameActivity;
import com.example.hw1_avishakuri.Activity.SelectCharactersActivity;
import com.example.hw1_avishakuri.Activity.SettingActivity;
import com.example.hw1_avishakuri.Activity.TopTenActivity;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.hw1_avishakuri.Other.Constants.SP_FILE;

public class MenuGameViewController {
    private AppCompatActivity activity;
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

    public MenuGameViewController(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void initMenu(){
        loadData();
        gson = new Gson();
        askLocationPermission();
        initView();
        clickOnButton();
        initFromOtherActivity();
    }
    private void askLocationPermission(){//ask from user if we can get permissiom to use his location..If he did not agree every time he entered the menu it would ask him again
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d("bbbb", "ask location permission " );
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10001);
            }else{
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10001);
            }
        }
    }
    private void initFromOtherActivity() {
        initPlayerFromActivitySelectCharc();
        initSettingFromActivitySetting();
    }

    private void initSettingFromActivitySetting() {
        boolAutoGame = activity.getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound = activity.getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
    }

    private void initPlayerFromActivitySelectCharc() {//get player that choice from menu

        String p1 = activity.getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER1");
        player1 = gson.fromJson(p1,Player.class);
        String p2 = activity.getIntent().getStringExtra("EXTRA_KEY_MY_PLAYER2");
        player2 = gson.fromJson(p2,Player.class);
    }

    private void clickOnButton() {
        menu_BTN_game.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);

                Intent intent = new Intent(activity, GameActivity.class);
                Gson gson = new Gson();
                String p1 = gson.toJson(player1);
                String p2 = gson.toJson(player2);
                intent.putExtra("EXTRA_KEY_MY_PLAYER1",p1);
                intent.putExtra("EXTRA_KEY_MY_PLAYER2",p2);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);

            }
        });
        menu_BTN_characters.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(activity, SelectCharactersActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);
            }
        });
        menu_BTN_topTen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(activity, TopTenActivity.class);
                String arrayTopTen = gson.toJson(topTenWinner);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                intent.putExtra("EXTRA_KEY_WINNERS", arrayTopTen);
                activity.startActivity(intent);
            }
        });
        menu_BTN_setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(activity, SettingActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);
            }
        });
    }

    private void initView() {
        menu_BTN_game = activity.findViewById(R.id.menu_BTN_playGame);
        menu_BTN_characters = activity.findViewById(R.id.menu_BTN_Characters);
        menu_BTN_topTen = activity.findViewById(R.id.menu_BTN_showTopTen);
        menu_BTN_setting = activity.findViewById(R.id.menu_BTN_setting);
        menu_IMG_background = activity.findViewById(R.id.menu_IMG_background);
        Glide
                .with(activity)
                .load(R.drawable.background_field)
                .into(menu_IMG_background);

    }
    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(activity,id);
        mp.start();
    }
    private void loadData() {
        SharedPreferences prefs = activity.getSharedPreferences(SP_FILE,activity.MODE_PRIVATE);
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
    private void initArrayList() {//if the list is null
        topTenWinner = new ArrayList<Player>();
        for (int i = 0; i < 10; i++) {
            topTenWinner.add(i,null);
        }
    }
}
