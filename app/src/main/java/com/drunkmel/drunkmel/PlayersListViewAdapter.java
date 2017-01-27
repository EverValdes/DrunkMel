package com.drunkmel.drunkmel;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by JuanManuel on 26/1/2017.
 */

public class PlayersListViewAdapter extends ArrayAdapter {

    private final Context context;

    public PlayersListViewAdapter(Context context) {
        super(context, 0);
        this.context = context;
    }

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.post, null);

            viewHolder = new ViewHolder();
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.description = (TextView)convertView.findViewById(R.id.description);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.title.setText(titles[position]);
        viewHolder.description.setText(descriptions[position]);
        viewHolder.image.setImageResource(imageId[position]);

        return convertView;
    }
    */
}
