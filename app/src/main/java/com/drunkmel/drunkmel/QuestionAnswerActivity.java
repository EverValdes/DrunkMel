package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.drunkmel.drunkmel.helpers.SharedPreferencesManager;

public class QuestionAnswerActivity extends ActivityMel {

    private Button continueButton, finalizeButton;
    private TextView tittleTextView;
    private Context context;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        context = getApplicationContext();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(context);
        loadUI();
    }

    private void loadUI() {
        continueButton = (Button) findViewById(R.id.questionAnswerContinueButton);
        finalizeButton = (Button) findViewById(R.id.questionAnswerFinalizeButton);
        tittleTextView = (TextView) findViewById(R.id.questionAnswerTittle);

        // If we receive a penalty, the player answered incorrectly
        if (getIntent().getExtras() != null) {
            tittleTextView.setText(getIntent().getExtras().getString("PENALTY"));
        } else {
            tittleTextView.setText(getResources().getString(R.string.questionAnswerTittle));
            // Only update the score when the player answered correctly
            sharedPreferencesManager.updateScore();
        }

        if (sharedPreferencesManager.isLastTurn()) {
            finalizeButton.setVisibility(View.VISIBLE);
        }
        sharedPreferencesManager.increaseTurn();
        setListeners();
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
