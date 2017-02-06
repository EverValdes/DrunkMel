package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drunkmel.drunkmel.loaders.JSONLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionActivity extends AppCompatActivity {

    private TextView questionTittle, questionTopic, questionDescription;
    private Button correctButton, incorrectButton;
    private SharedPreferences sp_PlayerTurn;
    private Context context;
    private String penalty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        loadUI();
    }

    private void loadUI() {
        questionTittle = (TextView) findViewById(R.id.questionTittle);
        questionTopic = (TextView) findViewById(R.id.questionTopic);
        questionDescription = (TextView) findViewById(R.id.questionDescription);
        correctButton = (Button) findViewById(R.id.correctButton);
        incorrectButton = (Button) findViewById(R.id.incorrectButton);

        setSharedPreferences();
        questionTittle.setText(getResources().getString(R.string.turnOf) + " " + getPlayerName());

        try {
            JSONLoader jsonLoader = new JSONLoader(this);
            JSONObject obj = new JSONObject(jsonLoader.loadJSONFromRaw(context));

            questionTopic.setText(obj.getString("topic"));
            questionDescription.setText(obj.getString("description"));
            penalty = obj.getString("penalty");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        setListeners();
    }

    private void setSharedPreferences() {
        context = getApplicationContext();
        sp_PlayerTurn = context.getSharedPreferences(getString(R.string.preference_file_players_turns), Context.MODE_PRIVATE);
    }

    private String getPlayerName() {
        return sp_PlayerTurn.getString("nextPlayer", null);
    }

    private void setListeners() {
        correctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questAnsIntent = new Intent(QuestionActivity.this, QuestionAnswerActivity.class);
                startActivity(questAnsIntent);
                finish();
            }
        });

        incorrectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questAnsIntent = new Intent(QuestionActivity.this, QuestionAnswerActivity.class);
                questAnsIntent.putExtra("PENALTY", penalty);
                startActivity(questAnsIntent);
                finish();
            }
        });
    }
}