package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.SnapHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.drunkmel.drunkmel.helpers.CircularChallengeArrayAdapter;
import com.drunkmel.drunkmel.loaders.JSONLoader;
import com.drunkmel.drunkmel.model.ChallengeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

public class ChallengeActivity extends ActivityMel {
    //Challenges that need to be recorded into singleton/database
    private ArrayList<ChallengeModel> challenges = new ArrayList<ChallengeModel>();

    //Variables
    private Context context;
    private CircularChallengeArrayAdapter circularChallengeArrayAdapter;
    private Button pass, fail, endChallenge;
    private SharedPreferences sp_PlayerTurn, sp_PlayerList, sp_PlayerScore;
    private TextView challengeTitle;
    private String idActualPlayerStr;

    //Layout
    private RecyclerView carousel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JSONLoader jsonLoader = new JSONLoader(this);
        //Read json file "challenges"
        JSONArray jsonArray = jsonLoader.readData("challenges");
        createDataModel(jsonArray);

        context = getApplicationContext();
        //Get all keys from the Shared Preferences
        configureSharedPreferencies();
        loadUI();
    }

    private void configureSharedPreferencies() {
        sp_PlayerTurn = context.getSharedPreferences(getString(R.string.preference_file_players_turns), Context.MODE_PRIVATE);
        sp_PlayerList = context.getSharedPreferences(getString(R.string.preference_file_players_list), Context.MODE_PRIVATE);
        sp_PlayerScore = context.getSharedPreferences(getString(R.string.preference_file_players_score), Context.MODE_PRIVATE);
    }

    private void loadUI() {
        setContentView(R.layout.activity_challenge);
        pass = (Button) findViewById(R.id.pass);
        fail = (Button) findViewById(R.id.fail);
        endChallenge = (Button) findViewById(R.id.endChallenge);
        carousel = (RecyclerView) findViewById(R.id.carousel);
        challengeTitle = (TextView) findViewById(R.id.challengeTittle);
        challengeTitle.setText(getResources().getString(R.string.turnOf) + " " + getPlayerName());
        setUpCarousel();

        //Listeners
        pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int actualScore = sp_PlayerScore.getInt(getIdActualPlayer(), 0);
                sp_PlayerScore.edit().putInt(getIdActualPlayer(), (actualScore + 1)).apply();
                Intent i = new Intent(ChallengeActivity.this, ChallengeActivity.class);
                startActivity(i);
                finish();
            }
        });

        fail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChallengeActivity.this, ChallengeActivity.class);
                startActivity(i);
                finish();
            }
        });

        endChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finalizeInt = new Intent(ChallengeActivity.this, ScoreActivity.class);
                startActivity(finalizeInt);
                finish();
            }
        });
    }

    private String getPlayerName() {
        return sp_PlayerTurn.getString("nextPlayer", null);
    }

    private String getIdActualPlayer() {
        Map<String, ?> allEntries = sp_PlayerList.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue().toString().equalsIgnoreCase(getPlayerName())) {
                return entry.getKey();
            }
        }
        return "";
    }

    private boolean isLastTurn() {
        if (getPlayerName().equalsIgnoreCase(sp_PlayerTurn.getString("lastTurn", null))) {
            return true;
        }
        return false;
    }

    private void setNextTurn() {
        if (isLastTurn()) {
            // The next turn will be the player with id = 0
            sp_PlayerTurn.edit().putString("nextPlayer", sp_PlayerList.getString("0", null)).apply();
            return;
        }

        idActualPlayerStr = getIdActualPlayer();
        int idActualPlayer = Integer.parseInt(idActualPlayerStr);
        String nextTurnId = String.valueOf(idActualPlayer + 1);
        sp_PlayerTurn.edit().putString("nextPlayer",sp_PlayerList.getString(nextTurnId,null)).apply();
    }

    private void setUpCarousel() {
        //Snap Helper to avoid having two challenges in the scrren
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(carousel);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        circularChallengeArrayAdapter = new CircularChallengeArrayAdapter(this, challenges);
        carousel.setAdapter(circularChallengeArrayAdapter);
        //Star on the middle of the challenges
        llm.scrollToPosition(circularChallengeArrayAdapter.getItemCount()/2);
        carousel.setLayoutManager(llm);

       carousel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {

                    //Disable the touch when the scrolling ends
                    recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                        @Override
                        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                            return true;
                        }
                        @Override
                        public void onTouchEvent(RecyclerView rv, MotionEvent e) {}
                        @Override
                        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
                    });

                    pass.setVisibility(View.VISIBLE);
                    fail.setVisibility(View.VISIBLE);
                    endChallenge.setVisibility(View.VISIBLE);

                    setNextTurn();
                }
            }
        });
    }

    private void createDataModel(JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject challengeObject = jsonArray.getJSONObject(i);
                JSONArray challengeKeys = challengeObject.names();
                ChallengeModel challengeModel = new ChallengeModel();
                for (int j = 0; j < challengeKeys.length(); j++) {
                    String key = challengeKeys.getString(j);
                    String value = challengeObject.getString(key);
                    switch (key) {
                        case "title": challengeModel.setTitle(value);
                            break;
                        case "description": challengeModel.setDescription(value);
                            break;
                        case "punishment": challengeModel.setPunishment(value);
                            break;
                    }
                }
                this.challenges.add(challengeModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
