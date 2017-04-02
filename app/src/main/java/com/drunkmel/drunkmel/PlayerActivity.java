package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drunkmel.drunkmel.helpers.SharedPreferencesManager;
import com.drunkmel.drunkmel.model.PlayerHistory;
import com.drunkmel.drunkmel.persistent.DrunkMelDataBase;

import org.apache.commons.lang3.StringUtils;

public class PlayerActivity extends ActivityMel {

    private TextView playerLabel;
    private Typeface custom_font;
    private Button addPlayerButton, nextButton;
    private LinearLayout playerItem;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        sharedPreferencesManager = SharedPreferencesManager.getInstance(getApplicationContext());
        sharedPreferencesManager.createPlayerSharedPreferences();

        loadUI();
        setDefaultPlayers(1);
    }

    private void loadUI(){
        //Find the elements
        playerLabel = (TextView) findViewById(R.id.playerLabel);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Frijole-Regular.ttf");
        playerLabel.setTypeface(custom_font);
        addPlayerButton = (Button) findViewById(R.id.addPlayer);
        nextButton = (Button) findViewById(R.id.next);
        playerItem = (LinearLayout) findViewById(R.id.playerItem);

        setListeners(getApplicationContext());
        enableButton(nextButton, false);
    }

    private void setListeners(final Context context) {
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
                playerName.setHintTextColor(Color.WHITE);
                playerName.setTextColor(Color.WHITE);
                playerItem.addView(playerName);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Get data from previous intent and create a new one depending the game mode
                String gameMode = getIntent().getExtras().getString("GAME_MODE");
                String lastPlayer="";
                int index;
                Intent i;
                if (gameMode.equalsIgnoreCase("challenge")) {
                    i = new Intent(context, ChallengeActivity.class);
                } else {
                    i = new Intent(context, QuestionActivity.class);
                }

                //Get the name of all the players checking the children of the container
                for(index=0; index< playerItem.getChildCount(); ++index) {
                    EditText nextChild = (EditText) playerItem.getChildAt(index);

                    sharedPreferencesManager.setPlayerInList(index, nextChild.getText().toString());
                    sharedPreferencesManager.setPlayerScore(Integer.toString(index), 0);

                    if (index == 0) {
                        sharedPreferencesManager.setPlayerTurn(sharedPreferencesManager.NEXT_PLAYER, nextChild.getText().toString());
                    } else {
                        lastPlayer = nextChild.getText().toString();
                    }
                    // Add players to the database
                    PlayerHistory playerHistory = new PlayerHistory();
                    playerHistory.setName(nextChild.getText().toString());
                    playerHistory.setScore(0);
                    playerHistory.setPoints(0);

                    DrunkMelDataBase drunkMelDB = DrunkMelDataBase.getInstance(context);
                    drunkMelDB.addPlayerHistory(playerHistory);
                }
                // Set the last player to enable finalize button
                sharedPreferencesManager.setPlayerTurn(sharedPreferencesManager.LAST_TURN, lastPlayer);
                sharedPreferencesManager.setDefaultScores();
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
}
