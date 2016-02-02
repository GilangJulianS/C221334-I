package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 23/12/15.
 */
public class Playlist extends Model {
    String name;
    String caption;
    String Private;
    String playCount;
    String favoriteCount;
    String image;
    String contentCount;
    String id;
    String createdAt;
    String updatedAt;
    String accountId;

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

    public String getPrivate() {
        return Private;
    }

    public void setPrivate(String aPrivate) {
        Private = aPrivate;
    }

    public String getPlayCount() {
        return playCount;
    }

    public void setPlayCount(String playCount) {
        this.playCount = playCount;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContentCount() {
        return contentCount;
    }

    public void setContentCount(String contentCount) {
        this.contentCount = contentCount;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
