package com.example.obstaclerace.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.obstaclerace.Interfaces.StepCallBack;
import com.example.obstaclerace.MenuActivity;


public class StepDetector {

    private Sensor sensor;
    private SensorManager sensorManager;
    private StepCallBack stepCallBack;

    private boolean moveRight;

    private boolean moveLeft;

    private int stepCounterX = 0;
    private int stepCounterY = 0;
    private long timestamp = 0;
    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallBack stepCallBack) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallBack = stepCallBack;
        initEventListener();
    }
    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };

    }
    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 30) {
            timestamp = System.currentTimeMillis();
            if (x > 6.0) {
                //stepCounterX++;
                moveRight = true;
                moveLeft = false;
                stepCallBack.stepMinusX();
            }
            if (x < - 6.0) {
                //stepCounterY++;
                moveLeft = true;
                moveRight = false;
                stepCallBack.stepX();
            }
        }
    }

    public boolean getMoveLeft(){
        if(moveLeft) {
            return true;
        }
        return false;
    }
    public boolean getMoveRight(){
        if(moveRight) {
            return true;
        }
        return false;
    }


    public int getStepsY() {
        return this.stepCounterY;
    }

    public int getStepsX() {
        return this.stepCounterX;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }



}
