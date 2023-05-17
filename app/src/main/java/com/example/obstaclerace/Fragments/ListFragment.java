package com.example.obstaclerace.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.obstaclerace.Interfaces.CallBack_SendClick;
import com.example.obstaclerace.Logic.ScoreManager;
import com.example.obstaclerace.Models.Score;
import com.example.obstaclerace.R;


import com.example.obstaclerace.Adapters.ScoreAdapter;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private RecyclerView score_LST;
    private CallBack_SendClick recordCallBack;

    public ListFragment() {
    }

    public static void setCallBack_sendClick(CallBack_SendClick RecordCallBack) {
        RecordCallBack = RecordCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initView();
   
        return view;
    }

  


    private void initView() {
        ArrayList<Score> scores = ScoreManager.getArrayScoreAfterAddingNewScore();
        ScoreAdapter scoreAdapter = new ScoreAdapter(getContext(), scores);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        score_LST.setAdapter(scoreAdapter);
        score_LST.setLayoutManager(linearLayoutManager);
    }




    private void findViews(View view) {
        score_LST = view.findViewById(R.id.score_LST);
    }


}