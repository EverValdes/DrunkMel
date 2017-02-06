package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

public class QuestionAnswerActivity extends AppCompatActivity {

    private SharedPreferences sp_PlayerList, sp_PlayerScore, sp_PlayerTurn;
    private Button continueButton, finalizeButton;
    private TextView tittleTextView;
    private Context context;
    private String idActualPlayerStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        loadUI();
    }

    private void loadUI() {
        continueButton = (Button) findViewById(R.id.questionAnswerContinueButton);
        finalizeButton = (Button) findViewById(R.id.questionAnswerFinalizeButton);
        tittleTextView = (TextView) findViewById(R.id.questionAnswerTittle);

        setSharedPreferences();

        // If we receive a penalty, the player answered incorrectly
        if (getIntent().getExtras() != null) {
            tittleTextView.setText(getIntent().getExtras().getString("PENALTY"));
        } else {
            tittleTextView.setText(getResources().getString(R.string.questionAnswerTittle));
            // Only update the score when the player answered correctly
            updateScore();
        }

        if (isLastTurn()) {
            finalizeButton.setVisibility(View.VISIBLE);
        }
        setNextTurn();
        setListeners();
    }

    private void setSharedPreferences() {
        context = getApplicationContext();

        sp_PlayerList = context.getSharedPreferences(getString(R.string.preference_file_players_list), Context.MODE_PRIVATE);
        sp_PlayerScore = context.getSharedPreferences(getString(R.string.preference_file_players_score), Context.MODE_PRIVATE);
        sp_PlayerTurn = context.getSharedPreferences(getString(R.string.preference_file_players_turns), Context.MODE_PRIVATE);
    }

    private void updateScore() {
        int actualScore = sp_PlayerScore.getInt(getIdActualPlayer(), 0);
        sp_PlayerScore.edit().putInt(getIdActualPlayer(), (actualScore + 1)).apply();
    }

    private String getIdActualPlayer() {
        Map<String, ?> allEntries = sp_PlayerList.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase(getPlayerName())) {
                return entry.getKey();
            }
        }
        return "";
    }

    private String getPlayerName() {
        return sp_PlayerTurn.getString("nextPlayer", null);
    }

    private boolean isLastTurn() {
        if (getPlayerName().equalsIgnoreCase(sp_PlayerTurn.getString("lastTurn", null))) {
            return true;
        }
        return false;
    }

    private void setNextTurn() {
        if (isLastTurn()) {
            // The next turn will be the player with id = 0
            sp_PlayerTurn.edit().putString("nextPlayer", sp_PlayerList.getString("0", null)).apply();
            return;
        }

        idActualPlayerStr = getIdActualPlayer();
        int idActualPlayer = Integer.parseInt(idActualPlayerStr);
        String nextTurnId = String.valueOf(idActualPlayer + 1);
        sp_PlayerTurn.edit().putString("nextPlayer",sp_PlayerList.getString(nextTurnId,null)).apply();
    }

    private void setListeners() {
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continueInt = new Intent(QuestionAnswerActivity.this, QuestionActivity.class);
                startActivity(continueInt);
                finish();
            }
        });

        finalizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizeInt = new Intent(QuestionAnswerActivity.this, ScoreActivity.class);
                startActivity(finalizeInt);
                finish();
            }
        });
    }
}
