package com.example.obstaclerace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.obstaclerace.Fragments.ListFragment;
import com.example.obstaclerace.Interfaces.StepCallBack;
import com.example.obstaclerace.Logic.GameManager;
import com.example.obstaclerace.Logic.ScoreManager;
import com.example.obstaclerace.Utilities.StepDetector;
import com.google.android.material.color.utilities.Score;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private Boolean playWithSensors,playWithArrows;
    public static final String ARROWS_SERVICE = "ARROWS_SERVICE";
    public static final String SENSORS_SERVICE = "SENSORS_SERVICE";
    public AppCompatImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] policeCar;
    final private ShapeableImageView[][] main_grid = new ShapeableImageView[5][5];

    final private ShapeableImageView[][] main_coins = new ShapeableImageView[5][5];
    private FloatingActionButton[] floatingActionArrowsButtons;
    private GameManager gameManager;
    private ScoreManager ScoreManager;
    private Handler handler0;
    private Handler handler1;
    private Handler handler2;

    private Handler handler3;

    private Handler handler4;

    private Handler handlerTimer;

    private MaterialTextView Timer_Text;
    private StepDetector stepDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        Intent prevIntent = getIntent();
        String Arrows = prevIntent.getStringExtra(ARROWS_SERVICE);
        String Sensors = prevIntent.getStringExtra(ARROWS_SERVICE);

        playWithArrows = prevIntent.getBooleanExtra(ARROWS_SERVICE,false);
        playWithSensors = prevIntent.getBooleanExtra(SENSORS_SERVICE,false);

        StartGame(playWithSensors,playWithArrows);
    }

    private void initStepDetector() {
        floatingActionArrowsButtons[0].setVisibility(View.INVISIBLE);
        floatingActionArrowsButtons[1].setVisibility(View.INVISIBLE);

        final int[] stateOfCar = {0};

        stepDetector = new StepDetector(this, new StepCallBack() {
            @Override
            public void stepX() {

                //move Left
                if (stateOfCar[0] == 2) {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.INVISIBLE);
                    policeCar[3].setVisibility(View.VISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);
                    stateOfCar[0]--;
                }

                else if(stateOfCar[0] == 1){
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.VISIBLE);
                    policeCar[3].setVisibility(View.INVISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);
                    stateOfCar[0]--;
                }


                else if(stateOfCar[0] == 0) {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.VISIBLE);
                    for(int i=2; i<=4;i++){
                        policeCar[i].setVisibility(View.INVISIBLE);
                    }
                    stateOfCar[0]--;
                }
                else if (stateOfCar[0] == -1)
                {
                    policeCar[0].setVisibility(View.VISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.INVISIBLE);
                    policeCar[3].setVisibility(View.INVISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);

                    stateOfCar[0]--;
                }



               // main_LBL_stepsX.setText("" + stepDetector.getStepsX());
            }

            @Override
            public void stepMinusX() {
                //move Right
                if(stateOfCar[0] == -2) {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.VISIBLE);
                    policeCar[2].setVisibility(View.INVISIBLE);
                    policeCar[3].setVisibility(View.INVISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);
                    stateOfCar[0]++;
                }
                else if (stateOfCar[0] == -1)
                {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.VISIBLE);
                    policeCar[3].setVisibility(View.INVISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);
                    stateOfCar[0]++;
                }
                else if (stateOfCar[0] == 0)
                {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.INVISIBLE);
                    policeCar[3].setVisibility(View.VISIBLE);
                    policeCar[4].setVisibility(View.INVISIBLE);
                    stateOfCar[0]++;
                }
                else if (stateOfCar[0] == 1)
                {
                    policeCar[0].setVisibility(View.INVISIBLE);
                    policeCar[1].setVisibility(View.INVISIBLE);
                    policeCar[2].setVisibility(View.INVISIBLE);
                    policeCar[3].setVisibility(View.INVISIBLE);
                    policeCar[4].setVisibility(View.VISIBLE);
                    stateOfCar[0]++;
                }
            }


            @Override
            public void stepY() {
                //pass
            }

            @Override
            public void stepZ() {
                // Pass
            }
        });
    }

    private void StartGame(Boolean playWithSensors,Boolean playWithArrows) {
        VisibleInStart();
        gameManager.setLife(main_IMG_hearts.length); //for Re-Game
        gameManager.setNumOfCrash(0);
        moveCols0();
        moveCols1();
        moveCols2();
        moveCols3();
        moveCols4();
        getScoreFromManager();

        if(playWithArrows) {
            playGame();
        }
        else{
            playGameWithSensors();
        }

    }
    private void getScoreFromManager() {
        handlerTimer = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Timer_Text.setText(gameManager.getScoreAsString());
                handlerTimer.postDelayed(this, 1000);
            }
        };
        handlerTimer.post(runnable);
    }
    private void moveCols0() {
        handler0 = new Handler();
        Runnable runnable = new Runnable() {
            int col0row = 1;
            boolean coinOnCol = false;
             //rows go from 1 to 3
            @Override
            public void run() {
                main_grid[3][0].setVisibility(View.INVISIBLE);
                main_coins[3][0].setVisibility(View.INVISIBLE);

                if (col0row == 1) {
                    if(!coinOnCol) {
                        main_grid[1][0].setVisibility(View.VISIBLE);
                        main_grid[2][0].setVisibility(View.INVISIBLE);
                        main_grid[3][0].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][0].setVisibility(View.VISIBLE);
                        main_coins[2][0].setVisibility(View.INVISIBLE);
                        main_coins[3][0].setVisibility(View.INVISIBLE);
                    }
                    col0row++;
                } else if (col0row == 2) {
                    if (!coinOnCol) {
                        main_grid[1][0].setVisibility(View.INVISIBLE);
                        main_grid[2][0].setVisibility(View.VISIBLE);
                        main_grid[3][0].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][0].setVisibility(View.INVISIBLE);
                        main_coins[2][0].setVisibility(View.VISIBLE);
                        main_coins[3][0].setVisibility(View.INVISIBLE);
                    }
                    col0row++;
                } else {
                    if(!coinOnCol) {
                        main_grid[1][0].setVisibility(View.INVISIBLE);
                        main_grid[2][0].setVisibility(View.INVISIBLE);
                        main_grid[3][0].setVisibility(View.VISIBLE);
                        if (policeCar[0].getVisibility() == View.VISIBLE) {
                            Toast.makeText(MainActivity.this, "Crash!", Toast.LENGTH_SHORT).show();
                            doVibrate();
                            gameManager.RemoveLifeBecauseCrash(true); //crash happend
                            showHeart(gameManager.getLife());
                            if (gameManager.isGameEnded(main_IMG_hearts.length)) {
                                StartGameOrGOToScoreScreen();
                            }
                        }
                    }
                    else{
                        main_coins[1][0].setVisibility(View.INVISIBLE);
                        main_coins[2][0].setVisibility(View.INVISIBLE);
                        main_coins[3][0].setVisibility(View.VISIBLE);
                        if (policeCar[0].getVisibility() == View.VISIBLE) {
                            Toast.makeText(MainActivity.this, "Bonus!", Toast.LENGTH_SHORT).show();
                            doVibrate();
                            gameManager.addCoinToScore(true); //got coin
                            Timer_Text.setText(gameManager.getScoreAsString());
                        }
                    }
                    col0row = 1;
                    if(!coinOnCol){
                        coinOnCol=true;
                    }
                    else{
                        coinOnCol=false;
                    }
                }
                handler0.postDelayed(this, 3000);
            }


        };
        handler0.post(runnable);
    }
    private void moveCols1() {
        handler1 = new Handler();
        Runnable runnable = new Runnable() {
            int col2row = 0;
            //rows go from 1 to 3
            @Override
            public void run() {
                if(col2row == 0 ){
                    main_grid[1][1].setVisibility(View.INVISIBLE);
                    main_grid[2][1].setVisibility(View.INVISIBLE);
                    main_grid[3][1].setVisibility(View.INVISIBLE);
                    col2row++;
                }
                else if (col2row == 1) {
                    main_grid[1][1].setVisibility(View.VISIBLE);
                    main_grid[2][1].setVisibility(View.INVISIBLE);
                    main_grid[3][1].setVisibility(View.INVISIBLE);
                    col2row++;
                } else if (col2row == 2) {
                    main_grid[1][1].setVisibility(View.INVISIBLE);
                    main_grid[2][1].setVisibility(View.VISIBLE);
                    main_grid[3][1].setVisibility(View.INVISIBLE);
                    col2row++;
                } else {
                    main_grid[1][1].setVisibility(View.INVISIBLE);
                    main_grid[2][1].setVisibility(View.INVISIBLE);
                    main_grid[3][1].setVisibility(View.VISIBLE);
                    if(policeCar[1].getVisibility() == View.VISIBLE){
                        Toast.makeText(MainActivity.this, "Crash!",Toast.LENGTH_SHORT).show();
                        doVibrate();
                        gameManager.RemoveLifeBecauseCrash(true); //crash happend
                        showHeart(gameManager.getLife());
                        if(gameManager.isGameEnded(main_IMG_hearts.length)){
                            StartGameOrGOToScoreScreen();
                        }
                    }
                    col2row = 1;
                }
                handler1.postDelayed(this, 2000);

            }
        };
        handler1.post(runnable);
    }
    private void moveCols2() {
        handler2 = new Handler();
        Runnable runnable = new Runnable() {

            int col2row = -1;
            //rows go from 1 to 3
            @Override
            public void run() {
                if(col2row == -1 ){
                    main_grid[1][2].setVisibility(View.INVISIBLE);
                    main_grid[2][2].setVisibility(View.INVISIBLE);
                    main_grid[3][2].setVisibility(View.INVISIBLE);
                    col2row++;
                }
                else if(col2row == 0 ){
                    main_grid[1][2].setVisibility(View.INVISIBLE);
                    main_grid[2][2].setVisibility(View.INVISIBLE);
                    main_grid[3][2].setVisibility(View.INVISIBLE);
                    col2row++;
                }
                else if (col2row == 1) {
                    main_grid[1][2].setVisibility(View.VISIBLE);
                    main_grid[2][2].setVisibility(View.INVISIBLE);
                    main_grid[3][2].setVisibility(View.INVISIBLE);
                    col2row++;
                } else if (col2row == 2) {
                    main_grid[1][2].setVisibility(View.INVISIBLE);
                    main_grid[2][2].setVisibility(View.VISIBLE);
                    main_grid[3][2].setVisibility(View.INVISIBLE);
                    col2row++;
                } else {
                    main_grid[1][2].setVisibility(View.INVISIBLE);
                    main_grid[2][2].setVisibility(View.INVISIBLE);
                    main_grid[3][2].setVisibility(View.VISIBLE);
                    if(policeCar[2].getVisibility() == View.VISIBLE){
                        Toast.makeText(MainActivity.this, "Crash!",Toast.LENGTH_SHORT).show();
                        doVibrate();
                        gameManager.RemoveLifeBecauseCrash(true); //crash happend
                        showHeart(gameManager.getLife());
                        if(gameManager.isGameEnded(main_IMG_hearts.length)){
                            StartGameOrGOToScoreScreen();
                        }
                    }
                    col2row = -1;
                }
                handler2.postDelayed(this, 2000);
            }
        };
        handler2.post(runnable);
    }
    private void moveCols3() {
        handler3 = new Handler();
        Runnable runnable = new Runnable() {

            int col3row = 0;

            boolean coinOnCol = false;

            //rows go from 1 to 3
            @Override
            public void run() {
                main_grid[3][3].setVisibility(View.INVISIBLE);
                main_coins[3][3].setVisibility(View.INVISIBLE);

                if(col3row <= 2 ){
                    if(!coinOnCol) {
                        main_grid[1][3].setVisibility(View.INVISIBLE);
                        main_grid[2][3].setVisibility(View.INVISIBLE);
                        main_grid[3][3].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][3].setVisibility(View.INVISIBLE);
                        main_coins[2][3].setVisibility(View.INVISIBLE);
                        main_coins[3][3].setVisibility(View.INVISIBLE);
                    }
                    col3row++;
                }
                else if(col3row == 3 ){
                    if(!coinOnCol){
                        main_grid[1][3].setVisibility(View.INVISIBLE);
                        main_grid[2][3].setVisibility(View.INVISIBLE);
                        main_grid[3][3].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][3].setVisibility(View.INVISIBLE);
                        main_coins[2][3].setVisibility(View.INVISIBLE);
                        main_coins[3][3].setVisibility(View.INVISIBLE);
                    }
                    col3row++;
                }
                else if (col3row == 4) {
                    if(!coinOnCol){
                        main_grid[1][3].setVisibility(View.VISIBLE);
                        main_grid[2][3].setVisibility(View.INVISIBLE);
                        main_grid[3][3].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][3].setVisibility(View.VISIBLE);
                        main_coins[2][3].setVisibility(View.INVISIBLE);
                        main_coins[3][3].setVisibility(View.INVISIBLE);
                    }
                    col3row++;
                } else if (col3row == 5) {
                    if(!coinOnCol){
                        main_grid[1][3].setVisibility(View.INVISIBLE);
                        main_grid[2][3].setVisibility(View.VISIBLE);
                        main_grid[3][3].setVisibility(View.INVISIBLE);
                    }
                    else{
                        main_coins[1][3].setVisibility(View.INVISIBLE);
                        main_coins[2][3].setVisibility(View.VISIBLE);
                        main_coins[3][3].setVisibility(View.INVISIBLE);
                    }
                    col3row++;
                } else {
                    if(!coinOnCol) {
                        main_grid[1][3].setVisibility(View.INVISIBLE);
                        main_grid[2][3].setVisibility(View.INVISIBLE);
                        main_grid[3][3].setVisibility(View.VISIBLE);
                        if (policeCar[3].getVisibility() == View.VISIBLE) {
                            Toast.makeText(MainActivity.this, "Crash!", Toast.LENGTH_SHORT).show();
                            doVibrate();
                            gameManager.RemoveLifeBecauseCrash(true); //crash happend
                            showHeart(gameManager.getLife());
                            if (gameManager.isGameEnded(main_IMG_hearts.length)) {
                                StartGameOrGOToScoreScreen();
                            }
                        }
                    }
                    else{
                        main_coins[1][3].setVisibility(View.INVISIBLE);
                        main_coins[2][3].setVisibility(View.INVISIBLE);
                        main_coins[3][3].setVisibility(View.VISIBLE);
                        if (policeCar[3].getVisibility() == View.VISIBLE) {
                            Toast.makeText(MainActivity.this, "Bonus!", Toast.LENGTH_SHORT).show();
                            doVibrate();
                            gameManager.addCoinToScore(true); //got coin
                            Timer_Text.setText(gameManager.getScoreAsString());
                        }
                    }
                    col3row = 0;
                    if(!coinOnCol){
                        coinOnCol=true;
                    }
                    else{
                        coinOnCol=false;
                    }
                }
                handler3.postDelayed(this, 2000);
            }
        };
        handler3.post(runnable);
    }
    private void moveCols4() {
        handler4 = new Handler();
        Runnable runnable = new Runnable() {

            int col4row = 0;
            //rows go from 1 to 3
            @Override
            public void run() {

                if(col4row <= 3 ){
                    main_grid[1][4].setVisibility(View.INVISIBLE);
                    main_grid[2][4].setVisibility(View.INVISIBLE);
                    main_grid[3][4].setVisibility(View.INVISIBLE);
                    col4row++;
                }
                else if(col4row == 4 ){
                    main_grid[1][4].setVisibility(View.INVISIBLE);
                    main_grid[2][4].setVisibility(View.INVISIBLE);
                    main_grid[3][4].setVisibility(View.INVISIBLE);
                    col4row++;
                }
                else if (col4row == 5) {
                    main_grid[1][4].setVisibility(View.VISIBLE);
                    main_grid[2][4].setVisibility(View.INVISIBLE);
                    main_grid[3][4].setVisibility(View.INVISIBLE);
                    col4row++;
                } else if (col4row == 5) {
                    main_grid[1][4].setVisibility(View.INVISIBLE);
                    main_grid[2][4].setVisibility(View.VISIBLE);
                    main_grid[3][4].setVisibility(View.INVISIBLE);
                    col4row++;
                } else {
                    main_grid[1][4].setVisibility(View.INVISIBLE);
                    main_grid[2][4].setVisibility(View.INVISIBLE);
                    main_grid[3][4].setVisibility(View.VISIBLE);
                    if(policeCar[4].getVisibility() == View.VISIBLE){
                        Toast.makeText(MainActivity.this, "Crash!",Toast.LENGTH_SHORT).show();
                        doVibrate();
                        gameManager.RemoveLifeBecauseCrash(true); //crash happend
                        showHeart(gameManager.getLife());
                        if(gameManager.isGameEnded(main_IMG_hearts.length)){
                            StartGameOrGOToScoreScreen();
                        }
                    }
                    col4row = 0;
                }
                handler4.postDelayed(this, 2000);
            }
        };
        handler4.post(runnable);
    }

    private void playGame() {
        final int[] stateOfCar = {0};
        //states of cars ->  -2 = left2 , -1 = left1 , 0 = middle , 1 = right1 ,2 = right2
        //Pressing Left Arrow
        floatingActionArrowsButtons[0].setOnClickListener(view -> {
            if (stateOfCar[0] == 2) {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                policeCar[3].setVisibility(View.VISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);
                stateOfCar[0]--;
            }

            else if(stateOfCar[0] == 1){
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.VISIBLE);
                policeCar[3].setVisibility(View.INVISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);
                stateOfCar[0]--;
            }


            else if(stateOfCar[0] == 0) {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.VISIBLE);
                for(int i=2; i<=4;i++){
                    policeCar[i].setVisibility(View.INVISIBLE);
                }
                stateOfCar[0]--;
                }
            else if (stateOfCar[0] == -1)
            {
                policeCar[0].setVisibility(View.VISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                policeCar[3].setVisibility(View.INVISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);

                stateOfCar[0]--;
            }
        });
        //states of cars ->  -2 = left2 , -1 = left1 , 0 = middle , 1 = right1 ,2 = right2
        //Pressing Right Arrow
        floatingActionArrowsButtons[1].setOnClickListener(view -> {

            if(stateOfCar[0] == -2) {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.VISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                policeCar[3].setVisibility(View.INVISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);
                stateOfCar[0]++;
            }
            else if (stateOfCar[0] == -1)
            {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.VISIBLE);
                policeCar[3].setVisibility(View.INVISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);
                stateOfCar[0]++;
            }
            else if (stateOfCar[0] == 0)
            {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                policeCar[3].setVisibility(View.VISIBLE);
                policeCar[4].setVisibility(View.INVISIBLE);
                stateOfCar[0]++;
            }
            else if (stateOfCar[0] == 1)
            {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                policeCar[3].setVisibility(View.INVISIBLE);
                policeCar[4].setVisibility(View.VISIBLE);
                stateOfCar[0]++;
            }
        });
    }

    private void playGameWithSensors() {
        //StepDetector.getMoveLeft();
        initStepDetector();
    }
    private void VisibleInStart() {
        main_IMG_hearts[0].setVisibility(View.VISIBLE);
        main_IMG_hearts[1].setVisibility(View.VISIBLE);
        main_IMG_hearts[2].setVisibility(View.VISIBLE);

        main_grid[1][0].setVisibility(View.INVISIBLE);
        main_grid[2][0].setVisibility(View.INVISIBLE);
        main_grid[3][0].setVisibility(View.INVISIBLE);

        main_grid[1][1].setVisibility(View.INVISIBLE);
        main_grid[2][1].setVisibility(View.INVISIBLE);
        main_grid[3][1].setVisibility(View.INVISIBLE);

        main_grid[1][2].setVisibility(View.INVISIBLE);
        main_grid[2][2].setVisibility(View.INVISIBLE);
        main_grid[3][2].setVisibility(View.INVISIBLE);

        main_grid[1][3].setVisibility(View.INVISIBLE);
        main_grid[2][3].setVisibility(View.INVISIBLE);
        main_grid[3][3].setVisibility(View.INVISIBLE);

        main_grid[1][4].setVisibility(View.INVISIBLE);
        main_grid[2][4].setVisibility(View.INVISIBLE);
        main_grid[3][4].setVisibility(View.INVISIBLE);

        main_coins[1][0].setVisibility(View.INVISIBLE);
        main_coins[2][0].setVisibility(View.INVISIBLE);
        main_coins[3][0].setVisibility(View.INVISIBLE);

        main_coins[1][3].setVisibility(View.INVISIBLE);
        main_coins[2][3].setVisibility(View.INVISIBLE);
        main_coins[3][3].setVisibility(View.INVISIBLE);

        policeCar[0].setVisibility(View.INVISIBLE);
        policeCar[1].setVisibility(View.INVISIBLE);
        policeCar[2].setVisibility(View.VISIBLE);
        policeCar[3].setVisibility(View.INVISIBLE);
        policeCar[4].setVisibility(View.INVISIBLE);

    }
    private void doVibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }
    private void showHeart(int life) {
        int GoInvisible = main_IMG_hearts.length-life;
        for(int i=0; i<GoInvisible;i++){
            main_IMG_hearts[i].setVisibility(View.INVISIBLE);
        }
    }
    private void stopObstacleMoving() {
        handler0.removeCallbacksAndMessages(null);
        handler1.removeCallbacksAndMessages(null);
        handler2.removeCallbacksAndMessages(null);
        handler3.removeCallbacksAndMessages(null);
        handler4.removeCallbacksAndMessages(null);


    }

    private void StartGameOrGOToScoreScreen() {
         ScoreManager scoreManager = new ScoreManager();
            if (MenuActivity.checkNewScore(scoreManager.getArrayOfScore(),gameManager.getScoreAsINT())) {
                //the new score inside the score array, now will show the score Intent ;
                stopObstacleMoving();
                openScoreScreen();
            }
            else
            {
                stopObstacleMoving();
                StartGame(playWithSensors,playWithArrows);
            }
        }



    @Override
   protected void onStop(){
        super.onStop();
        stopObstacleMoving();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if( playWithSensors) {
            stepDetector.start();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        stopObstacleMoving();
        if( playWithSensors) {
            stepDetector.stop();
        }
    }

    public void openScoreScreen() {
        Intent scoreIntent = new Intent(this, ScoreActivity.class);
       //scoreIntent.putExtra(ScoreActivity.KEY_SCORE, score);
        //scoreIntent.putExtra(ScoreActivity.KEY_STATUS, status);
        startActivity(scoreIntent);
        finish();
    }

    private void findViews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

        main_grid[0][1] = findViewById(R.id.Thief);

        main_grid[1][0] = findViewById(R.id.Ob1);
        main_grid[2][0] = findViewById(R.id.Ob2);
        main_grid[3][0] = findViewById(R.id.Ob3);

        main_grid[1][1] = findViewById(R.id.Ob4);
        main_grid[2][1] = findViewById(R.id.Ob5);
        main_grid[3][1] = findViewById(R.id.Ob6);

        main_grid[1][2] = findViewById(R.id.Ob7);
        main_grid[2][2] = findViewById(R.id.Ob8);
        main_grid[3][2] = findViewById(R.id.Ob9);

        main_grid[1][3] = findViewById(R.id.Ob10);
        main_grid[2][3] = findViewById(R.id.Ob11);
        main_grid[3][3] = findViewById(R.id.Ob12);

        main_grid[1][4] = findViewById(R.id.Ob13);
        main_grid[2][4] = findViewById(R.id.Ob14);
        main_grid[3][4] = findViewById(R.id.Ob15);

        main_coins[1][0] = findViewById(R.id.coin1);
        main_coins[2][0] = findViewById(R.id.coin2);
        main_coins[3][0] = findViewById(R.id.coin3);

        main_coins[1][3] = findViewById(R.id.coin4);
        main_coins[2][3] = findViewById(R.id.coin5);
        main_coins[3][3] = findViewById(R.id.coin6);


        policeCar = new ShapeableImageView[]{
                findViewById(R.id.carLeft2),
                findViewById(R.id.carLeft1),
                findViewById(R.id.carCenter),
                findViewById(R.id.carRight1),
                findViewById(R.id.carRight2)
        };

        floatingActionArrowsButtons = new FloatingActionButton[]{
                findViewById(R.id.main_BTN_LEFT),
                findViewById(R.id.main_BTN_Right)};

        Timer_Text = findViewById(R.id.TIMER_main_TXT);
    }

}