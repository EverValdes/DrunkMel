package com.drunkmel.drunkmel;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActivityMel extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.context = getApplicationContext();
        super.onCreate(savedInstanceState);
    }

    /*@Override
    public void onBackPressed() {
        //We do not want to perform anything when hard back button is pressed.
    }*/

}
