package com.cyclone.model;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramContent {

	public static int TYPE_MUSIC = 100;
	public static int TYPE_COMMERCIAL = 101;
	public static int TYPE_SOUND = 102;

	public int type;
	public String time;
	public String content;

	public ProgramContent(int type, String time, String content){
		this.type = type;
		this.time = time;
		this.content = content;
	}
}
