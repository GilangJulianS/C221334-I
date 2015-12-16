package com.cyclone.model;

import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramPager {

	public List<String> imgUrls;
	public int defaultIdx;

	public ProgramPager(List<String> imgurls, int defaultIdx){
		this.imgUrls = imgurls;
		this.defaultIdx = defaultIdx;
	}
}
