package com.cyclone.model;

import android.support.annotation.Nullable;

import com.cyclone.R;

/**
 * Created by gilang on 21/11/2015.
 */
public class Content {

	public String imgUrl;
	public String txtPrimary;
	public String txtSecondary;
	public String txtTertiary;
	public int imgResource = R.drawable.no_image_available;

	public Content(String imgUrl, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		this.imgUrl = imgUrl;
		this.txtPrimary = txtPrimary;
		this.txtSecondary = txtSecondary;
		this.txtTertiary = txtTertiary;
	}
	public Content(int imgResource, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		this.imgResource = imgResource;
		this.txtPrimary = txtPrimary;
		this.txtSecondary = txtSecondary;
		this.txtTertiary = txtTertiary;
	}
}
