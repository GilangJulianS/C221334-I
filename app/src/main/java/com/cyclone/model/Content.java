package com.cyclone.model;

import android.support.annotation.Nullable;

import com.cyclone.R;

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

	public int favoritableType;
	public int contentType;
	public String imgUrl;
	public String tag;
	public String txtTertiary;
	public String music_url;
	public int targetType;
	public boolean isFavorited;
	public int position;
	public String id;

	/*public Content(String imgUrl, String tag, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, int contentType){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		favoritableType = NOT_FAVORITABLE;
		isFavorited = false;
		this.tag = tag;
		this.contentType = contentType;
	}

	public Content(String imgUrl, String tag, int favoritableType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, int contentType){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.favoritableType = favoritableType;
		this.tag = tag;
		isFavorited = false;
		this.contentType = contentType;
	}

	public Content(String imgUrl, String tag, int favoritableType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, boolean isFavorited, int contentType){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.favoritableType = favoritableType;
		this.isFavorited = isFavorited;
		this.tag = tag;
		this.contentType = contentType;
	}
	public Content(String imgUrl, String tag,int favoritableType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary,boolean isFavorited,  @Nullable String music_url, @Nullable int position, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.favoritableType = favoritableType;
		this.isFavorited = isFavorited;
		this.tag = tag;
		this.music_url = music_url;
		this.contentType = contentType;
		this.position = position;
		this.id = id;
	}*/
	public Content(String imgUrl, String tag, int favoritableType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary, boolean isFavorited, int contentType, String music_url, int position, String id){
		super(txtPrimary, txtSecondary);
		this.imgUrl = imgUrl;
		this.txtTertiary = txtTertiary;
		this.favoritableType = favoritableType;
		this.isFavorited = isFavorited;
		this.tag = tag;
		this.contentType = contentType;
		this.music_url = music_url;
		this.contentType = contentType;
		this.position = position;
		this.id = id;
	}

	public int getMenuResId(){
		switch (contentType){
			case TYPE_ALBUM: return R.menu.popup_album;
			case TYPE_ARTIST: return R.menu.popup_artist;
			case TYPE_MIX: return R.menu.popup_mix;
			case TYPE_PLAYLIST: return R.menu.popup_playlist;
			case TYPE_RADIO_CONTENT: return R.menu.popup_radio_content;
			case TYPE_TRACKS: return R.menu.popup_tracks;
			case TYPE_UPLOADED: return R.menu.popup_uploaded;
			default: return R.menu.global;
		}
	}
}
