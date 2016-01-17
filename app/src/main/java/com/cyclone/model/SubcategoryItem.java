package com.cyclone.model;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryItem extends MasterModel{

	public static final int TYPE_DELETABLE = 100;
	public static final int TYPE_NORMAL = 101;
	public static final int TYPE_SELECTABLE = 102;
	public String imgUrl;
	public int type;
	public String music_url;
	public String category;
	public String id;


	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo, String id){
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		type = TYPE_NORMAL;
		this.id = id;
	}
	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo, String music_url, String category, String id){
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		this.music_url = music_url;
		this.category = category;
		type = TYPE_NORMAL;
		this.id = id;
	}

	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo, int type, String id){
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		this.type = type;
		this.id = id;
	}
}
