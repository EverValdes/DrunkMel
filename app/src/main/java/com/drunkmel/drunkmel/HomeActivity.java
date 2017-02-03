package com.drunkmel.drunkmel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends ActivityMel {
    private Button penaltyButton;
    private Button challengeButton;
    private Button questionButton;
    private Intent playerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadUI();
    }

    private void loadUI() {
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
    }
}
