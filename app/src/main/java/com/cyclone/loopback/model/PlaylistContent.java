package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 15/01/16.
 */
public class PlaylistContent extends Model {
    String name;
    String caption;
   // String private;
    String id;
    String createdAt;
    String accountId;

    String musics;
    String uploads;
    String radioContents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMusics() {
        return musics;
    }

    public void setMusics(String musics) {
        this.musics = musics;
    }

    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public String getRadioContents() {
        return radioContents;
    }

    public void setRadioContents(String radioContents) {
        this.radioContents = radioContents;
    }
}
