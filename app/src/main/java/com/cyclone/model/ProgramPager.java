package com.cyclone.model;

import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramPager {

	public List<Program> programs;
	public int defaultIdx;

	public ProgramPager(List<Program> programs, int defaultIdx){
		this.programs = programs;
		this.defaultIdx = defaultIdx;
	}
}
