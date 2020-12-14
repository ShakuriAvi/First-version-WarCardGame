package com.example.hw1_avishakuri.Controller;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Activity.GameActivity;
import com.example.hw1_avishakuri.Activity.MenuActivity;
import com.example.hw1_avishakuri.Activity.TopTenActivity;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.hw1_avishakuri.Other.Constants.SP_FILE;

public class FinishGameViewController {

    private Gson gson;
    private ImageView finish_IMG_playerWin;
    private TextView finish_TXT_scorePlayerWin;
    private Button finish_BTN_newGame;
    private Button finish_BTN_menu;
    private Button finish_BTN_topTen;
    private Button finish_BTN_saveName;
    private ImageView finish_IMG_background;
    private EditText finish_TXT_replaceName;
    Player playerWin;
    private ArrayList<Player> topTenWinner;
    private FusedLocationProviderClient client;
    private Context context;
    private AppCompatActivity activity;
    private boolean boolAutoGame ;
    private boolean boolSound ;
    private  Location location;

    public FinishGameViewController(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void playActivity(){
        gson = new Gson();
        loadData();
        findView();
        getPlayerWinFromOtherActivity();
        clickOnButton();
    }

    private void findView() {
        finish_IMG_playerWin = activity.findViewById(R.id.finish_IMG_winner);
        finish_TXT_scorePlayerWin = activity.findViewById(R.id.finish_TXT_score);
        finish_BTN_newGame = activity.findViewById(R.id.finish_BTN_newGame);
        finish_BTN_menu = activity.findViewById(R.id.finish_BTN_menu);
        finish_BTN_topTen = activity.findViewById(R.id.finish_BTN_topTen);
        finish_IMG_background = activity.findViewById(R.id.finish_IMG_background);
        finish_TXT_replaceName = activity.findViewById(R.id.finish_TXT_replaceName);
        finish_BTN_saveName = activity.findViewById(R.id.finish_BTN_saveName);
        Glide
                .with(activity)
                .load(R.drawable.gaming_dice_cards_casino_chips_dark_background)
                .centerCrop()
                .into(finish_IMG_background);
    }

    private  void loadData() {//load the last list from memory phone
        SharedPreferences prefs = activity.getSharedPreferences(SP_FILE, activity.MODE_PRIVATE);
        String json = prefs.getString("topTenArr", null);
        Type type = new TypeToken<ArrayList<Player>>() {
        }.getType();
        topTenWinner = gson.fromJson(json, type);
        if (topTenWinner == null) {
            initArrayList();
        }


    }
    private void initArrayList() {
        topTenWinner = new ArrayList<Player>();
        for (int i = 0; i < 10; i++) {
            topTenWinner.add(i, null);
        }
    }

    private void getPlayerWinFromOtherActivity() {//get from the game class the status of setting and details of winner
        boolAutoGame = activity.getIntent().getBooleanExtra("EXTRA_KEY_MY_AutoGame",true);
        boolSound = activity.getIntent().getBooleanExtra("EXTRA_KEY_MY_SoundPlay",true);
        String p1 = activity.getIntent().getStringExtra("EXTRA_KEY_WINNER");
        playerWin = gson.fromJson(p1,Player.class);
        finish_IMG_playerWin.setImageResource(playerWin.getIdImage());
        finish_TXT_scorePlayerWin.setText("The Score : "+ playerWin.getScore());
        getLocationOfUser();
        changeName();
        addPlayerToArr();
    }
    private void getLocationOfUser() {//gwt from user current location and check if status of location open if no thr defult is 0,0 and check another things....

        context = activity.getApplicationContext();
        client = LocationServices.getFusedLocationProviderClient(activity);
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
             getLastLocation();
            if(location!=null)
            playerWin.setLocation(location.getLatitude(),location.getLongitude());
            else
                playerWin.setLocation(0,0);
        } else {
            playerWin.setLocation(0,0);
            Log.d("cccc", " " + playerWin.getLongitude() + playerWin.getLatitude());
        }
    }
    private void getLastLocation() {
        try {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                location = null;
            }
            Location l = locationManager.getLastKnownLocation(provider);


            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            location = null;
        }
            location= bestLocation;
        }catch (Exception e){
            location = new Location("");
            location.setLatitude(0);
            location.setLongitude(0);
        }

    }



    private void changeName() {// save the name of user give the winner if the user dont change the name, each player stay with defult his name
        finish_BTN_saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!finish_TXT_replaceName.getText().toString().equals("Name") && !finish_TXT_replaceName.getText().toString().equals(null)){
                     playerWin.setName(finish_TXT_replaceName.getText().toString());
            }
        }
    });
    }
    private void addPlayerToArr() {//sorted and add new player to list of player
        for (int i = 0; i <10 ; i++) {
            if(topTenWinner.get(i) == null) {
                topTenWinner.add(i,playerWin);
                break;
            }
            else if(playerWin.getScore() > topTenWinner.get(i).getScore()) {
                swap(i , playerWin);
                break;
            }
        }
    }
    private void swap(int position , Player newPlayer) {
        Player temp = topTenWinner.get(position);
        Player temp2 = null;
        topTenWinner.add(position, playerWin);
        for (int j = position+1; j > 10; j++) {
            temp2 = topTenWinner.get(j);
            topTenWinner.add(j, temp);
            temp = temp2;
        }
    }
    public void clickOnButton() {
        finish_BTN_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(activity, GameActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);
               activity.finish();
            }
        });
        finish_BTN_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(activity, MenuActivity.class);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);
               activity.finish();
            }
        });
        finish_BTN_topTen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(activity, TopTenActivity.class);
                activity.startActivity(intent);
                gson = new Gson();
                String arrayTopTen = gson.toJson(topTenWinner);
                intent.putExtra("EXTRA_KEY_WINNERS", arrayTopTen);
                intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
                intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }
    private void saveData() {//save the last top ten list after sort

        SharedPreferences prefs = activity.getSharedPreferences(SP_FILE,activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(topTenWinner);
        editor.putString("topTenArr" , json);
        editor.apply();
    }
}
