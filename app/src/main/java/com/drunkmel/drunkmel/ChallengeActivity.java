package com.drunkmel.drunkmel;

import android.os.Bundle;

import com.drunkmel.drunkmel.interfaces.DataModel;
import com.drunkmel.drunkmel.loaders.JSONLoader;
import com.drunkmel.drunkmel.model.ChallengeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChallengeActivity extends ActivityMel {
    //Challenges that need to be recorded into singleton/database
    private ArrayList<ChallengeModel> challenges = new ArrayList<ChallengeModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        JSONLoader jsonLoader = new JSONLoader(this);
        //Read json file "challenges"
        JSONArray jsonArray = jsonLoader.readData("challenges");
        createDataModel(jsonArray);
    }

    private void loadUI() {

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
