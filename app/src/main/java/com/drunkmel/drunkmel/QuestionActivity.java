package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drunkmel.drunkmel.helpers.SharedPreferencesManager;
import com.drunkmel.drunkmel.loaders.JSONLoader;

import org.json.JSONException;
import org.json.JSONObject;

public class QuestionActivity extends ActivityMel {

    private TextView questionTittle, questionTopic, questionDescription;
    private Button correctButton, incorrectButton;
    private Context context;
    private String penalty;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        context = getApplicationContext();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        loadUI();
    }

    private void loadUI() {
        questionTittle = (TextView) findViewById(R.id.questionTittle);
        questionTopic = (TextView) findViewById(R.id.questionTopic);
        questionDescription = (TextView) findViewById(R.id.questionDescription);
        correctButton = (Button) findViewById(R.id.correctButton);
        incorrectButton = (Button) findViewById(R.id.incorrectButton);

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

    private String getPlayerName() {
        return sharedPreferencesManager.getNextPlayer();
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