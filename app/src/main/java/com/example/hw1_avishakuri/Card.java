package com.example.hw1_avishakuri;

import android.view.View;
import android.widget.ImageView;

public class Card {
private int value;
private int idSymbol;

    public int getValue() {
        return value;
    }

    public Card(int value) {
        this.value = value;

    }

    public int getIdSymbol() {
        return idSymbol;
    }

    public void setIdSymbol(int id) {
        this.idSymbol = id;
    }




}
