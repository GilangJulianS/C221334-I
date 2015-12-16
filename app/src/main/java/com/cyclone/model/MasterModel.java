package com.cyclone.model;

import android.support.annotation.Nullable;

/**
 * Created by gilang on 10/12/2015.
 */
public class MasterModel {

	public String txtPrimary;
	public String txtSecondary;

	public MasterModel(@Nullable String primary, @Nullable String secondary){
		this.txtPrimary = primary;
		this.txtSecondary = secondary;
	}
}
