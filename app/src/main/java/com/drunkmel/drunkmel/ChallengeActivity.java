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
import android.widget.LinearLayout;
import android.widget.Toast;
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
    Context context;
    private CircularChallengeArrayAdapter circularChallengeArrayAdapter;
    ArrayList<String> players = new ArrayList<>();
    LinearLayout selectionButtons;
    Button pass;
    Button fail;
    SharedPreferences sharedPref;

    //Layout
    RecyclerView carousel;

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
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();
        int index = 0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            players.add(index, entry.getKey());
            index++;
        }

        //Example to get values from the Shared Preferences - to be deleted
        int value = sharedPref.getInt(players.get(0), 25);
        Toast.makeText(context, players.get(0) + " " + value, Toast.LENGTH_LONG).show();
    }

    private void loadUI() {
        setContentView(R.layout.activity_challenge);
        selectionButtons = (LinearLayout) findViewById(R.id.selectionButtons);
        pass = (Button) findViewById(R.id.pass);
        fail = (Button) findViewById(R.id.fail);
        carousel = (RecyclerView) findViewById(R.id.carousel);
        setUpCarousel();

        //Listeners
        pass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO Add points to the player
                Intent i = new Intent(ChallengeActivity.this, ChallengeActivity.class);
                startActivity(i);
            }
        });

        //Listeners
        fail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChallengeActivity.this, ChallengeActivity.class);
                startActivity(i);
            }
        });
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

                    selectionButtons.setVisibility(View.VISIBLE);
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
