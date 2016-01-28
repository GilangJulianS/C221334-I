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
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.model.Content;

import java.util.ArrayList;

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
				String[] s = {"as","qw","fg","rt","hj"};
				ArrayList<String> l = new ArrayList<>();
				l.add("as");
				l.add("qw");
				l.add("fg");
				l.add("rt");
				l.add("jh");

				String parm;
				parm = "{";
				for(int j = 0; j < l.size(); j++){
					parm = parm + l.get(j);
					if(j < l.size()-1) parm = parm + ",";
					else parm = parm +"}";
				}

				System.out.println("list : "+ parm);
				return true;
			case R.id.menu_add_favorites:
				Toast.makeText(activity, "Add to Favorites Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_add_playlist:
				PopupMenu menu = new PopupMenu(activity, anchorView);
				menu.getMenu().add("New Playlist");
				/*for(Playlist p : PlaylistData.playlists){
					menu.getMenu().add(p.name);
				}*/
				for(int j = 0; j < UtilArrayData.PlaylistAccount.size(); j++){
					menu.getMenu().add(0, j, j, UtilArrayData.PlaylistAccount.get(j).getName());
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
