package com.example.hw1_avishakuri;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
private int temp = 0;//check num of cards its for me
private double timeInPercent = 0;
private ImageView click;//click on image for movw card
private Game game;//the rule of game: move two card, choose random player,etc...
private TextView txtViewScorePlayer1;
private int score1;//get from the class game who need get point
private TextView txtViewScorePlayer2;
private int score2;
private int idDraw;// id of package draw
private ImageView firstImagePlayer;
private ImageView secondImagePlayer;
private ImageView firstBackCardImage;
private ImageView secondBackCardImage;
private ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        game = new Game(initCharacters(), initPackageCard());
        initView();
       // clickOnPlay();
    }



    //}
/*
   //     if you want to show the game on the length screen and move width.
        try {
            if(savedInstanceState !=null){
                int previousScore1= savedInstanceState.getInt("MY_SCORE1");
                int previousScore2= savedInstanceState.getInt("MY_SCORE2");
                score1 = previousScore1;
                score2 = previousScore2;
                txtViewScorePlayer1.setText(" "+ score1);
                txtViewScorePlayer2.setText(" "+ score2);
              //  game = savedInstanceState.getParcelable("MY_GAME");
            }
        }   catch (Exception ex) { }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d("pttt", "onSaveInstanceState");
        super.onSaveInstanceState(outState);
        outState.putInt("MY_SCORE1", score1);
        outState.putInt("MY_SCORE2", score2);
        //outState.putParcelable("MY_GAME",game);
    }
*/
    private void initView() { //init all view and score
        click = findViewById(R.id.main_IMG_playButton);
        firstImagePlayer = findViewById(R.id.main_IMG_firstPlayer);
        firstImagePlayer.setImageResource(game.getPlayer1().getIdImage());
        secondImagePlayer = findViewById(R.id.main_IMG_secondPlayer);
        secondImagePlayer.setImageResource(game.getPlayer2().getIdImage());
        firstBackCardImage = findViewById(R.id.main_IMG_firstBackCard);
        secondBackCardImage = findViewById(R.id.main_IMG_secondBackCard);
        txtViewScorePlayer1 = findViewById(R.id.main_TXT_firstScore);
        txtViewScorePlayer2 = findViewById(R.id.main_TXT_secondPlayer);
        prg = findViewById(R.id.main_PRG_timeGame);
        score1 = 0;
        score2=0;
        idDraw = getResources().getIdentifier("draw_flag", "drawable",this.getPackageName());
    }

    private void clickOnPlay() { //the action click on image play

        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                        timer();
                }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        clickOnPlay();
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    private void timer() {
        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (game.getThePackage().getCards().size() != 0) {
                        moveCard();
                        handler.postDelayed(this, delay);

                }
                else {
                    moveToFinishPage();
                }
            }
        },delay);
    }
    private void moveCard(){
        timeInPercent =100 - (100 *(52 - temp))/52;
        temp += 2;//just check num of cards
        int result = game.play(); // the function that replace the cards and return who win in this round
        Log.d("try0", " " + game.getChoiceCard1().getValue() + " " + game.getChoiceCard2().getValue() + " card " + (52 - temp) +" time in percent " + timeInPercent);//this is for me for check if the value card match
        viewCard(game.getChoiceCard1(), game.getChoiceCard2());//show the view card in screen
        addPoint(result);//show the point and add score for the win round
        prg.setProgress((int)timeInPercent);
        playSound(R.raw.snd_poker_chips);

    }
    private void moveToFinishPage() {
        if (game.getThePackage().getCards().size() == 0) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            int idWin = score1 > score2 ? game.getPlayer1().getIdImage() : score1 < score2 ? game.getPlayer2().getIdImage() : idDraw; //if that check who win
            int scoreWin = score1 > score2 ? score1 : score2;
            intent.putExtra("EXTRA_KEY_ID_IMAGE", (idWin));
            intent.putExtra("EXTRA_KEY_SCORE", scoreWin);
            startActivity(intent);
            finish();

        }
    }
    private void playSound(int id){
        MediaPlayer mp;
        mp = MediaPlayer.create(this,id);
        mp.start();
    }

    private void viewCard(Card choiceCard1,Card choiceCard2) {
        firstBackCardImage.setImageResource(choiceCard1.getIdSymbol());
        secondBackCardImage.setImageResource(choiceCard2.getIdSymbol());
    }

    private void addPoint(int result) {
        if(result == 1)
            txtViewScorePlayer1.setText(" "+ ++score1);
        else if(result == 2)
            txtViewScorePlayer2.setText(" " + ++score2);
    }


    private Characters initCharacters() {//return Characters for init Characters Player in class game. I cant init the Characters in Game becuase there is UI

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

    private Package initPackageCard() {// return package for init package card in class game. I cant init the package in Game becuase there is UI
        Package thePackage = new Package();
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

