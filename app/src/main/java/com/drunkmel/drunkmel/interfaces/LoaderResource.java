package com.drunkmel.drunkmel.interfaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public interface LoaderResource {
    public DataModel[] readData();
    public void whiteData(DataModel datamodel);
}
