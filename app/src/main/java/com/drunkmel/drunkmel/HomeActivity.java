package com.drunkmel.drunkmel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends ActivityMel {
    private Button challengeButton;
    private Button questionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadUI();
    }

    private void loadUI() {
        challengeButton = (Button) findViewById(R.id.challengeButton);
        challengeButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(HomeActivity.this, PlayerActivity.class);
                playerIntent.putExtra("GAME_MODE", "challenge");
                startActivity(playerIntent);
            }
        });

        questionButton = (Button) findViewById(R.id.questionButton);
        questionButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(HomeActivity.this, PlayerActivity.class);
                playerIntent.putExtra("GAME_MODE", "question");
                startActivity(playerIntent);
            }
        });
    }
}
