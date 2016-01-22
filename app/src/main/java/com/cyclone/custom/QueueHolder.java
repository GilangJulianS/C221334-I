package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Queue;

/**
 * Created by gilang on 01/11/2015.
 */
public class QueueHolder extends UniversalHolder{

	public TextView txtNumber;
	public TextView txtTitle;
	public TextView txtArtist;
	public TextView txtDuration;

	public QueueHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtNumber = (TextView) v.findViewById(R.id.txt_number);
		txtTitle = (TextView) v.findViewById(R.id.txt_song_title);
		txtArtist = (TextView) v.findViewById(R.id.txt_song_artist);
		txtDuration = (TextView) v.findViewById(R.id.txt_duration);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Queue)object);
	}

	public void bind(Queue queue){
		txtNumber.setText(String.valueOf(queue.id));
		txtTitle.setText(queue.artist + " - " + queue.title);
		txtArtist.setText(queue.artist);
		txtDuration.setText(queue.duration);
	}
}
