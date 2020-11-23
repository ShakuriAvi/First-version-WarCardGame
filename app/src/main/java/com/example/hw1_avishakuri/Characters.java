package com.example.hw1_avishakuri;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import java.io.Serializable;
import java.util.ArrayList;

public class Characters implements Serializable {
private ArrayList<Player> playersArray;

    public Characters(){
        this.playersArray = new ArrayList<>();
    }
    public ArrayList<Player> getPlayersArray() {
        return playersArray;
    }


    public void setPlayersArray(ArrayList<Player> playersArray) {
        this.playersArray = playersArray;
    }
    public Player getPlayerByName(String name){
        for (int i = 0; i <playersArray.size() ; i++) {
            if(playersArray.get(i).getName().contains(name))
                return playersArray.get(i);
        }
        return null;
    }


}
