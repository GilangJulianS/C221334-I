package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Content;
import com.cyclone.model.Playlist;
import com.cyclone.model.PlaylistData;

/**
 * Created by gilang on 12/12/2015.
 */
public class PopupMenuListener implements PopupMenu.OnMenuItemClickListener {

	private Activity activity;
	private Content content;
	private View anchorView;

	public PopupMenuListener(Activity activity, Content content, View anchorView){
		this.activity = activity;
		this.content = content;
		this.anchorView = anchorView;
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
			case R.id.menu_add_queue:
				Toast.makeText(activity, "Add to Queue Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_add_favorites:
				Toast.makeText(activity, "Add to Favorites Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_add_playlist:
				PopupMenu menu = new PopupMenu(activity, anchorView);
				menu.getMenu().add("New Playlist");
				for(Playlist p : PlaylistData.playlists){
					menu.getMenu().add(p.name);
				}
				menu.setOnMenuItemClickListener(new PopupPlaylistListener(activity, content, anchorView));
				menu.show();
				return true;
			case R.id.menu_album_page:
				i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", content.txtPrimary);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_ALBUM);
				activity.startActivity(i);
				return true;
			case R.id.menu_artist_page:
				i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", content.txtPrimary);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_ARTIST);
				activity.startActivity(i);
				return true;
			case R.id.menu_share:
				Toast.makeText(activity, "Share Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_play_mix:
				i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", "Player");
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_PLAYER);
				activity.startActivity(i);
				return true;
			case R.id.menu_take_offline:
				Toast.makeText(activity, "Play Mix Clicked", Toast.LENGTH_SHORT).show();
				return true;
			default: return false;
		}
	}
}
