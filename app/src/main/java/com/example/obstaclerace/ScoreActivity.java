package com.example.obstaclerace;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obstaclerace.Adapters.ScoreAdapter;
import com.example.obstaclerace.Fragments.ListFragment;
import com.example.obstaclerace.Fragments.MapFragment;
import com.example.obstaclerace.Interfaces.CallBack_SendClick;
import com.example.obstaclerace.Logic.ScoreManager;

public class ScoreActivity extends AppCompatActivity{
    private ListFragment listFragment;
    private MapFragment mapFragment;
    private Button StartAgain;



    CallBack_SendClick RecordCallBack = new CallBack_SendClick() {
        @Override
        public void ScoreChosen(Double CoordinateX, Double CoordinateY) {
            mapFragment.zoomOnRecord(CoordinateX, CoordinateY);
            Log.d("TAG", String.valueOf("ScoreActivity - ScoreChosen" + CoordinateX + CoordinateY ));

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent prevIntent = getIntent();
        findView();
        initFragments();
        beginTransactions();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_FRAME_map,mapFragment).commit();
        ListFragment.setCallBack_sendClick(RecordCallBack);

        StartAgain.setOnClickListener(view -> {
            Intent menuIntent = new Intent(this, MenuActivity.class);
            startActivity(menuIntent);
            finish();
        });


    }



    private void initFragments() {
        listFragment = new ListFragment();

        listFragment.setCallBack_sendClick(RecordCallBack);
        mapFragment = new MapFragment();

        StartAgain = findViewById(R.id.startAgain_BTN);
    }

    private void findView() {
    }
}