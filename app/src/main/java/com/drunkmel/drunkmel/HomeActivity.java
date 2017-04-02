package com.drunkmel.drunkmel;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.drunkmel.drunkmel.model.PlayerHistory;

public class HomeActivity extends ActivityMel {
    private Button penaltyButton;
    private Button challengeButton;
    private Button questionButton;
    private Button playerHistoryButton;
    private Intent playerIntent;
    private TextView homeTitle;
    private Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadUI();
    }

    private void loadUI() {
        homeTitle = (TextView) findViewById(R.id.homeTitle);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Frijole-Regular.ttf");
        homeTitle.setTypeface(custom_font);

        penaltyButton = (Button) findViewById(R.id.penaltyButton);
        penaltyButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                playerIntent = new Intent(HomeActivity.this, PenaltyActivity.class);
                startActivity(playerIntent);
            }
        });

        challengeButton = (Button) findViewById(R.id.challengeButton);
        challengeButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                playerIntent = new Intent(HomeActivity.this, PlayerActivity.class);
                playerIntent.putExtra("GAME_MODE", "challenge");
                startActivity(playerIntent);
            }
        });

        questionButton = (Button) findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                playerIntent = new Intent(HomeActivity.this, PlayerActivity.class);
                playerIntent.putExtra("GAME_MODE", "question");
                startActivity(playerIntent);
            }
        });

        playerHistoryButton = (Button) findViewById(R.id.playerHistoryButton);
        playerHistoryButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                playerIntent = new Intent(HomeActivity.this, PlayerHistoryActivity.class);
                startActivity(playerIntent);
            }
        });
    }
}
