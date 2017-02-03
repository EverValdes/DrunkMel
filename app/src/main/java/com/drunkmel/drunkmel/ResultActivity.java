package com.drunkmel.drunkmel;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    //Variables
    TextView resultTitle;
    Typeface custom_font;
    Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        loadUI();
    }

    public void loadUI(){
        resultTitle = (TextView) findViewById(R.id.resultTitle);
        custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Frijole-Regular.ttf");
        resultTitle.setTypeface(custom_font);
        playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
