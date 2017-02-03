package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import org.apache.commons.lang3.StringUtils;
import android.widget.TextView;

public class PlayerActivity extends ActivityMel {

    //Variabes declaration
    Button addPlayerButton;
    Button nextButton;
    LinearLayout playerItem;

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
        //Set default quantity of players
        //TODO: make a constant or configurable prop to set the minimum quantity of players per game
        setDefaultPlayers(1);
        //enableNextButton();

    }

    public void loadUI(){
        //Find the elements
        addPlayerButton = (Button) findViewById(R.id.addPlayer);
        nextButton = (Button) findViewById(R.id.next);
        enableButton(nextButton, false);
        playerItem = (LinearLayout) findViewById(R.id.playerItem);


    }

    public void setListeners(final Context context) {
        //Listeners
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Creating a new edit text for a new player
                EditText playerName = new EditText(context);
                playerName.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        configureNextButton();
                    }
                });
                playerName.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                playerName.setFocusableInTouchMode(true);
                playerName.requestFocus();
                playerName.setHint(R.string.newPlayerHint);
                playerName.setHintTextColor(Color.GRAY);
                playerName.setTextColor(Color.BLACK);
                playerItem.addView(playerName);

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
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
                for(int index = 0; index < playerItem.getChildCount(); ++index) {
                    EditText nextChild = (EditText) playerItem.getChildAt(index);
                    createPlayerInTable(nextChild.getText().toString());
                }

                //Start new activity
                startActivity(i);
                finish();
            }
        });
    }

    private void setDefaultPlayers(int numberOfPlayers){
        for (int i = 1; i<= numberOfPlayers; i++){
            addPlayerButton.performClick();
        }
    }

    private void configureNextButton() {
        //All the fields for players into this activity
        int amountOfPlayers = playerItem.getChildCount();
        //If there are least than 2 players, don't enable the nextButton button
        if (amountOfPlayers < 2) {
            enableButton(nextButton, false);
        } else {
            for (int i = 0; i < amountOfPlayers; i++) {
                EditText player = (EditText) playerItem.getChildAt(i);
                if (StringUtils.isEmpty(player.getText())) {
                    enableButton(nextButton, false);
                    return;
                } else {
                    enableButton(nextButton, true);
                }
            }
        }
    }

    private void enableButton(Button button, boolean enable) {
        button.setEnabled(enable);
        if (enable) {
            button.setAlpha(1F);
        } else {
            button.setAlpha(0.7F);
        }
    }

    public void createPlayerInTable(String player){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(player, 0);
        editor.commit();
    }
}
