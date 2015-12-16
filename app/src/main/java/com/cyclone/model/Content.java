package com.cyclone.model;

import android.support.annotation.Nullable;

/**
 * Created by gilang on 21/11/2015.
 */
public class Content extends MasterModel{

	public static final int TYPE_FAVORITABLE = 100;
	public static final int TYPE_ELSE = 101;
	public String imgUrl;
	public String tag;
	public String txtTertiary;
	public String music_url;
	public int targetType;
	public boolean isFavorited;

	public Content(String imgUrl, String tag, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		targetType = TYPE_ELSE;
		isFavorited = false;
		this.tag = tag;
	}

	public Content(String imgUrl, String tag, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.tag = tag;
		isFavorited = false;
	}

	public Content(String imgUrl, String tag, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, boolean isFavorited){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.isFavorited = isFavorited;
		this.tag = tag;
	}

	public Content(String imgUrl, String tag, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, @Nullable String music_url){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.tag = tag;
		this.music_url = music_url;
		isFavorited = false;
	}
}
