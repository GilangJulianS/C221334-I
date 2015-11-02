package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gilang on 01/11/2015.
 */
public abstract class UniversalHolder extends RecyclerView.ViewHolder {

	public UniversalHolder(View itemView) {
		super(itemView);
	}

	public abstract void bind(Object object, Activity activity, int position);
}
