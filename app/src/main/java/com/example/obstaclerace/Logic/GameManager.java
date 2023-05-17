package com.example.obstaclerace.Logic;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class GameManager {
   private int life;
   private int numOfCrash;
   private int score;
   private Handler Timer;
   private int seconds = 0;
   private int minutes =0;
   private int hours = 0;
   //public int[] scoreArray; //array size 5

  // public ScoreManager scoreManager;

   private long startTime = 0;
    public GameManager(int life) {
        this.life = life;
        this.numOfCrash = 0;
        this.score = 0;
        startTimer();
    }

    public int getLife() {
        return life;
    }

    public void setLife(int numOfLife){
        life = numOfLife;
    }
    public void setNumOfCrash(int numberOfCrush){
        numOfCrash = numberOfCrush;
    }

    public void RemoveLifeBecauseCrash(boolean Crash) {
        if(Crash==true && life!=0){
            life--;
            numOfCrash++;
        }
    }

    public int getScoreAsINT(){
        return score;
    }
    public int getNumOfCrash() {
        return numOfCrash;
    }

    public boolean isGameEnded(int StartLife) {
        return StartLife == numOfCrash;
    }

    private void startTimer() {
        Timer = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                updateTimeUI();

                Timer.postDelayed(this, 1000);
            }
        };
        Timer.post(runnable);

    }
    public String getScoreAsString() {
        return ("" + score);
    }

    private void updateTimeUI() {
        this.score++;

    }

    public void addCoinToScore(boolean addCoin) {
        if(addCoin)
            this.score+=50;
    }







}
