package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Album;
import com.cyclone.model.SubcategoryItem;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryHolder extends UniversalHolder {

	public ImageView imgCover;
	public TextView txtPrimary;
	public TextView txtSecondary;

	public SubcategoryHolder(View v, Activity activity) {
		super(v, activity);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((SubcategoryItem) object);
	}

	public void bind(SubcategoryItem subcategoryItem){
		imgCover.setImageResource(R.drawable.wallpaper);
		txtPrimary.setText(subcategoryItem.primary);
		txtSecondary.setText(subcategoryItem.secondary);
	}
}
