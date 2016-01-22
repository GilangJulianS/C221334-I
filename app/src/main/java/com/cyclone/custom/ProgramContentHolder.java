package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.ProgramContent;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramContentHolder extends UniversalHolder {

	public TextView txtType;
	public TextView txtTime;
	public TextView txtContent;

	public ProgramContentHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtType = (TextView) v.findViewById(R.id.txt_type);
		txtTime = (TextView) v.findViewById(R.id.txt_time);
		txtContent = (TextView) v.findViewById(R.id.txt_content);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((ProgramContent)object);
	}

	public void bind(ProgramContent pc){
		if(pc.type == ProgramContent.TYPE_MUSIC){
			txtType.setText("music");
			txtType.setBackgroundResource(R.drawable.txt_background_orange);
		}else if(pc.type == ProgramContent.TYPE_COMMERCIAL){
			txtType.setText("commercial");
			txtType.setBackgroundResource(R.drawable.txt_background_green);
		}else if(pc.type == ProgramContent.TYPE_SOUND){
			txtType.setText("sound");
			txtType.setBackgroundResource(R.drawable.txt_background_red);
		}
		txtTime.setText(pc.time);
		txtContent.setText(pc.content);
	}
}
