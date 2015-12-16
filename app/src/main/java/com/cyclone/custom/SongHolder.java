package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Song;

/**
 * Created by gilang on 01/11/2015.
 */
public class SongHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnMenu;

	public SongHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Song) object);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Song) object);
	}*/

	public void bind(Song song){
		txtPrimary.setText(song.primary);
		txtSecondary.setText(song.secondary);
		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(activity, btnMenu);
				menu.inflate(R.menu.popup_default);
				menu.setOnMenuItemClickListener(new PopupMenuListener(activity));
				menu.show();
			}
		});
	}
}
