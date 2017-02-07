package com.drunkmel.drunkmel.model;

import com.drunkmel.drunkmel.interfaces.DataModel;

/**
 * Created by german.moyano on 1/26/2017.
 */

public class QuestionModel implements DataModel {
    private String description;
    private String penalty;
    private String topic;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
