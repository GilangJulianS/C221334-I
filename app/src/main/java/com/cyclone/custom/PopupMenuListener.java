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
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.repository.FavotireRepository;
import com.cyclone.model.Content;
import com.cyclone.model.Post;
import com.cyclone.model.SubcategoryItem;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import java.util.ArrayList;

/**
 * Created by gilang on 12/12/2015.
 */
public class PopupMenuListener implements PopupMenu.OnMenuItemClickListener {

	public static int TYPE_CONTENT = 0x3c4d9;
	public static int TYPE_ALBUM = 0x3c4da;
	public static int TYPE_CLUB_FEED = 0x3c4db;
	public static int TYPE_SONG = 0x3c4dc;
	public static int TYPE_SUB_CATEGORY = 0x3c4dd;

	private Activity activity;
	private View anchorView;
	private Object object;
	private int TypeData;
	private String title;
	private String dataId;
	private String typeId;
	private int typeContent;

	public PopupMenuListener(Activity activity, Object object, int TypeData, View anchorView) {
		this.activity = activity;
		//this.content = content;
		this.anchorView = anchorView;
		this.object = object;
		if (TypeData == TYPE_CONTENT) {
			Content content = (Content) object;
			title = content.txtPrimary;
			System.out.println("title : " + title);
			dataId = content.id;
			typeContent = content.contentType;
			typeId = content.id;
			System.out.println("data id : " + dataId);
		} else if (TypeData == TYPE_SUB_CATEGORY) {
			SubcategoryItem subcategoryItem = (SubcategoryItem) object;
			title = subcategoryItem.txtPrimary;
			dataId = subcategoryItem.id;
		} else if (TypeData == TYPE_CLUB_FEED) {
			Post post = (Post) object;
			title = post.postTitle;
			dataId = post.FeedId;
			typeContent = post.playlistType;
			typeId = post.TypeId;
		}

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
				//Toast.makeText(activity, "Add to Favorites Clicked", Toast.LENGTH_SHORT).show();
				RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
				if (typeContent == Content.TYPE_PLAYLIST) {
					FavotireRepository.newInstance(FavotireRepository.PLAYLIST_CLASS);
					FavotireRepository favotireRepository = restAdapter.createRepository(FavotireRepository.class);
					favotireRepository.addToFavorite(typeId, new Adapter.Callback() {
						@Override
						public void onSuccess(String response) {

						}

						@Override
						public void onError(Throwable t) {

						}
					});
				}
				if (typeContent == Content.TYPE_TRACKS) {
					FavotireRepository.newInstance(FavotireRepository.MUSIC_CLASS);
					FavotireRepository favotireRepository = restAdapter.createRepository(FavotireRepository.class);
					favotireRepository.addToFavorite(typeId, new Adapter.Callback() {
						@Override
						public void onSuccess(String response) {

						}

						@Override
						public void onError(Throwable t) {

						}
					});
				}
				if (typeContent == Content.TYPE_RADIO_CONTENT) {
					FavotireRepository.newInstance(FavotireRepository.RADIOCONTENT_CLASS);
					FavotireRepository favotireRepository = restAdapter.createRepository(FavotireRepository.class);
					favotireRepository.addToFavorite(typeId, new Adapter.Callback() {
						@Override
						public void onSuccess(String response) {

						}

						@Override
						public void onError(Throwable t) {

						}
					});
				}
				return true;
			case R.id.menu_add_playlist:
				PopupMenu menu = new PopupMenu(activity, anchorView);
				menu.getMenu().add("New Playlist");
				/*for(Playlist p : PlaylistData.playlists){
					menu.getMenu().add(p.name);
				}*/
				for(int j = 0; j < UtilArrayData.playlistAccount.size(); j++){
					menu.getMenu().add(0, j, j, UtilArrayData.playlistAccount.get(j).getName());
				}

				menu.setOnMenuItemClickListener(new PopupPlaylistListener(activity, dataId, anchorView));
				menu.show();
				return true;
			case R.id.menu_album_page:
				i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", title);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_ALBUM);
				activity.startActivity(i);
				return true;
			case R.id.menu_artist_page:
				i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", title);
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
