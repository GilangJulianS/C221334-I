package com.cyclone.model;

/**
 * Created by gilang on 31/10/2015.
 */


public class Playlist {

	public String artist;
	public String title;
	public String duration;
	public String mp3;
	public String cover;

	public Playlist(String artist, String title, String duration, String cover, String mp3){
		this.artist = artist;
		this.title = title;
		this.duration = duration;
		this.mp3 = mp3;
		this.cover = cover;
	}
}
