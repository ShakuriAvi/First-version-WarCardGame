package com.example.hw1_avishakuri;

import java.util.Random;

public class Game {
    private Player player1;
    private Player player2;
    private Package thePackage;
    private Characters theCharacters;
    Card choiceCard1;
    Card choiceCard2;
    public Game(Characters characters,Package thePackage){
        this.thePackage = thePackage;
        this.theCharacters = characters;
        player1=  randomPlayer();
        player2 = randomPlayer();

    }
    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }

    public Package getThePackage(){
        return thePackage;
    }
    public int play(){
        choiceCard1 = randomCard();
        choiceCard2 = randomCard();
        return rulesGame(choiceCard1, choiceCard2);
    }
    public Card getChoiceCard1(){
        return choiceCard1;
    }
    public Card getChoiceCard2(){
        return choiceCard2;
    }
    private Card randomCard() {
        Card choiceCard;
        Random rand1 = new Random();
        int randChoice = thePackage.getCards().size()==1? 0 : rand1.nextInt((thePackage.getCards().size()-1));//choice package
        choiceCard = thePackage.getCards().get(randChoice);
        thePackage.getCards().remove(randChoice);
        //temp++;
        return choiceCard;
    }
    private Player randomPlayer() {
        Random rand1 = new Random();
        int randChoice = rand1.nextInt(theCharacters.getPlayersArray().size() - 1);
        Player newPlayer = theCharacters.getPlayersArray().get(randChoice);
        theCharacters.getPlayersArray().remove(randChoice);
        return newPlayer;
    }
    private int rulesGame(Card choiceCard1, Card choiceCard2) {
        if(choiceCard1.getValue() > choiceCard2.getValue())
            return 1;
        else if(choiceCard1.getValue() < choiceCard2.getValue())
            return 2;
        else return 0;
    }

}
