package com.cyclone.model;

import android.support.annotation.Nullable;

/**
 * Created by gilang on 21/11/2015.
 */
public class Content extends MasterModel{

	public static final int FAVORITABLE = 100;
	public static final int NOT_FAVORITABLE = 101;
	public static final int TYPE_TRACKS = 102;
	public static final int TYPE_ARTIST = 103;
	public static final int TYPE_ALBUM = 104;
	public static final int TYPE_PLAYLIST = 105;
	public static final int TYPE_MIX = 106;
	public static final int TYPE_RADIO_CONTENT = 107;
	public static final int TYPE_UPLOADED = 108;
	public static final int TYPE_ADS = 109;
	public static final int TYPE_FAVORITABLE = 100;
	public static final int TYPE_ELSE = 101;
	public String imgUrl;
	public String tag;
	public String txtTertiary;
	public String music_url;
	public int targetType;
	public boolean isFavorited;
	public int position;
	public String id;

	public Content(String imgUrl, String tag, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		targetType = TYPE_ELSE;
		isFavorited = false;
		this.tag = tag;
		this.id = id;
	}

	public Content(String imgUrl, String tag, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.tag = tag;
		isFavorited = false;
		this.id = id;
	}

	public Content(String imgUrl, String tag, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, boolean isFavorited, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.isFavorited = isFavorited;
		this.tag = tag;
		this.id = id;
	}

	public Content(String imgUrl, String tag,int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, @Nullable String music_url, @Nullable int position, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.targetType = targetType;
		this.tag = tag;
		this.music_url = music_url;
		isFavorited = false;
		this.position = position;
		this.id = id;
	}
}
