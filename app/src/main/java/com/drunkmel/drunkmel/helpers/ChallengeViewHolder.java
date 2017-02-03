package com.drunkmel.drunkmel.helpers;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import com.drunkmel.drunkmel.R;

/**
 * Created by everv on 2/1/2017.
 */

class ChallengeViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private TextView punishment;


    public ChallengeViewHolder(View view, boolean punishment) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.description = (TextView) view.findViewById(R.id.description);
        if (punishment == true){
            this.punishment = (TextView) view.findViewById(R.id.punishment);
        }
    }

    public ChallengeViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.description = (TextView) view.findViewById(R.id.description);
        this.punishment = (TextView) view.findViewById(R.id.punishment);
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getPunishment() {
        return punishment;
    }

    public void setPunishment(TextView punishment) {
        this.punishment = punishment;
    }

}
