package com.example.hw1_avishakuri.Controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hw1_avishakuri.Activity.FinishActivity;
import com.example.hw1_avishakuri.Class.Card;
import com.example.hw1_avishakuri.Class.Characters;
import com.example.hw1_avishakuri.Class.Game;
import com.example.hw1_avishakuri.Class.Package;
import com.example.hw1_avishakuri.Class.Player;
import com.example.hw1_avishakuri.R;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class InitGameViewController {
    private int temp = 0;//check num of cards its for me
    private double timeInPercent = 0;
    private AppCompatActivity activity;
    private Characters theCharacters;
    private ImageView game_IMG_button;//click on image for movw card
    private TextView game_TXT_scorePlayer1;
    private TextView game_TXT_scorePlayer2;
    private ImageView game_IMG_firstImagePlayer;
    private ImageView game_IMG_secondImagePlayer;
    private ImageView game_IMG_firstBackCardImage;
    private ImageView game_IMG_secondBackCardImage;
    private ProgressBar prg;
    private Game game;
    private boolean boolSound ;
    private boolean boolAutoGame;
    private boolean isClicked = false;
    int delay = 1000;
    public InitGameViewController(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void initCharcacters(){//return Characters for init Characters Player in class game. I cant init the Characters in Game becuase there is UI

            theCharacters = new Characters();
            theCharacters.getPlayersArray().add(new Player("don quixote",0));
            initViewCharc(R.drawable.don_quixote,theCharacters.getPlayersArray().get(0));
            theCharacters.getPlayersArray().add(new Player("cow girl",0));
            initViewCharc(R.drawable.cowgirl,theCharacters.getPlayersArray().get(1));
            theCharacters.getPlayersArray().add(new Player("teacher",0));
            initViewCharc(R.drawable.teacher,theCharacters.getPlayersArray().get(2));
            theCharacters.getPlayersArray().add(new Player("man",0));
            initViewCharc(R.drawable.man,theCharacters.getPlayersArray().get(3));
            theCharacters.getPlayersArray().add(new Player("business man",0));
            initViewCharc(R.drawable.business_man,theCharacters.getPlayersArray().get(4));
            theCharacters.getPlayersArray().add(new Player("old man",0));
            initViewCharc(R.drawable.old_man,theCharacters.getPlayersArray().get(5));
            theCharacters.getPlayersArray().add(new Player("ninja",0));
            initViewCharc(R.drawable.ninja,theCharacters.getPlayersArray().get(6));
            theCharacters.getPlayersArray().add(new Player("king",0));
            initViewCharc(R.drawable.king,theCharacters.getPlayersArray().get(7));
            theCharacters.getPlayersArray().add(new Player("banshee",0));
            initViewCharc(R.drawable.banshee,theCharacters.getPlayersArray().get(8));
        }

    public Characters getTheCharacters() {
        return theCharacters;
    }

    private void initViewCharc(int id, Player newPlayer) {
        newPlayer.setIdImage(id);
    }

    public void initView(AppCompatActivity activity , Game theGame) { //init all view and score
        game = theGame;
        game_IMG_button = activity.findViewById(R.id.game_IMG_playButton);
        game_IMG_firstImagePlayer = activity.findViewById(R.id.game_IMG_firstPlayer);
        game_IMG_firstImagePlayer.setImageResource(game.getPlayer1().getIdImage());
        game_IMG_secondImagePlayer = activity.findViewById(R.id.game_IMG_secondPlayer);
        game_IMG_secondImagePlayer.setImageResource(game.getPlayer2().getIdImage());
        game_IMG_firstBackCardImage = activity.findViewById(R.id.game_IMG_firstBackCard);
        game_IMG_secondBackCardImage = activity.findViewById(R.id.game_IMG_secondBackCard);
        game_TXT_scorePlayer1 = activity.findViewById(R.id.game_TXT_firstScore);
        game_TXT_scorePlayer2 = activity.findViewById(R.id.game_TXT_secondPlayer);
        prg = activity.findViewById(R.id.game_PRG_timeGame);
       game.getPlayer1().setScore(0);
        game.getPlayer2().setScore(0);

    }
    public void clickOnPlay(final boolean boolAutoGame, boolean boolSound ) { //the action click on image play
    this.boolAutoGame = boolAutoGame;
    this.boolSound = boolSound;
    game_IMG_button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (boolAutoGame == true) {
                isClicked = true;
                game_IMG_button.setEnabled(false);//Turns off the button after one press
                game_IMG_button.setClickable(false);
                startCounting();
            }
            else {
                if(game.getThePackage().getCards().size() != 0)
                    moveCard(activity);
                else
                    moveToFinishPage(activity);
            }

        }
    });

    }
    public boolean isClickled() {
        return isClicked;
    }
    public Package getPackageCard(AppCompatActivity activity) {// return package for init package card in class game. I cant init the package in Game becuase there is UI
        Package thePackage = new Package();
        Character[] ch = {'c', 'd', 'h', 's'};
        for (int i = 2; i < 15; i++) {
            for (int j = 0; j < ch.length; j++) {
                Card newCard = new Card(i);
                String name = "poker_" + (i) + "_" + ch[j];
                int id = activity.getResources().getIdentifier(name, "drawable",activity.getPackageName());
                initViewCard(id,newCard);
                thePackage.setCardInPack(newCard);
            }
        }
        return  thePackage;
    }
    private void initViewCard(int id,Card newCard) {
        newCard.setIdSymbol(id);
    }

    private Timer carousalTimer;
    public void startCounting() {
        carousalTimer = new Timer();
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {




            activity.runOnUiThread(new Runnable() {


                @Override
                public void run() {
                    if (game.getThePackage().getCards().size() != 0) {
                        moveCard(activity);
                        //handler.postDelayed(this, delay);
                    }
                    else {
                        moveToFinishPage(activity);
                    }
                }

            });
            }
        }, 0, delay);
    }
    public void stopCounting() {
        carousalTimer.cancel();
    }




    private void moveCard(AppCompatActivity activity){
        timeInPercent =100 - (100 *(52 - temp))/52;
        temp += 2;//just check num of cards
        int result = game.play(); // the function that replace the cards and return who win in this round
        Log.d("try0", " " + game.getChoiceCard1().getValue() + " " + game.getChoiceCard2().getValue() + " card " + (52 - temp) +" time in percent " + timeInPercent);//this is for me for check if the value card match
        viewCard(activity,game.getChoiceCard1(), game.getChoiceCard2());//show the view card in screen
        addPoint(result);//show the point and add score for the win round
        prg.setProgress((int)timeInPercent);
        if(boolSound == true)
            playSound(R.raw.snd_poker_chips,activity);

    }

    private void moveToFinishPage(AppCompatActivity activity) {
        if (game.getThePackage().getCards().size() == 0) {
            Intent intent = new Intent(activity, FinishActivity.class);
           // int idWin = game.getPlayer1().getScore() > game.getPlayer2().getScore() ? game.getPlayer1().getIdImage() : game.getPlayer1().getScore() < game.getPlayer2().getScore() ? game.getPlayer2().getIdImage() : idDraw; //if that check who win
            Player playerWin = game.getPlayer1().getScore() > game.getPlayer2().getScore() ? game.getPlayer1() : game.getPlayer2();
            Gson gson = new Gson();
            String winner = gson.toJson(playerWin);
            intent.putExtra("EXTRA_KEY_WINNER", winner);
            intent.putExtra("EXTRA_KEY_MY_SoundPlay",boolSound);
            intent.putExtra("EXTRA_KEY_MY_AutoGame",boolAutoGame);
            activity.startActivity(intent);
            activity.finish();

        }
    }
    private void playSound(int id,AppCompatActivity activity){
        MediaPlayer mp;
        mp = MediaPlayer.create(activity,id);
        mp.start();
    }

    private void viewCard(AppCompatActivity activity,Card choiceCard1,Card choiceCard2) {
        Glide
                .with(activity)
                .load(choiceCard1.getIdSymbol())
                .into(game_IMG_firstBackCardImage);
        Glide
                .with(activity)
                .load(choiceCard2.getIdSymbol())
                .into(game_IMG_secondBackCardImage);
       // firstBackCardImage.setImageResource(choiceCard1.getIdSymbol());
       // secondBackCardImage.setImageResource(choiceCard2.getIdSymbol());
    }

    private void addPoint(int result) {
        if(result == 1) {
            game.getPlayer1().setScore(game.getPlayer1().getScore()+1);
            game_TXT_scorePlayer1.setText(" " + game.getPlayer1().getScore());
        }
        else if(result == 2) {
            game.getPlayer2().setScore(game.getPlayer2().getScore()+1);
            game_TXT_scorePlayer2.setText(" " + game.getPlayer2().getScore());
        }
    }


}
