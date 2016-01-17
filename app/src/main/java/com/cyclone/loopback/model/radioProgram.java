package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 08/01/16.
 */
public class radioProgram extends Model {
    String name;
    String description;
    String logo;
    String started_at;
    String finished_at;
    String id;
    String radioId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStarted_at() {
        return started_at;
    }

    public void setStarted_at(String started_at) {
        this.started_at = started_at;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRadioId() {
        return radioId;
    }

    public void setRadioId(String radioId) {
        this.radioId = radioId;
    }
}
