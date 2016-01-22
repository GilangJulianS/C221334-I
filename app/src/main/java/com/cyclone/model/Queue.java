package com.cyclone.model;

/**
 * Created by gilang on 31/10/2015.
 */
public class Queue {

	private static int counter = 0;
	public int id;
	public String artist;
	public String title;
	public String duration;
	public int posisi;

	public Queue(String artist, String title, String duration, int posisi){
		id = counter;
		counter++;
		this.artist = artist;
		this.title = title;
		this.duration = duration;
		this.posisi = posisi;
	}

	public static void reset(){
		counter = 0;
	}
}
