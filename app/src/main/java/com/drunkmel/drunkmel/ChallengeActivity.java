package com.drunkmel.drunkmel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.drunkmel.drunkmel.interfaces.DataModel;
import com.drunkmel.drunkmel.loaders.JSONLoader;
import com.drunkmel.drunkmel.model.ChallengeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ChallengeActivity extends ActivityMel {
    //Challenges that need to be recorded into singleton/database
    private ArrayList<ChallengeModel> challenges = new ArrayList<ChallengeModel>();

    //Variables
    Context context;
    ArrayList<String> players = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        JSONLoader jsonLoader = new JSONLoader(this);
        //Read json file "challenges"
        JSONArray jsonArray = jsonLoader.readData("challenges");
        createDataModel(jsonArray);

        //Get all keys from the Shared Preferences
        context = getApplicationContext();
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
        //TODO
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
                    }
                }
                this.challenges.add(challengeModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
