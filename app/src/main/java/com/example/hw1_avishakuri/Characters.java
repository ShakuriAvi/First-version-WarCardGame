package com.example.hw1_avishakuri;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import java.util.ArrayList;

public class Characters {
private ArrayList<Player> playersArray;

    public Characters(){
        this.playersArray = new ArrayList<>();
        initPlayer();
    }
    public ArrayList<Player> getPlayersArray() {
        return playersArray;
    }


    public void setPlayersArray(ArrayList<Player> playersArray) {
        this.playersArray = playersArray;
    }


    private void initPlayer() {

    }
}
