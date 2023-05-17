package com.example.obstaclerace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.obstaclerace.Logic.ScoreManager;
import com.example.obstaclerace.Models.Score;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private Button fastGame,slowGame,OpenScoreScreen,playWithSensor,playWithArrows;
    private boolean pressSensor = false;
    private boolean pressArrow = false;

    public static ScoreManager scoreManager = new ScoreManager();

    private Button[] menuButtons;

    public static boolean checkNewScore(ArrayList<Score> scores, int scoreAsINT) {
        if (scoreManager != null){
            scoreManager.checkNewScore(scores,scoreAsINT);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void printScoreArray() {
        if (scoreManager != null)
            scoreManager.printScoreArray();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        createScoreManager();
        findViews();
        pressButtons();
    }

    private void createScoreManager() {
        if(scoreManager == null){
            scoreManager = new ScoreManager();
        }
    }


    private void pressButtons() {

        menuButtons[0].setOnClickListener(view -> {

                Intent scoreIntent = new Intent(this, ScoreActivity.class);
                startActivity(scoreIntent);
                finish();
        });
        //play game with sensor
        menuButtons[1].setOnClickListener(view -> {
            //play game with Sensor
            GoToGame(true,false);
            Toast.makeText(MenuActivity.this, "sensors",Toast.LENGTH_SHORT).show();

        });
        //play game with Arrows
        menuButtons[2].setOnClickListener(view -> {
            Toast.makeText(MenuActivity.this, "Arrows",Toast.LENGTH_SHORT).show();
            GoToGame(false,true);
        });
    }

    private void GoToGame(boolean pressSensor, boolean pressArrow) {
        //if choose: sensor + Bonus-> Go to MainActivity with the parameters
        if(pressSensor ) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(MainActivity.SENSORS_SERVICE, true);
            mainIntent.putExtra(MainActivity.ARROWS_SERVICE, false);
            startActivity(mainIntent);
            finish();
        }

        if(pressArrow) {
            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(MainActivity.SENSORS_SERVICE, false);
            mainIntent.putExtra(MainActivity.ARROWS_SERVICE, true);
            startActivity(mainIntent);
            finish();
        }



    }



    private void findViews() {
            menuButtons = new Button[]{
                findViewById(R.id.ScreenScore_BTN),
                findViewById(R.id.play_BTN_Sensor),
                findViewById(R.id.play_BTN_Arrows)
        };
    }




}
