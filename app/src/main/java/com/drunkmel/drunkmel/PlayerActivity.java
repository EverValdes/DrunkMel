package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerActivity extends ActivityMel {

    private TextView playerLabel;
    private Typeface custom_font;
    private Button addPlayer, next;
    private LinearLayout linearLayout;
    private SharedPreferences sp_PlayerList, sp_PlayerScore, sp_PlayerTurn;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Create shared Preferences file to manage the players score and turn.
        createSharedPreferences();

        loadUI();
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

        setListeners(context);
    }

    private void setListeners(final Context context) {
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
                playerItem.setHintTextColor(Color.WHITE);
                playerItem.setTextColor(Color.WHITE);
                linearLayout.addView(playerItem);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
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
                for(index=0; index<linearLayout.getChildCount(); ++index) {
                    EditText nextChild = (EditText) linearLayout.getChildAt(index);

                    setPlayerList(Integer.toString(index), nextChild.getText().toString());
                    setPlayerScore(Integer.toString(index));

                    if (index == 0) {
                        setPlayerTurn("nextPlayer", nextChild.getText().toString());
                    } else {
                        lastPlayer = nextChild.getText().toString();
                    }
                }
                // Set the last player to enable finalize button
                setPlayerTurn("lastTurn", lastPlayer);

                startActivity(i);
                finish();
            }
        });
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
