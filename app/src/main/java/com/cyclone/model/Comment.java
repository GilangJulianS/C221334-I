package com.cyclone.model;

/**
 * Created by gilang on 23/11/2015.
 */
public class Comment {

	public String imgUrl;
	public String username;
	public String comment;
	public String time;
	public float rating;

	public Comment(String imgUrl, String username, String comment, String time){
		this.imgUrl = imgUrl;
		this.username = username;
		this.comment = comment;
		this.time = time;
		this.rating = 0;
	}

	public Comment(String imgUrl, String username, String comment, String time, float rating){
		this.imgUrl = imgUrl;
		this.username = username;
		this.comment = comment;
		this.time = time;
		this.rating = rating;
	}
}
