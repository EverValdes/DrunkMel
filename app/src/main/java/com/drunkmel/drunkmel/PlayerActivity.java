package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

public class PlayerActivity extends AppCompatActivity {

    //Variabes declaration
    Button addPlayer;
    Button next;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Load the UI elements and listeners
        Context context = getApplicationContext();
        loadUI(context);
    }

    public void loadUI(final Context context){
        //Find the elements
        addPlayer = (Button) findViewById(R.id.addPlayer);
        next = (Button) findViewById(R.id.next);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //Listeners
        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText playerItem = new EditText(context);
                playerItem.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(playerItem);
                playerItem.setHint("New Player");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(context, ChallengeActivity.class));
            }
        });

    }

}
