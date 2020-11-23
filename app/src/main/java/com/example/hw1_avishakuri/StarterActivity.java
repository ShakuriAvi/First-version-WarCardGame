package com.example.hw1_avishakuri;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class StarterActivity extends AppCompatActivity {
    private Button btnGame;
    private Button btnCharacters;
    private Button btnTopTen;
    private boolean selectPlayer = false;
    private Player player1;
    private Player player2;
    private Characters theCharacters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);
        initView();
        initCharacters();
        clickOnButton();

    }

    private void clickOnButton() {
        btnGame.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(StarterActivity.this, MainActivity.class);
                intent.putExtra("EXTRA_KEY_MY_CHARACTERS", theCharacters);
                intent.putExtra("EXTRA_KEY_MY_PLAYER1",player1);
                intent.putExtra("EXTRA_KEY_MY_PLAYER2",player2);
                startActivity(intent);

            }
        });
        btnCharacters.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                selectPlayer = true;
                playSound(R.raw.tiny_button_push);
                Intent intent = new Intent(StarterActivity.this, SelectCharactersActivity.class);
                intent.putExtra("EXTRA_KEY_MY_CHARACTERS", (Serializable) theCharacters);
                player1 =  (Player) getIntent().getSerializableExtra("EXTRA_KEY_MY_PLAYER1");
                player2 =  (Player) getIntent().getSerializableExtra("EXTRA_KEY_MY_PLAYER2");
            }
        });
        btnTopTen.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                playSound(R.raw.tiny_button_push);

            }
        });
    }

    private void initView() {
        btnGame = findViewById(R.id.starter_BTN_playGame);
        btnCharacters = findViewById(R.id.starter_BTN_Characters);
        btnTopTen = findViewById(R.id.starter_BTN_showTopTen);
    }
    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(this,id);
        mp.start();
    }
    private void initCharacters() {//return Characters for init Characters Player in class game. I cant init the Characters in Game becuase there is UI

        theCharacters = new Characters();
        theCharacters.getPlayersArray().add(new Player("don quixote",0));
        initViewCharc(R.drawable.don_quixote,theCharacters.getPlayersArray().get(0));
        theCharacters.getPlayersArray().add(new Player("cowgirl",0));
        initViewCharc(R.drawable.cowgirl,theCharacters.getPlayersArray().get(1));
        theCharacters.getPlayersArray().add(new Player("teacher",0));
        initViewCharc(R.drawable.teacher,theCharacters.getPlayersArray().get(2));
        theCharacters.getPlayersArray().add(new Player("man",0));
        initViewCharc(R.drawable.man,theCharacters.getPlayersArray().get(3));
        theCharacters.getPlayersArray().add(new Player("business man",0));
        initViewCharc(R.drawable.business_man,theCharacters.getPlayersArray().get(4));
        theCharacters.getPlayersArray().add(new Player("old",0));
        initViewCharc(R.drawable.old_man,theCharacters.getPlayersArray().get(5));
        theCharacters.getPlayersArray().add(new Player("ninja",0));
        initViewCharc(R.drawable.ninja,theCharacters.getPlayersArray().get(6));
        theCharacters.getPlayersArray().add(new Player("king",0));
        initViewCharc(R.drawable.king,theCharacters.getPlayersArray().get(7));
        theCharacters.getPlayersArray().add(new Player("banshee",0));
        initViewCharc(R.drawable.banshee,theCharacters.getPlayersArray().get(8));
    }
    private void initViewCharc(int id, Player newPlayer) {
        newPlayer.setIdImage(id);
    }
}
