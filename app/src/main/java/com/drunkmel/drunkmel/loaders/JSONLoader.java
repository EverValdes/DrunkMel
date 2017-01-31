package com.drunkmel.drunkmel.loaders;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.helpers.ResourceGetter;
import com.drunkmel.drunkmel.interfaces.DataModel;
import com.drunkmel.drunkmel.interfaces.LoaderResource;
import com.drunkmel.drunkmel.model.ChallengeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

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
}
