package com.drunkmel.drunkmel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.drunkmel.drunkmel.loaders.JSONLoader;

public class ChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        JSONLoader jsonLoader = new JSONLoader(this);
        jsonLoader.readData();
    }

    private void loadUI() {

    }
}
