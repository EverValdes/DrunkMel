package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerActivity extends ActivityMel {

    //Variabes declaration
    private TextView playerLabel;
    private Typeface custom_font;
    private Button addPlayer;
    private Button addPlayer, nextButton;
    private LinearLayout playerItem;
    private SharedPreferences sp_PlayerList;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

// Create shared Preferences file to manage the players score and turn.
        createSharedPreferences();

        loadUI();
        setListeners(context);
    }

    private void createSharedPreferences() {
        context = getApplicationContext();

        // Creating new Shared Preferences file for players list
        sp_PlayerList = context.getSharedPreferences(
                getString(R.string.preference_file_players_list), Context.MODE_PRIVATE);

        // Creating new Shared Preferences file for players score
        sp_PlayerScore = context.getSharedPreferences(
                getString(R.string.preference_file_players_score), Context.MODE_PRIVATE);

        // Creating new Shared Preferences file for players turn
        sp_PlayerTurn = context.getSharedPreferences(
                getString(R.string.preference_file_players_turns), Context.MODE_PRIVATE);

        // Clean all the Shared Preferences before starting a new game
        context.getSharedPreferences(getString(R.string.preference_file_players_list), 0)
                .edit().clear().apply();
        context.getSharedPreferences(getString(R.string.preference_file_players_score), 0)
                .edit().clear().apply();
        context.getSharedPreferences(getString(R.string.preference_file_players_turns), 0)
                .edit().clear().apply();
    }

    private void loadUI(){
        //Find the elements
        playerLabel = (TextView) findViewById(R.id.playerLabel);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Frijole-Regular.ttf");
        playerLabel.setTypeface(custom_font);
        addPlayer = (Button) findViewById(R.id.addPlayer);
        next = (Button) findViewById(R.id.next);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
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
                String lastPlayer="";
                int index;
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
                // Set the last player to enable finalize button
                setPlayerTurn("lastTurn", lastPlayer);

                //Start new activity
                startActivity(i);
                finish();
            }
        });
    }

    public void createPlayerInTable(String player){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(player, 0);
        editor.commit();
    }

    private void setPlayerList(String id, String player) {
        sp_PlayerList.edit().putString(id, player).commit();
    }

    private void setPlayerScore(String id) {
        sp_PlayerScore.edit().putInt(id, 0).commit();
    }

    private void setPlayerTurn(String positionTurn, String playerName) {
        sp_PlayerTurn.edit().putString(positionTurn, playerName).commit();
    }
}
