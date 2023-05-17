package com.example.obstaclerace.Logic;

import android.util.Log;

import com.example.obstaclerace.Models.Score;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {

    private boolean isScoresCreate = false;
    public static ArrayList<Score> scores ;
    public static ArrayList<Score> getScores() {
        ArrayList<Score> scores = new ArrayList<>();
        scores.add(new Score()
                .setRecord(100)
                .setImage("")
                .setCoordinateX(32.830476)
                .setCoordinateY(34.976954)
        );
        scores.add(new Score()
                .setRecord(70)
                .setImage("")
                .setCoordinateX(32.088004)
                .setCoordinateY(34.777591)
        );

        scores.add(new Score()
                .setRecord(50)
                .setImage("")
                .setCoordinateX(29.548668)
                .setCoordinateY(34.952933)
        );

        scores.add(new Score()
                .setRecord(30)
                .setImage("")
                .setCoordinateX(31.548668)
                .setCoordinateY(34.952933)
        );
        return scores;
    }

     public ScoreManager() {
        if(isScoresCreate == false) {
            scores = getScores();
            isScoresCreate = true;
        }
        else
        {
            Log.d("Scores:", String.valueOf("the array already exist! "));
        }
    }

    public static ArrayList<Score> getArrayOfScore(){
        return scores;
    }

     public static ArrayList<Score> getArrayScoreAfterAddingNewScore(){
        if(scores==null){
            return getScores();
        }
        else
        {
            return scores;
        }
     }


     public static void sortScores(ArrayList<Score> scores) {
        Collections.sort(scores, (score1, score2) -> Integer.compare(score2.getRecord(), score1.getRecord()));
        }



    public boolean checkNewScore(ArrayList<Score> scores, int newScore){
        int LastIndex = getScores().size()-1;
        //check if newScore > the last index in arraylist of records
        if(scores.get(LastIndex).getRecord() < newScore){
            scores.get(LastIndex).setRecord(newScore);
            sortScores(scores);
            return true;
            }
        return false;
    }




    public void printScoreArray() {
        for(int i=0;i< this.scores.size()-1;i++){
            Log.d("TAG", String.valueOf(scores.get(i).getUserNameAndScore()));
        }
    }



}
