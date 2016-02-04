package com.cyclone.loopback.model;

import com.cyclone.model.Music;
import com.cyclone.model.RadioContent;
import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 03/02/16.
 */
public class PlaylistData extends Model {
    public static int TYPE_RADIOCONTENT = 0x607d;
    public static int TYPE_MUSIC = 0x607e;
    String id;
    String playlistId;
    String contentId;
    int TypeContent;
    RadioContent radioContent;
    Music music;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public int getTypeContent() {
        return TypeContent;
    }

    public void setTypeContent(int typeContent) {
        TypeContent = typeContent;
    }

    public RadioContent getRadioContent() {
        return radioContent;
    }

    public void setRadioContent(RadioContent radioContent) {
        this.radioContent = radioContent;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
