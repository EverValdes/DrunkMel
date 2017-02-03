package com.drunkmel.drunkmel.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.model.ChallengeModel;

import java.util.ArrayList;
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
        ChallengeViewHolder challengeViewHolder = new ChallengeViewHolder(view, true);
        return challengeViewHolder;
    }

    @Override
    public void onBindViewHolder(ChallengeViewHolder challengeViewHolder, int position) {
        ChallengeModel challengeModel = challenges.get(position % challenges.size());
        challengeViewHolder.getTitle().setText(challengeModel.getTitle());

        SpannableString spanString = new SpannableString(challengeViewHolder.getTitle().getText());
        spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
        challengeViewHolder.getTitle().setText(spanString);

        challengeViewHolder.getDescription().setText(challengeModel.getDescription());
        String sourceString = "<b>" + context.getResources().getText(R.string.punishmentBoldInCarousel) + "</b> " + challengeModel.getPenalty();
        challengeViewHolder.getPunishment().setText(Html.fromHtml(sourceString));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealItemCount() {return challenges.size(); }

}