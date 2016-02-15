package com.cyclone.model;

/**
 * Created by gilang on 01/11/2015.
 */
public class Section {

	public static final int TYPE_TRANSPARENT = 100;
	public static final int TYPE_NORMAL = 101;
	public static final int TYPE_NONE = 102;
	public static final String CATEGORY_FAFORITE = "com.cyclone.favorite_category";
	public String name;
	public String category;
	public int sectionType;
	public int targetFragment;
	public int typeFavorite;

	public Section(String name, String category){
		this.name = name;
		this.category = category;
		sectionType = TYPE_NORMAL;
		targetFragment = -1;
	}

	public Section(String name, String category, int targetFragment){
		this.name = name;
		this.category = category;
		sectionType = TYPE_NORMAL;
		this.targetFragment = targetFragment;
	}

	public Section(String name, String category, int sectionType, int targetFragment){
		this.name = name;
		this.category = category;
		this.sectionType = sectionType;
		this.targetFragment = targetFragment;
	}

	public Section(String name, int typeFoforite, String category, int targetFragment) {
		this.name = name;
		this.typeFavorite = typeFoforite;
		this.category = category;
		sectionType = TYPE_NORMAL;
		this.targetFragment = targetFragment;
	}
}
