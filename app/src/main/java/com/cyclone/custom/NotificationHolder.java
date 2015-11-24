package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Notification;

/**
 * Created by gilang on 01/11/2015.
 */
public class NotificationHolder extends UniversalHolder{

	public ImageView imgUser;
	public TextView txtInfo;
	public TextView txtTime;

	public NotificationHolder(View v, Activity activity) {
		super(v, activity);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		txtInfo = (TextView) v.findViewById(R.id.txt_info);
		txtTime = (TextView) v.findViewById(R.id.txt_time);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Notification)object);
	}

	public void bind(Notification notif){
		imgUser.setImageResource(R.drawable.background_login);
		txtInfo.setText(notif.info);
		txtTime.setText(notif.time);
	}
}
