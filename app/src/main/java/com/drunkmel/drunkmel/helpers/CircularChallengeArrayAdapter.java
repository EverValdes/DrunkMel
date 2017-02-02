package com.drunkmel.drunkmel.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.model.ChallengeModel;
/**
 * Created by everv on 2/1/2017.
 */

public class CircularChallengeArrayAdapter extends RecyclerView.Adapter<ChallengeViewHolder > {
    private ArrayList<ChallengeModel> challenges;
    private Context context;

    public CircularChallengeArrayAdapter(Context context, ArrayList<ChallengeModel> challenges) {
        this.context = context;
        this.challenges = challenges;
    }


    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.challenge, null);
        ChallengeViewHolder challengeViewHolder = new ChallengeViewHolder(view);
        return challengeViewHolder;
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder challengeViewHolder, int position) {
        ChallengeModel challengeModel = challenges.get(position % challenges.size());
        challengeViewHolder.getTitle().setText(challengeModel.getTitle());
        challengeViewHolder.getDescription().setText(challengeModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealItemCount() {return challenges.size(); }

}