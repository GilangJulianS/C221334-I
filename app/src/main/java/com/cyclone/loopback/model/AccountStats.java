package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 15/02/16.
 */
public class AccountStats extends Model {
    String showlists;
    String uploads;
    String followers;
    String followings;

    public String getShowlists() {
        return showlists;
    }

    public void setShowlists(String showlists) {
        this.showlists = showlists;
    }

    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowings() {
        return followings;
    }

    public void setFollowings(String followings) {
        this.followings = followings;
    }
}
