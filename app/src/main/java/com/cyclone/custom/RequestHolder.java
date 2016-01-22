package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Request;

/**
 * Created by gilang on 07/11/2015.
 */
public class RequestHolder extends UniversalHolder {

	public ImageView imgUser;
	public ImageView imgSocmed;
	public TextView txtUsername;
	public TextView txtContent;
	public TextView txtTime;

	public RequestHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		imgSocmed = (ImageView) v.findViewById(R.id.img_socmed);
		txtUsername = (TextView) v.findViewById(R.id.txt_username);
		txtContent = (TextView) v.findViewById(R.id.txt_content);
		txtTime = (TextView) v.findViewById(R.id.txt_time);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Request)object);
	}

	public void bind(Request request){
		imgUser.setImageResource(R.drawable.background_login);
		imgSocmed.setImageResource(R.drawable.ic_language_black_24dp);
		txtUsername.setText(request.username);
		txtContent.setText(request.content);
		txtTime.setText(request.time);
	}

}
