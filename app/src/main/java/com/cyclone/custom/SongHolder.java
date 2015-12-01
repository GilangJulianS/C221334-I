package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Song;

/**
 * Created by gilang on 01/11/2015.
 */
public class SongHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;

	public SongHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Song) object);
	}

	public void bind(Song song){
		txtPrimary.setText(song.primary);
		txtSecondary.setText(song.secondary);
	}
}
