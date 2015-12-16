package com.cyclone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/12/2015.
 */
public class Data {

	private static List<MasterModel> data = new ArrayList<>();

	public static void reset(){
		data = new ArrayList<>();
	}

	public static void add(MasterModel o){
		data.add(o);
	}


	public static void remove(MasterModel o){
		data.remove(searchObject(o));
	}

	public static MasterModel searchObject(MasterModel model){
		for(MasterModel m : data){
			if(m.txtPrimary.equals(model.txtPrimary) && m.txtSecondary.equals(model.txtSecondary)){
				return m;
			}
		}
		return null;
	}

	public static List<MasterModel> getData(){
		return data;
	}
}
