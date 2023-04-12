package com.example.obstaclerace.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;



public class GameManager {
   private int life;
   private int numOfCrash;


    public GameManager(int life) {
        this.life = life;
        this.numOfCrash = 0;
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

    public int getNumOfCrash() {
        return numOfCrash;
    }

    public boolean isGameEnded(int StartLife) {
        return StartLife == numOfCrash;
    }



}
