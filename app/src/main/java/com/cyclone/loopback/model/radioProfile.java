package com.cyclone.loopback.model;

import com.strongloop.android.loopback.Model;

/**
 * Created by solusi247 on 07/01/16.
 */
public class radioProfile extends Model {
    String logo;
    String name;
    String tagline;
    String region;
    String about;
    String phone_office;
    String phone_live;
    String address;
    String email;
    String website;
    String facebook_page;
    String twitter_page;
    String stream;
    String id;
    String createdAt;
    String updatedAt;
    String radioAccId;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone_office() {
        return phone_office;
    }

    public void setPhone_office(String phone_office) {
        this.phone_office = phone_office;
    }

    public String getPhone_live() {
        return phone_live;
    }

    public void setPhone_live(String phone_live) {
        this.phone_live = phone_live;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFacebook_page() {
        return facebook_page;
    }

    public void setFacebook_page(String facebook_page) {
        this.facebook_page = facebook_page;
    }

    public String getTwitter_page() {
        return twitter_page;
    }

    public void setTwitter_page(String twitter_page) {
        this.twitter_page = twitter_page;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
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

    public String getRadioAccId() {
        return radioAccId;
    }

    public void setRadioAccId(String radioAccId) {
        this.radioAccId = radioAccId;
    }
}
