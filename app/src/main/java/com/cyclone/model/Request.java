package com.cyclone.model;

/**
 * Created by gilang on 07/11/2015.
 */
public class Request {

	public String imgUrl;
	public int socmedType;
	public String username;
	public String content;
	public String time;

	public Request(String imgUrl, int socmedType, String username, String content, String time){
		this.imgUrl = imgUrl;
		this.socmedType = socmedType;
		this.username = username;
		this.content = content;
		this.time = time;
	}
}
