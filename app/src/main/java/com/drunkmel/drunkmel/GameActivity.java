package com.drunkmel.drunkmel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameActivity extends AppCompatActivity {
    private Button standartGame;
    private Button customGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadUI();
    }

    private void loadUI() {
        getActionBar().setTitle(R.string.playerActivityTitle);

        standartGame = (Button) findViewById(R.id.standartGame);
        standartGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(GameActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }

        });

        customGame = (Button) findViewById(R.id.customGame);
        customGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent challengeIntent = new Intent(GameActivity.this, ChallengeActivity.class);
                startActivity(challengeIntent);
            }

        });
    }
}
