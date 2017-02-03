package com.drunkmel.drunkmel.model;

import com.drunkmel.drunkmel.interfaces.DataModel;

/**
 * Created by JuanManuel on 2/2/2017.
 */

public class PenaltyModel implements DataModel {
    protected String title;
    protected String description;

    public PenaltyModel(String title, String description) {

        this.title = title;
        this.description = description;
    }

    public PenaltyModel(){
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}