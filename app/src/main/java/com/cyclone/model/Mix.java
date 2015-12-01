package com.cyclone.model;

/**
 * Created by gilang on 23/11/2015.
 */
public class Mix {

	public String imgUrl;
	public String text;
	public boolean isFavorite;

	public Mix(String imgUrl, String text){
		this.imgUrl = imgUrl;
		this.text = text;
		isFavorite = false;
	}

	public Mix(String imgUrl, String text, boolean isFavorite){
		this.imgUrl = imgUrl;
		this.text = text;
		this.isFavorite = isFavorite;
	}

}
