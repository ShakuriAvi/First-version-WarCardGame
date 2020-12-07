package com.example.hw1_avishakuri.Class;

import java.util.ArrayList;

public class Characters {
private ArrayList<Player> playersArray;

    public Characters(){
        this.playersArray = new ArrayList<Player>();
    }
    public ArrayList<Player> getPlayersArray() {
        return playersArray;
    }


    public void setPlayersArray(ArrayList<Player> playersArray) {
        this.playersArray = playersArray;
    }
    public Player getPlayerByName(String name){
        for (int i = 0; i <playersArray.size() ; i++) {
            if(playersArray.get(i).getName().equals(name))
                return playersArray.get(i);
        }
        return null;
    }


}
