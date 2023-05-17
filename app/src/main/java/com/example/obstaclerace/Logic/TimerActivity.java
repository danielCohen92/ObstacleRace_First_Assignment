package com.example.obstaclerace.Logic;

import android.net.CaptivePortal;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.google.android.material.textview.MaterialTextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {
    private MaterialTextView time_LBL_time;

    long startTime = 0;
    public final int DELAY = 1000;
    public long score=0;



    private final Handler handler = new Handler();

    public TimerActivity  () {
        handler.postDelayed(runnable,DELAY);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, DELAY); //Do it again in a second
            updateTimeUI();
        }
    };

    private void updateTimeUI() {

        this.score++;
        Log.d("TimerCount: ", "" + System.currentTimeMillis());
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds %= 60;
        int hours = minutes / 60;
        minutes %= 60;
        hours %= 24;

        time_LBL_time.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        this.score = millis;

    }

    public int getTime(){
        //return the number of second as score of the game.
        return (int) (score = score/1000);
    }

    private void stopTime() {
        handler.removeCallbacks(runnable);
    }

    private void startTime() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable,DELAY);
    }
}
