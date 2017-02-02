package com.drunkmel.drunkmel.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.drunkmel.drunkmel.R;

/**
 * Created by everv on 2/1/2017.
 */

class ChallengeViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;

    public ChallengeViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.description = (TextView) view.findViewById(R.id.description);
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


}
