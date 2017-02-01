package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class PlayerActivity extends ActivityMel {

    //Variabes declaration
    Button addPlayer;
    Button next;
    LinearLayout linearLayout;
    SharedPreferences sharedPref;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //Get the Shared Preferences file
        context = getApplicationContext();
        sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //Clean the Shared Preferences before starting a new game
        context.getSharedPreferences(getString(R.string.preference_file_key), 0)
                .edit().clear().apply();

        //Load the UI elements
        loadUI();

        //Set the listeners
        setListeners(context);
    }

    public void loadUI(){
        //Find the elements
        addPlayer = (Button) findViewById(R.id.addPlayer);
        next = (Button) findViewById(R.id.next);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
    }

    public void setListeners(final Context context) {
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
                //Get data from previous intent and create a new one depending the game mode
                String gameMode = getIntent().getExtras().getString("GAME_MODE");
                Intent i;
                if(gameMode.equalsIgnoreCase("challenge")) {
                    i = new Intent(context, ChallengeActivity.class);
                } else {
                    i = new Intent(context, QuestionActivity.class);
                }

                //Get the name of all the players checking the childrens of the container
                for(int index=0; index<linearLayout.getChildCount(); ++index) {
                    EditText nextChild = (EditText) linearLayout.getChildAt(index);
                    createPlayerInTable(nextChild.getText().toString());
                }

                //Start new activity
                startActivity(i);
            }
        });
    }

    public void createPlayerInTable(String player){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(player, 0);
        editor.commit();
    }
}
