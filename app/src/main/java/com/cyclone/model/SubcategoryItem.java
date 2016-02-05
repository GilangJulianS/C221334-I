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
	public int contentType;
	public int posisi;
	public String id;
	public String category;
	public String url;


	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo){
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		type = TYPE_NORMAL;
	}

	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo, int type){
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		this.type = type;
	}

	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo, int type, String category, String url, int posisi, int contentType, String id) {
		super(primaryInfo, secondaryInfo);
		this.imgUrl = imgUrl;
		this.type = type;
		this.posisi = posisi;
		this.id = id;
		this.category = category;
		this.url = url;
		this.contentType = contentType;
	}

	public int getMenuResId() {
		return Content.getMenuResId(contentType);
	}
}
