package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Song;

/**
 * Created by gilang on 01/11/2015.
 */
public class SongHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;

	public SongHolder(View v) {
		super(v);
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
