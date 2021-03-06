package com.drunkmel.drunkmel.loaders;

import android.content.Context;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.helpers.ResourceGetter;
import com.drunkmel.drunkmel.interfaces.DataModel;
import com.drunkmel.drunkmel.interfaces.LoaderResource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by everv on 1/26/2017.
 */

public class JSONLoader implements LoaderResource {
    private Context context;

    public JSONLoader(Context context){
        this.context = context;
    }

    @Override
    public JSONArray readData(String dataModelType) {
        ResourceGetter resourceGetter = ResourceGetter.getInstance(context);

        int jsonResourceID = resourceGetter.getResourceId(dataModelType, "raw", context.getPackageName());
        String jsonString = readMockJson(jsonResourceID);
        return formatJsonString(jsonString, dataModelType);

    }

    @Override
    public void whiteData(DataModel dataModel) {

    }

    private String readMockJson (int resourceID) {
        String jsonString;
        try {
            InputStream is = context.getResources().openRawResource(resourceID);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private JSONArray formatJsonString(String jsonString, String dataModelType ) {
        JSONArray jsonElementArray = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonElementArray = jsonObject.getJSONArray(dataModelType);
            return jsonElementArray;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonElementArray;
    }

    public String loadJSONFromRaw(Context context) {
        String jsonString;
        try {
            InputStream is = context.getResources().openRawResource(getRandomQuestion());
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return jsonString;
    }

    private int getRandomQuestion(){
        Random r = new Random();
        int jsonId;
        int questionId = r.nextInt(10 - 1) + 1;

        switch (questionId) {
            case 1:
                jsonId = R.raw.question1;
                break;
            case 2:
                jsonId = R.raw.question2;
                break;
            case 3:
                jsonId = R.raw.question3;
                break;
            case 4:
                jsonId = R.raw.question4;
                break;
            case 5:
                jsonId = R.raw.question5;
                break;
            case 6:
                jsonId = R.raw.question6;
                break;
            case 7:
                jsonId = R.raw.question7;
                break;
            case 8:
                jsonId = R.raw.question8;
                break;
            case 9:
                jsonId = R.raw.question9;
                break;
            case 10:
                jsonId = R.raw.question10;
                break;
            default:
                jsonId = R.raw.question1;
        }
        return jsonId;
    }
}
