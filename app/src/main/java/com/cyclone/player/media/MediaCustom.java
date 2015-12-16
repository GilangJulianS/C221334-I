package com.cyclone.player.media;

import android.graphics.Bitmap;
import android.net.Uri;

import com.cyclone.player.PlaybackService;

/**
 * Created by solusi247 on 13/12/15.
 */
public class MediaCustom {
    Uri uri = null;
    long time = 0;
    long length = 0;
    int type = PlaybackService.TYPE_AUDIO;
    Bitmap picture = null;
    String title = null;
    String artist = null;
    String genre = null;
    String album = null;
    String albumArtist = null;
    int width = 0;
    int height = 0;
    String artworkURL = null;
    int audio = 0;

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getArtworkURL() {
        return artworkURL;
    }

    public void setArtworkURL(String artworkURL) {
        this.artworkURL = artworkURL;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public int getSpu() {
        return spu;
    }

    public void setSpu(int spu) {
        this.spu = spu;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }

    public int getDiscNumber() {
        return discNumber;
    }

    public void setDiscNumber(int discNumber) {
        this.discNumber = discNumber;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    int spu;
    int trackNumber;
    int discNumber;
    long lastModified;
}
