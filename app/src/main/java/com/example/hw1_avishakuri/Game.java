package com.example.hw1_avishakuri;

import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    int score1;
    int score2;
    private Characters theCharacters;

    public Game(){
        score1 = 0;
        score2=0;
        player1=  randomPlayer();
        player2 = randomPlayer();
    }

    private Player randomPlayer() {
        Random rand1 = new Random();
        int randChoice = rand1.nextInt(theCharacters.getPlayersArray().size() - 1);
        Player newPlayer = theCharacters.getPlayersArray().get(randChoice);
        theCharacters.getPlayersArray().remove(randChoice);

        return newPlayer;
    }
}
