package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.drunkmel.drunkmel.helpers.ListViewAdapter;
import com.drunkmel.drunkmel.helpers.SortMapByValue;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {

    private SharedPreferences sp_PlayerList, sp_PlayerScore;
    private Button finalizeGameButton;
    private ListView listView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        loadUI();
    }

    private void loadUI() {
        finalizeGameButton = (Button) findViewById(R.id.finalizeGame);
        listView = (ListView) findViewById(R.id.resultsListView);

        setSharedPreferences();
        showScoreFromSharedPref();
        setListeners();
    }

    private void setSharedPreferences() {
        context = getApplicationContext();
        sp_PlayerList = context.getSharedPreferences(getString(R.string.preference_file_players_list), Context.MODE_PRIVATE);
        sp_PlayerScore = context.getSharedPreferences(getString(R.string.preference_file_players_score), Context.MODE_PRIVATE);
    }

    private void showScoreFromSharedPref() {

        //Get the scores for all players
        Map<String, ?> players = sp_PlayerList.getAll();
        Map<String, Integer> playersScores = new HashMap<>();
        int playersCount = 0;
        for (Map.Entry<String, ?> entry : players.entrySet()) {
            playersCount++;
            playersScores.put(entry.getValue().toString(), sp_PlayerScore.getInt(
                    entry.getKey().toString(), 0));
            }

        //Sort the Map
        Map<String, Integer> sortedMap = SortMapByValue.sortByComparator(playersScores, false);

        //Get the values to show them in the listView
        String[] finalPlayers = new String[playersCount];
        String[] finalScores = new String[playersCount];
        int index = 0;
        for (Map.Entry<String, ?> entry : sortedMap.entrySet()) {
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
