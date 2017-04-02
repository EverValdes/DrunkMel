package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.drunkmel.drunkmel.helpers.ListViewAdapter;
import com.drunkmel.drunkmel.model.PlayerHistory;
import com.drunkmel.drunkmel.persistent.DrunkMelDataBase;

import java.util.List;

/**
 * Created by gm on 01/04/17.
 */
public class PlayerHistoryActivity extends ActivityMel {

    private Button goToHome;
    private ListView listView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_history);
        context = getApplicationContext();

        loadUI();
    }

    private void loadUI() {

        goToHome = (Button) findViewById(R.id.goToHome);
        listView = (ListView) findViewById(R.id.playerHistoryListView);

        showPlayerHistory();
        setListeners();
    }


    private void showPlayerHistory() {
        DrunkMelDataBase drunkMelDataBase = DrunkMelDataBase.getInstance(context);
        List<PlayerHistory> playerHistories = drunkMelDataBase.getCompletePlayerHistory();

        String[] playerName = new String[10];
        String[] playerScore = new String[10];
        String[] playerPoints = new String[10];
        int index = 0;
        for (PlayerHistory ph : playerHistories) {
            if (index < 10) {
                playerName[index] = ph.getName();
                playerScore[index] = String.valueOf(ph.getScore());
                playerPoints[index] = String.valueOf(ph.getPoints());
                index++;
            } else {
                break;
            }
        }
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, playerName, playerScore);
        listView.setAdapter(listViewAdapter);
    }

    private void setListeners() {
        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHome = new Intent(PlayerHistoryActivity.this, HomeActivity.class);
                startActivity(goToHome);
                finish();
            }
        });
    }
}
