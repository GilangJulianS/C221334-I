package com.cyclone.model;

import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramPager {

	public List<String> imgUrls;
	public int defaultIdx;
	public List<Integer> imgInt;

	public ProgramPager(List<String> imgurls, int defaultIdx){
		this.imgUrls = imgurls;
		this.defaultIdx = defaultIdx;
	}

	public ProgramPager(List<Integer> imgInt, int defaultIdx, Boolean isInt){
		this.imgInt = imgInt;
		this.defaultIdx = defaultIdx;
	}

}
