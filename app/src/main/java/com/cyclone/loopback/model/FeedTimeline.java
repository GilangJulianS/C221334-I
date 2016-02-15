package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

import java.util.Map;

/**
 * Created by solusi247 on 22/12/15.
 */
public class FeedTimeline extends Model{
    int type;
    String created_at;
   // String updated_at;
    String id;
    String accountId;
    String feedContentId;
    int likesCount;
    int commentsCount;
    boolean liked;
//    Map<String, String> statistics;
    Map<String, String> typePost;
    Map<String, String> owner;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getFeedContentId() {
        return feedContentId;
    }

    public void setFeedContentId(String feedContentId) {
        this.feedContentId = feedContentId;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public Map<String, String> getTypePost() {
        return typePost;
    }

    public void setTypePost(Map<String, String> typePost) {
        this.typePost = typePost;
    }

    public Map<String, String> getOwner() {
        return owner;
    }

    public void setOwner(Map<String, String> owner) {
        this.owner = owner;
    }
}
