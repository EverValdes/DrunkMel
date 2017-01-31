package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PlayerActivity extends ActivityMel {

    //Variabes declaration
    Button addPlayer;
    Button next;
    LinearLayout linearLayout;
    ArrayList<String> players = new ArrayList<String>();

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
                //Creating a new edit text for a new player
                EditText playerItem = new EditText(context);
                playerItem.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                playerItem.setFocusableInTouchMode(true);
                playerItem.requestFocus();
                playerItem.setHint(R.string.newPlayerHint);
                playerItem.setHintTextColor(Color.GRAY);
                playerItem.setTextColor(Color.BLACK);
                linearLayout.addView(playerItem);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get data from previous intent and create a new one
                String gameMode = getIntent().getExtras().getString("GAME_MODE");
                Intent i = new Intent(context, ChallengeActivity.class);

                //Get the name of all the players checking the childrens of the container
                for(int index=0; index<linearLayout.getChildCount(); ++index) {
                    EditText nextChild = (EditText) linearLayout.getChildAt(index);
                    players.add(nextChild.getText().toString());
                }

                //Set players and game mode in the intent and start new activity
                i.putExtra("PLAYERS", players);
                i.putExtra("GAME_MODE", gameMode);
                startActivity(i);
            }
        });

    }

}
