package com.example.hw1_avishakuri.Class;



import java.util.ArrayList;
public class Package {
   private ArrayList<Card> packageOfCard;


   public Package(){
      this.packageOfCard = new ArrayList<Card>();

   }

   public void setCardInPack(Card newCard) {
      packageOfCard.add(newCard);
   }

   public void setCards(ArrayList<Card> cards)
   {
      packageOfCard = cards;
   }
   public ArrayList<Card> getCards() {
      return packageOfCard;
   }


}
