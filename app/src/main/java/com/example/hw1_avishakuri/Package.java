package com.example.hw1_avishakuri;



import java.util.ArrayList;
public class Package {
   private ArrayList<Card> packageOfCard;


   public Package(){
      this.packageOfCard = new ArrayList<>();

   }

   public void setCardInPack(Card newCard) {
      packageOfCard.add(newCard);
   }


   public ArrayList<Card> getCards() {
      return packageOfCard;
   }


}
