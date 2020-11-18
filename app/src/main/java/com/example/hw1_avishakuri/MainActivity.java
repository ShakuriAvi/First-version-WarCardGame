package com.example.hw1_avishakuri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
ImageView click;
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
     //   Log.d("try1","success");
        Game game = new Game( initCharacters(),initPackageCard());
        initView(game);
        clickOnPlay(game);

    }



    private void initView(Game game) {
        click = findViewById(R.id.main_IMG_playButton);
        firstImagePlayer = findViewById(R.id.main_IMG_firstPlayer);
        firstImagePlayer.setImageResource(game.getPlayer1().getIdImage());
        secondImagePlayer = findViewById(R.id.main_IMG_secondPlayer);
        secondImagePlayer.setImageResource(game.getPlayer2().getIdImage());
        firstBackCardImage = findViewById(R.id.main_IMG_firstBackCard);
        secondBackCardImage = findViewById(R.id.main_IMG_secondBackCard);
        txtViewScorePlayer1 = findViewById(R.id.main_TXT_firstScore);
        txtViewScorePlayer2 = findViewById(R.id.main_TXT_secondPlayer);
        score1 = 0;
        score2=0;
    }

    private void clickOnPlay(Game game) {
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(game.getThePackage().getCards().size()==0)
                {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    int idWin = score1 > score2 ?game.getPlayer1().getIdImage() :  game.getPlayer2().getIdImage();
                    int scoreWin = score1 > score2 ? score1 : score2;
                    intent.putExtra("EXTRA_KEY_ID_IMAGE",(idWin));
                    intent.putExtra("EXTRA_KEY_SCORE",scoreWin);
                    startActivity(intent);
                    finish();
                }
                else {
                    int result = game.play();
                    Log.d("try0"," "+ game.getChoiceCard1().getValue() + " " +game.getChoiceCard2().getValue() + " card " );
                    viewCard(game.getChoiceCard1(), game.getChoiceCard2());
                    rulesGame(result);
                }
            }
        });
    }

    private void viewCard(Card choiceCard1,Card choiceCard2) {

        firstBackCardImage.setImageResource(choiceCard1.getIdSymbol());
        secondBackCardImage.setImageResource(choiceCard2.getIdSymbol());
    }

    private void rulesGame(int result) {

        if(result == 1)
            txtViewScorePlayer1.setText(" "+ ++score1);
        else if(result == 2)
            txtViewScorePlayer2.setText(" " + ++score2);

    }


    private Characters initCharacters() {

        Characters theCharacters = new Characters();
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
        return theCharacters;
    }
    private void initViewCharc(int id, Player newPlayer) {
        newPlayer.setIdImage(id);
    }

    private Package initPackageCard() {
        Package thePackage = new Package();
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
            return  thePackage;
    }

    private void initViewCard(int id,Card newCard) {
        newCard.setIdSymbol(id);
    }

}

