package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gilang on 01/11/2015.
 */
public abstract class UniversalHolder extends RecyclerView.ViewHolder {

	protected Activity activity;
	protected UniversalAdapter adapter;
	protected boolean isSelected;


	public UniversalHolder(View itemView, Activity activity, UniversalAdapter adapter) {
		super(itemView);
		this.activity = activity;
		this.adapter = adapter;
	}

	public abstract void bind(Object object, Activity activity, int position);

	public void setSelected(boolean selected){
		isSelected = selected;
	}
}
