package com.example.hw1_avishakuri;

import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int score;
    private int idImage;

    public Player(String name,int score)
    {
        this.name = name;
        this.score = score;//maybe in HW2 we need to continue from the score in the last game
    }

    public int getIdImage() {
        return idImage;
    }

    public String getName() {
        return name;
    }

    public void setIdImage(int id) {
        this.idImage = id;
    }
}
