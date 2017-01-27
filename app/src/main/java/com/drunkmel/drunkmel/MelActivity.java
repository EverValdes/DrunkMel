package com.drunkmel.drunkmel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MelActivity extends AppCompatActivity {
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mel);
        loadUI();
    }

    private void loadUI() {
        startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(MelActivity.this, GameActivity.class);
                startActivity(playerIntent);
            }
        });

        Button challengeButton = (Button) findViewById(R.id.challenge);
        challengeButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(MelActivity.this, ChallengeActivity.class);
                startActivity(playerIntent);
            }
        });

    }
}
