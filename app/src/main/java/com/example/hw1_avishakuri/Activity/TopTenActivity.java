package com.example.hw1_avishakuri.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Fragment.Fragment_List;
import com.example.hw1_avishakuri.Fragment.Fragment_Map;
import com.example.hw1_avishakuri.Other.CallBack_Top;
import com.example.hw1_avishakuri.Other.MyScreenUtils;
import com.example.hw1_avishakuri.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TopTenActivity extends BaseActivity {

    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;
    private Gson gson;
    private ArrayList<Player> topTenWinner;
    private Button topTen_BTN_return;
    public static String PACKAGE_NAME;
    private boolean boolAutoGame;
    private boolean boolSound;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        MyScreenUtils.hideSystemUI(this);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        boolAutoGame = getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound = getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
        clickOnButton();
        getListTopTen();
        fragment_list = new Fragment_List(topTenWinner);
        fragment_list.setCallBack_top(callBack_top);
        fragment_map = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_list, fragment_list).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_map,fragment_map).commit();


    }

    private void clickOnButton() {//return to menu and save the status of setting
        topTen_BTN_return = findViewById(R.id.topTen_BTN_return);
        topTen_BTN_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boolSound == true)
                    playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(TopTenActivity.this, MenuActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                startActivity(intent);
            }
        }
        );
    }

    private void getListTopTen() {//get from menu or finish activity the top ten list from memory phone
        gson = new Gson();
        String json =  getIntent().getStringExtra("EXTRA_KEY_WINNERS");
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        topTenWinner = gson.fromJson(json, type);

    }

    private CallBack_Top callBack_top = new CallBack_Top() {//move to fragment map location of user from fragment list

        @Override
        public void displayLocation(double lat, double lon) {
         //   Log.d("zzzz","change" + lat + " "+ lon);
            fragment_map.setLatLong(lat,lon);
        }
    };

    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(this,id);
        mp.start();
    }

}
