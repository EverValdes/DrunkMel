package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    private SharedPreferences sp_PlayerList, sp_PlayerScore;
    private Button finalizeGameButton;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        loadUI();
    }

    private void loadUI() {
        finalizeGameButton = (Button) findViewById(R.id.finalizeGame);

        setSharedPreferences();
        showScoreFromSharedPref();
        setListeners();
    }

    private void setSharedPreferences() {
        context = getApplicationContext();
        sp_PlayerList = context.getSharedPreferences(getString(R.string.preference_file_players_list), Context.MODE_PRIVATE);
        sp_PlayerScore = context.getSharedPreferences(getString(R.string.preference_file_players_score), Context.MODE_PRIVATE);
    }

    private void showScoreFromSharedPref() {
        // TODO: show Score in order from SharedPref
    }

    private void setListeners() {
        finalizeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizeGame = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(finalizeGame);
                finish();
            }
        });
    }
}
