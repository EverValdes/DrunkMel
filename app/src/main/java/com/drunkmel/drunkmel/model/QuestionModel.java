package com.drunkmel.drunkmel.model;

import com.drunkmel.drunkmel.interfaces.DataModel;

/**
 * Created by everv on 1/26/2017.
 */

public class QuestionModel implements DataModel {
    private String title;
    private String description;

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
