package com.example.hw1_avishakuri;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//ArrayList<Integer> imageViewArray;
int temp = 0;
private final int numOfPlayer = 2;
private Package thePackage;
private Characters theCharacters;
ImageView click;
Player player1;
Player player2;
TextView txtViewScorePlayer1;
int score1;
TextView txtViewScorePlayer2;
int score2;
ImageView firstImagePlayer;
ImageView secondImagePlayer;
ImageView firstBackCardImage;
ImageView secondBackCardImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d("try1","success");
        game();


    }

    private void game() {
        initPackageCard();

        initCharacters();

        initPlayers();

        initView();

        initGame();


    }

    private void initView() {
        click = findViewById(R.id.main_IMG_playButton);
        firstImagePlayer = findViewById(R.id.main_IMG_firstPlayer);
        firstImagePlayer.setImageResource(player1.getIdImage());
        secondImagePlayer = findViewById(R.id.main_IMG_secondPlayer);
        secondImagePlayer.setImageResource(player2.getIdImage());
        firstBackCardImage = findViewById(R.id.main_IMG_firstBackCard);
        secondBackCardImage = findViewById(R.id.main_IMG_secondBackCard);
        txtViewScorePlayer1 = findViewById(R.id.main_TXT_firstScore);
        txtViewScorePlayer2 = findViewById(R.id.main_TXT_secondPlayer);
        score1 = 0;
        score2=0;
    }

    private void initGame() {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(thePackage.getPackage().size()==0)
                {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    int idWin = score1 > score2 ? player1.getIdImage() :  player2.getIdImage();
                    int scoreWin = score1 > score2 ? score1 : score2;
                    intent.putExtra("EXTRA_KEY_ID_IMAGE",(idWin));
                    intent.putExtra("EXTRA_KEY_SCORE",scoreWin);
                    startActivity(intent);
                    finish();
                }
                else {
                    Card choiceCard1 = randomCard();
                    Card choiceCard2 = randomCard();
                    Log.d("try0"," "+ choiceCard1.getValue() + " " +choiceCard2.getValue() + " card " + (52-temp));
                    viewCard(choiceCard1, choiceCard2);
                    rulesGame(choiceCard1, choiceCard2);
                }
            }
        });
    }

    private void viewCard(Card choiceCard1,Card choiceCard2) {

        firstBackCardImage.setImageResource(choiceCard1.getIdSymbol());
        secondBackCardImage.setImageResource(choiceCard2.getIdSymbol());
    }

    private void rulesGame(Card choiceCard1, Card choiceCard2) {

        if(choiceCard1.getValue() > choiceCard2.getValue())
            txtViewScorePlayer1.setText(" "+ ++score1);
        else if(choiceCard1.getValue() < choiceCard2.getValue())
            txtViewScorePlayer2.setText(" " + ++score2);

    }


    private Card randomCard() {
        Card choiceCard;
        Random rand1 = new Random();
        int randChoice = thePackage.getPackage().size()==1? 0 : rand1.nextInt((thePackage.getPackage().size()-1));//choice package
        choiceCard = thePackage.getPackage().get(randChoice);
        thePackage.getPackage().remove(randChoice);
    temp++;
        return choiceCard;
    }



    private void initPlayers() {
      player1=  randomPlayer();
      player2 = randomPlayer();
    }

    private Player randomPlayer() {
            Random rand1 = new Random();
            int randChoice = rand1.nextInt(theCharacters.getPlayersArray().size()-1);
            Player newPlayer = theCharacters.getPlayersArray().get(randChoice);
            theCharacters.getPlayersArray().remove(randChoice);

            return newPlayer;
    }

    private void initCharacters() {

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
        theCharacters.getPlayersArray().add(new Player("old man",0));
        initViewCharc(R.drawable.old_man,theCharacters.getPlayersArray().get(5));
    }
    private void initViewCharc(int id, Player newPlayer) {
        newPlayer.setIdImage(id);
    }

    private void initPackageCard() {
        thePackage = new Package();
    int t = 0;
        Character[] ch = {'c', 'd', 'h', 's'};
        for (int i = 2; i < 15; i++) {
            for (int j = 0; j < ch.length; j++) {
                Card newCard = new Card(i);
                String name = "poker_" + (i) + "_" + ch[j];
                int id = getResources().getIdentifier(name, "drawable",this.getPackageName());
                initViewCard(id,newCard);
                thePackage.setCardInPack(newCard);
            }
        }

    }

    private void initViewCard(int id,Card newCard) {
        newCard.setIdSymbol(id);
    }

}

