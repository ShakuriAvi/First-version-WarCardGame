package com.example.hw1_avishakuri.Class;

public class Player  {
    private String name;
    private int score;
    private int idImage;
    private double latitude;
    private double longitude;
    public Player(){

    }
    public Player(String name,int score)
    {
        this.name = name;
        this.score = score;//maybe in HW2 we need to continue from the score in the last game
        this.latitude = 31.993904;
        this.longitude = 34.756071;
    }
    public void setLocation( double newLatitude, double newLongitude)
    {
        this.latitude = newLatitude;
        this.longitude = newLongitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getIdImage() {
        return idImage;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name =  name;
    }

    public void setIdImage(int id) {
        this.idImage = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
