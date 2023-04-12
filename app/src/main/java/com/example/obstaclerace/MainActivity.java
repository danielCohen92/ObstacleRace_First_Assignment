package com.example.obstaclerace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Build;
import android.os.Bundle;
import android.content.Context;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import com.example.obstaclerace.Logic.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    public AppCompatImageView main_IMG_background;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] policeCar;
    final private ShapeableImageView[][] main_grid = new ShapeableImageView[5][5];
    private FloatingActionButton[] floatingActionArrowsButtons;
    private GameManager gameManager;
    private Handler handler0;
    private Handler handler1;
    private Handler handler2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        StartGame();
    }
    private void StartGame() {
        VisibleInStart();
        gameManager.setLife(main_IMG_hearts.length); //for Re-Game
        gameManager.setNumOfCrash(0);
        moveCols0();
        moveCols1();
        moveCols2();
        playGame();
    }
    private void moveCols0() {
        handler0 = new Handler();
        Runnable runnable = new Runnable() {
            int col0row = 1;
             //rows go from 1 to 3
            @Override
            public void run() {
                if (col0row == 1) {
                    main_grid[1][0].setVisibility(View.VISIBLE);
                    main_grid[2][0].setVisibility(View.INVISIBLE);
                    main_grid[3][0].setVisibility(View.INVISIBLE);
                    col0row++;
                } else if (col0row == 2) {
                    main_grid[1][0].setVisibility(View.INVISIBLE);
                    main_grid[2][0].setVisibility(View.VISIBLE);
                    main_grid[3][0].setVisibility(View.INVISIBLE);
                    col0row++;
                } else {
                    main_grid[1][0].setVisibility(View.INVISIBLE);
                    main_grid[2][0].setVisibility(View.INVISIBLE);
                    main_grid[3][0].setVisibility(View.VISIBLE);
                    if(policeCar[0].getVisibility() == View.VISIBLE){
                        Toast.makeText(MainActivity.this, "Crash!",Toast.LENGTH_SHORT).show();
                        doVibrate();
                        gameManager.RemoveLifeBecauseCrash(true); //crash happend
                        showHeart(gameManager.getLife());
                        if(gameManager.isGameEnded(main_IMG_hearts.length)){
                            Toast.makeText(MainActivity.this, "Start Again!",Toast.LENGTH_LONG).show();
                            stopObstacleMoving();
                            StartGame();
                        }
                    }
                    col0row = 1;
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
                            Toast.makeText(MainActivity.this, "Start Again!",Toast.LENGTH_LONG).show();
                            stopObstacleMoving();
                            StartGame();
                        }

                    }
                    col2row = 1;
                }
                handler1.postDelayed(this, 5000);

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
                            Toast.makeText(MainActivity.this, "Start Again!",Toast.LENGTH_LONG).show();
                            stopObstacleMoving();
                            StartGame();
                        }
                    }
                    col2row = -1;
                }
                handler2.postDelayed(this, 2500);
            }
        };
        handler2.post(runnable);
    }
    private void playGame() {
        final int[] stateOfCar = {0};
        //states of cars -> 0 = middle , 1 = right , -1 = left
        //Pressing Left Arrow
        floatingActionArrowsButtons[0].setOnClickListener(view -> {
            if(stateOfCar[0] == 0) {
                policeCar[0].setVisibility(View.VISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                stateOfCar[0]--;
                }


            else if (stateOfCar[0] == 1)
            {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.VISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                stateOfCar[0]--;
            }
        });
        //Pressing Right Arrow
        floatingActionArrowsButtons[1].setOnClickListener(view -> {
            if(stateOfCar[0] == 0) {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.INVISIBLE);
                policeCar[2].setVisibility(View.VISIBLE);
                stateOfCar[0]++;
            }
            else if (stateOfCar[0] == -1)
            {
                policeCar[0].setVisibility(View.INVISIBLE);
                policeCar[1].setVisibility(View.VISIBLE);
                policeCar[2].setVisibility(View.INVISIBLE);
                stateOfCar[0]++;
            }
        });
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

        policeCar[0].setVisibility(View.INVISIBLE);
        policeCar[1].setVisibility(View.VISIBLE);
        policeCar[2].setVisibility(View.INVISIBLE);

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
    }

    @Override
   protected void onStop(){
        super.onStop();
        stopObstacleMoving();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
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

        policeCar = new ShapeableImageView[]{
                findViewById(R.id.carLeft),
                findViewById(R.id.carMiddle),
                findViewById(R.id.carRight)
        };

        floatingActionArrowsButtons = new FloatingActionButton[]{
                findViewById(R.id.main_BTN_LEFT),
                findViewById(R.id.main_BTN_Right)};
    }
}