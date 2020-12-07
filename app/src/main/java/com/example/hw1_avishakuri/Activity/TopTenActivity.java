package com.example.hw1_avishakuri.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hw1_avishakuri.Controller.CallBack_Top;
import com.example.hw1_avishakuri.Fragment.Fragment_List;
import com.example.hw1_avishakuri.Fragment.Fragment_Map;
import com.example.hw1_avishakuri.Class.Player;
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
    private Button btnReturn;
    public static String PACKAGE_NAME;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        clickOnButton();
        PACKAGE_NAME = getApplicationContext().getPackageName();
        getListTopTen();
        fragment_list = new Fragment_List(topTenWinner);
        fragment_list.setCallBack_top(callBack_top);
        fragment_map = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_list, fragment_list).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.topTen_LAY_map,fragment_map).commit();


    }

    private void clickOnButton() {
        btnReturn = findViewById(R.id.topTen_BTN_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopTenActivity.this, StarterActivity.class);
                startActivity(intent);
            }
        }
        );
    }

    private void getListTopTen() {
        gson = new Gson();
        String json =  getIntent().getStringExtra("EXTRA_KEY_WINNERS");
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        topTenWinner = gson.fromJson(json, type);

    }

    private CallBack_Top callBack_top = new CallBack_Top() {

        @Override
        public void displayLocation(double lat, double lon) {
            Log.d("zzzz","change" + lat + " "+ lon);
            fragment_map.setLatLong(lat,lon);
        }
    };



}
