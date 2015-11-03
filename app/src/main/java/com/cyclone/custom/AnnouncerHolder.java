package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Announcer;

/**
 * Created by gilang on 01/11/2015.
 */
public class AnnouncerHolder extends UniversalHolder{

	public TextView txtName;
	public ImageView image;

	public AnnouncerHolder(View v){
		super(v);
		txtName = (TextView) v.findViewById(R.id.txt_announcer_name);
		image = (ImageView) v.findViewById(R.id.img_announcer);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Announcer)object);
	}

	public void bind(Announcer announcer){
		image.setImageResource(Integer.valueOf(announcer.imgUrl));
		txtName.setText(announcer.name);
	}

}
