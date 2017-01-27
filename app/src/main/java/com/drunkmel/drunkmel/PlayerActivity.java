package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class PlayerActivity extends AppCompatActivity {

    //Variabes declaration
    ListView listView;
    Button addPlayer;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        //Load the UI elements and listeners
        Context context = getApplicationContext();
        loadUI(context);

        PlayersListViewAdapter listViewAdapter = new PlayersListViewAdapter(context);
        listView.setAdapter(listViewAdapter);
    }

    public void loadUI(final Context context){
        //Find the elements
        listView = (ListView) findViewById(R.id.playerList);
        addPlayer = (Button) findViewById(R.id.addPlayer);
        next = (Button) findViewById(R.id.next);
        //Listeners
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(context, ChallengeActivity.class));
            }
        });

    }

}
