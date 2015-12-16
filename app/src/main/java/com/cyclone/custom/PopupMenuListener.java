package com.cyclone.custom;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.R;

/**
 * Created by gilang on 12/12/2015.
 */
public class PopupMenuListener implements PopupMenu.OnMenuItemClickListener {

	private Activity activity;

	public PopupMenuListener(Activity activity){
		this.activity = activity;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_add_favorites:
				Toast.makeText(activity, "Add to Favorites Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_add_playlist:
				Toast.makeText(activity, "Add to Playlist Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_album_page:
				Toast.makeText(activity, "Album Page Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_share:
				Toast.makeText(activity, "Share Clicked", Toast.LENGTH_SHORT).show();
				return true;
			default: return false;
		}
	}
}
