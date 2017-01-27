package com.drunkmel.drunkmel.loaders;

import android.content.Context;

import com.drunkmel.drunkmel.R;
import com.drunkmel.drunkmel.interfaces.DataModel;
import com.drunkmel.drunkmel.interfaces.LoaderResource;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by everv on 1/26/2017.
 */

public class JSONLoader implements LoaderResource {
    private Context context;

    public JSONLoader(Context context) {
        this.context = context;
    }

    @Override
    public DataModel[] readData() {
        DataModel[] jsonData = null;
        String json = readMockJsonFile(context);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*JSONObject uniObject = jsonObject.getJSONObject("university");
        String  title = jsonObject.getJsonString("title");
        String description = jsonObject.getJsonString("description");*/

        Iterator<String> iter = jsonObject.keys();
        while (iter.hasNext()) {
            String key = iter.next();
            try {
                DataModel jsonDataModel = null;
                Object value = jsonObject.get(key);
                String  title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                jsonDataModel.setTitle(title);
                jsonDataModel.setDescription(description);
            } catch (JSONException e) {
                // Something went wrong!
            }
        }

        return null;
    }

    @Override
    public void whiteData(DataModel datamodel) {

    }

    //Read json file and return it as string
    private String readMockJsonFile(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.challenge);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void formatData(String stringJson) {

    }
}
