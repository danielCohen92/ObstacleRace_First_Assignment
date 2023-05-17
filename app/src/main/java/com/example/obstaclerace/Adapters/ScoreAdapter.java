package com.example.obstaclerace.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obstaclerace.Interfaces.CallBack_SendClick;
import com.example.obstaclerace.Interfaces.ScoreCallback;
import com.example.obstaclerace.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import com.example.obstaclerace.Models.Score;

public class ScoreAdapter extends RecyclerView.Adapter <ScoreAdapter.ScoreViewHolder> {

    private Context context;
    private ArrayList<Score> Scores;

    private ScoreCallback scoreCallback;
    private CallBack_SendClick CallBack_SendClick;

    public ScoreAdapter(Context context, ArrayList<Score> Scores) {
        this.context = context;
        this.Scores = Scores;
    }


    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("passed VT:", "" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        ScoreViewHolder scoreViewHolder = new ScoreViewHolder(view);
        return scoreViewHolder;    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapter.ScoreViewHolder holder, int position) {
        Score score = getItem(position);
        holder.record_Number.setText(position+ 1 + ")");
        holder.record_LBL_UserNameAndScore.setText(score.getUserNameAndScore());

    }
    public void setScoreCallback(ScoreCallback scoreCallback) {
        this.scoreCallback = scoreCallback;
    }


    @Override
    public int getItemCount() {
        return this.Scores == null ? 0 : Scores.size();
    }

    private Score getItem(int position) {
        return this.Scores.get(position);
    }

    public class ScoreViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView record_IMG_map;
        private MaterialTextView record_LBL_UserNameAndScore;

        private MaterialTextView record_Number;


        public ScoreViewHolder(@NonNull View itemView) {

            super(itemView);
            record_Number = itemView.findViewById(R.id.record_NUMBER);
            record_IMG_map = itemView.findViewById(R.id.record_IMG_map);
            record_LBL_UserNameAndScore = itemView.findViewById(R.id.userName_LBL_title);

            itemView.setOnClickListener(v -> {
                Log.d("TAG", String.valueOf("PressOnRecord"));

                if (scoreCallback != null)
                    scoreCallback.itemClicked(getItem(getAdapterPosition()), getAdapterPosition());
            });
            record_IMG_map.setOnClickListener(v -> {
                Log.d("TAG", String.valueOf("PressOnMap"));
                Log.d("CoorX" , String.valueOf(getItem(getAdapterPosition()).getCoordinateX()));
                Log.d("CoorY" , String.valueOf(getItem(getAdapterPosition()).getCoordinateY()));

                if(CallBack_SendClick!=null){
                    CallBack_SendClick.ScoreChosen(getItem(getAdapterPosition()).getCoordinateX(),getItem(getAdapterPosition()).getCoordinateY());
                }
            });
        }
    }





}
