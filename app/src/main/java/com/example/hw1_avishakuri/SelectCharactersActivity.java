package com.example.hw1_avishakuri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectCharactersActivity extends AppCompatActivity {
private  Characters tempCharc;
private  Player player1;
private  Player player2;
private  boolean turnChoice = true;// if true turn player1 choose charc else turn player2 choose
private ImageView imgPlayer1;
private ImageView imgPlayer2;
private Button btnReturn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_characters);
        Characters tempCharc = (Characters) getIntent().getSerializableExtra("EXTRA_KEY_MY_CHARACTERS");
        initView();
        btnReturn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( SelectCharactersActivity.this,StarterActivity.class);
                intent.putExtra("EXTRA_KEY_MY_PLAYER1",player1);
                intent.putExtra("EXTRA_KEY_MY_PLAYER2",player2);
            }
        });
    }

    private void initView() {
        imgPlayer1 = findViewById(R.id.starter_IMG_player1);
        imgPlayer2 = findViewById(R.id.starter_IMG_player2);
        btnReturn = findViewById(R.id.starter_BTN_return);
    }

    public void clickOnImage(View view){
        ImageView img = (ImageView)view;
        String name = splitTheName( String.valueOf(img.getTag()));
        if (turnChoice == true) {
            player1 = tempCharc.getPlayerByName(name);
            imgPlayer1=img;
            turnChoice = false;
        }
        else {
            player2 = tempCharc.getPlayerByName(name);
            imgPlayer2 = img;
            turnChoice = true;
        }

    }

    private String splitTheName(String valueOf) {
        String[] split = valueOf.split("_");
        return split[2];
    }
}
