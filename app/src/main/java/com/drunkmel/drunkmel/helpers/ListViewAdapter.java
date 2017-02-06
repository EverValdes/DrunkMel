package com.drunkmel.drunkmel.helpers;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.drunkmel.drunkmel.R;

/**
 * Created by jrodriguez on 2/6/17.
 */

public class ListViewAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] finalPlayers;
    private final String[] finalScores;

    public ListViewAdapter(Activity context, String[] finalPlayers, String[] finalScores) {
        super(context, -1, finalPlayers);
        this.context = context;
        this.finalPlayers = finalPlayers;
        this.finalScores = finalScores;
    }

    static class ViewHolder {
        public TextView finalPlayer;
        public TextView finalScore;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.score_item, null);

            viewHolder = new ViewHolder();
            viewHolder.finalPlayer = (TextView)convertView.findViewById(R.id.finalPlayer);
            viewHolder.finalScore = (TextView)convertView.findViewById(R.id.finalScore);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.finalPlayer.setText(finalPlayers[position]);
        viewHolder.finalScore.setText(finalScores[position]);

        return convertView;
    }
}
