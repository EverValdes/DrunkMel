package com.drunkmel.drunkmel.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.model.ChallengeModel;
import com.drunkmel.drunkmel.model.PenaltyModel;

import java.util.ArrayList;

/**
 * Created by everv on 2/1/2017.
 */

public class CircularPenaltyArrayAdapter extends RecyclerView.Adapter<ChallengeViewHolder > {
    private ArrayList<PenaltyModel> penalties;
    private Context context;

    public CircularPenaltyArrayAdapter(Context context, ArrayList<PenaltyModel> penalties) {
        this.context = context;
        this.penalties = penalties;
    }


    @Override
    public ChallengeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.penalty, null);
        ChallengeViewHolder penaltyViewHolder = new ChallengeViewHolder(view);
        return penaltyViewHolder;
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder penaltyViewHolder, int position) {
        PenaltyModel penaltyModel = penalties.get(position % penalties.size());
        penaltyViewHolder.getTitle().setText(penaltyModel.getTitle());
        penaltyViewHolder.getDescription().setText(penaltyModel.getDescription());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

}