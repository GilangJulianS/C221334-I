package com.cyclone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solusi247 on 02/02/16.
 */
public class AddPlaylist {
    private String title;
    private String caption;
    private List<String> list = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
