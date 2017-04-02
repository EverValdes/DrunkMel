package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.drunkmel.drunkmel.helpers.ListViewAdapter;
import com.drunkmel.drunkmel.helpers.SharedPreferencesManager;
import com.drunkmel.drunkmel.helpers.SortMapByValue;
import com.drunkmel.drunkmel.persistent.DrunkMelDataBase;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends ActivityMel {

    private SharedPreferencesManager sharedPreferencesManager;
    private Button finalizeGameButton;
    private ListView listView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        context = getApplicationContext();
        sharedPreferencesManager = SharedPreferencesManager.getInstance(context);
        loadUI();
    }

    private void loadUI() {
        finalizeGameButton = (Button) findViewById(R.id.finalizeGame);
        listView = (ListView) findViewById(R.id.resultsListView);

        showScoreFromSharedPref();
        setListeners();
    }

    private void showScoreFromSharedPref() {

        //Get the scores for all players
        Map<String, ?> players = sharedPreferencesManager.getAllPlayers();
        Map<String, Integer> playersScores = new HashMap<>();
        int playersCount = 0;
        for (Map.Entry<String, ?> entry : players.entrySet()) {
            playersCount++;
            playersScores.put(entry.getValue().toString(), sharedPreferencesManager.getPlayerScore(sharedPreferencesManager.getPlayerInList(entry.getKey())));
            }

        //Sort the Map
        Map<String, Integer> sortedMap = SortMapByValue.sortByComparator(playersScores, false);

        //Get the values to show them in the listView
        String[] finalPlayers = new String[playersCount];
        String[] finalScores = new String[playersCount];
        int index = 0;
        for (Map.Entry<String, ?> entry : sortedMap.entrySet()) {
            if (index == 0) {
                DrunkMelDataBase drunkMelDB = DrunkMelDataBase.getInstance(context);
                drunkMelDB.updatePlayerHistory(entry.getKey().toString(), (Integer) entry.getValue());
            }
            finalPlayers[index] = entry.getKey();
            finalScores[index] = entry.getValue().toString();
            index++;
        }
        ListViewAdapter listViewAdapter = new ListViewAdapter(this, finalPlayers, finalScores);
        listView.setAdapter(listViewAdapter);

    }

    private void setListeners() {
        finalizeGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizeGame = new Intent(ScoreActivity.this, HomeActivity.class);
                startActivity(finalizeGame);
                finish();
            }
        });
    }

}
