package com.cyclone.model;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryItem {
	public String imgUrl;
	public String primary;
	public String secondary;

	public SubcategoryItem(String imgUrl, String primaryInfo, String secondaryInfo){
		this.imgUrl = imgUrl;
		primary = primaryInfo;
		secondary = secondaryInfo;
	}
}
