package com.drunkmel.drunkmel.interfaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;

import java.util.ArrayList;

public interface LoaderResource {
    public JSONArray readData(String dataModelType);
    public void whiteData(DataModel datamodel);
}
