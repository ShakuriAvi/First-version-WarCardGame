package com.example.hw1_avishakuri.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.Controller.FinishGameViewController;
import com.example.hw1_avishakuri.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.hw1_avishakuri.Controller.Constants.SP_FILE;

public class FinishActivity extends BaseActivity {
    private Gson gson;
    private ImageView picPlayerWin;
    private TextView scorePlayerWin;
    private Button btnNewGame;
    private Button btnMenu;
    private Button btnTopTen;
    private Button btnSaveName;
    private ImageView imgBackground;
    private EditText txtReplaceName;
    Player playerWin;
    private ArrayList<Player> topTenWinner;
    private FusedLocationProviderClient client;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_finish);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        isDoubleBackPressToClose = true;
        gson = new Gson();
        FinishGameViewController finishGameViewController = new FinishGameViewController(this);
        finishGameViewController.playActivity();
       // finishGameViewController.clickOnButton();
   //     loadData();
//        findView();
     //   getPlayerWinFromOtherActivity();
       // clickOnButton();
    }

    private void initArrayList() {
        topTenWinner = new ArrayList<Player>();
        for (int i = 0; i < 10; i++) {
            topTenWinner.add(i,null);
        }
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences(SP_FILE,MODE_PRIVATE);
        String json = prefs.getString("topTenArr",null);
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        topTenWinner = gson.fromJson(json, type);
        if(topTenWinner == null){
            initArrayList();
        }

    }

    private void saveData() {

        SharedPreferences prefs = getSharedPreferences(SP_FILE,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String json = gson.toJson(topTenWinner);
        editor.putString("topTenArr" , json);
        editor.apply();
    }

    private void changeName() {
        btnSaveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerWin.setName(txtReplaceName.getText().toString());
            }
        });
    }

    private void getPlayerWinFromOtherActivity() {
        String p1 = getIntent().getStringExtra("EXTRA_KEY_WINNER");
        playerWin = gson.fromJson(p1,Player.class);
        picPlayerWin.setImageResource(playerWin.getIdImage());
        scorePlayerWin.setText("The Score : "+ playerWin.getScore());
        getLocationOfUser();
        changeName();
        addPlayerToArr();
        for (int i = 0; i <topTenWinner.size() ; i++) {
            if(topTenWinner.get(i)!=null)
            Log.d("cccc", " " + topTenWinner.get(i).getName() + " " + topTenWinner.get(i).getScore());
        }
    }



    private void addPlayerToArr() {
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


    private void clickOnButton() {
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishActivity.this, StarterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnTopTen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(FinishActivity.this, TopTenActivity.class);
                startActivity(intent);
                gson = new Gson();
                String arrayTopTen = gson.toJson(topTenWinner);
                intent.putExtra("EXTRA_KEY_WINNERS", arrayTopTen);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findView() {
        picPlayerWin = findViewById(R.id.finish_IMG_winner);
        scorePlayerWin = findViewById(R.id.finish_TXT_score);
        btnNewGame =findViewById(R.id.finish_BTN_newGame);
        btnMenu = findViewById(R.id.finish_BTN_menu);
        btnTopTen = findViewById(R.id.finish_BTN_topTen);
        imgBackground = findViewById(R.id.finish_IMG_background);
        txtReplaceName = findViewById(R.id.finish_TXT_replaceName);
        btnSaveName = findViewById(R.id.finish_BTN_saveName);
        Glide
                .with(this)
                .load(R.drawable.gaming_dice_cards_casino_chips_dark_background)
                .centerCrop()
                .into(imgBackground);
    }

    private void getLocationOfUser() {

        context = this.getApplicationContext();
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location =  getLastLocation();
            playerWin.setLocation(location.getLatitude(),location.getLongitude());
        } else {
            askLocationPermission();
        }
}
    private Location getLastLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
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
            return null;
        }
        return bestLocation;

    }
    private void askLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                Log.d("bbbb", "ask location permission " );
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10001);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},10001);
            }
        }
    }
}