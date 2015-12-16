package com.cyclone.model;

/**
 * Created by gilang on 23/11/2015.
 */
public class Mix {

	public String imgUrl;
	public String text;
	public String tag;
	public boolean isFavorite;

	public Mix(String imgUrl, String text, String tag){
		this.imgUrl = imgUrl;
		this.text = text;
		this.tag = tag;
		isFavorite = false;
	}

	public Mix(String imgUrl, String text, String tag, boolean isFavorite){
		this.imgUrl = imgUrl;
		this.text = text;
		this.tag = tag;
		this.isFavorite = isFavorite;
	}

}
