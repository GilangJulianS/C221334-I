package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Playlist;

/**
 * Created by gilang on 01/11/2015.
 */
public class PlaylistHolder extends UniversalHolder{

	public TextView txtTitle;
	public TextView txtArtist;
	public TextView txtDuration;

	public PlaylistHolder(View v) {
		super(v);
		txtTitle = (TextView) v.findViewById(R.id.txt_song_title);
		txtArtist = (TextView) v.findViewById(R.id.txt_song_artist);
		txtDuration = (TextView) v.findViewById(R.id.txt_duration);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Playlist)object);
	}

	public void bind(Playlist playlist){
		txtTitle.setText(playlist.artist + " - " + playlist.title);
		txtArtist.setText(playlist.artist);
		txtDuration.setText(playlist.duration);
	}
}
