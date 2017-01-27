package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

public class PlayerActivity extends AppCompatActivity {

    //Variabes declaration
    Button addPlayer;
    Button next;
    ScrollView scrollView;
    EditText playerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Load the UI elements and listeners
        Context context = getApplicationContext();
        loadUI(context);
    }

    public void loadUI(final Context context){
        //Find the elements
        addPlayer = (Button) findViewById(R.id.addPlayer);
        next = (Button) findViewById(R.id.next);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        playerItem = (EditText) findViewById(R.id.playerItem);
        //Listeners
        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*
                TODO
                Check how to add a view into a scrollview programmatically
                 */
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(context, ChallengeActivity.class));
            }
        });

    }

}
