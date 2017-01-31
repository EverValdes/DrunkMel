package com.drunkmel.drunkmel.model;

import com.drunkmel.drunkmel.interfaces.DataModel;

import java.io.Serializable;

/**
 * Created by everv on 1/26/2017.
 */

public class ChallengeModel implements DataModel {
    protected String title;
    protected String description;

    public ChallengeModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public ChallengeModel(){};

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
