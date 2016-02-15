package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 15/02/16.
 */
public class Favorite extends Model {
    public static final int TYPE_PLAYLIST = 247001;
    public static final int TYPE_MUSIC = 247002;
    public static final int TYPE_RADIOCONTENT = 247003;
    public static final int TYPE_ALBUM = 247004;
    public static final int TYPE_ARTIST = 247005;
    public static final int TYPE_MIX = 247006;

    String id;
    String accountId;
    String contentId;
    String createdAt;
    String updatedAt;
    int type;
    Playlist playlist;
    Music music;
    RadioContent radioContent;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public RadioContent getRadioContent() {
        return radioContent;
    }

    public void setRadioContent(RadioContent radioContent) {
        this.radioContent = radioContent;
    }
}
