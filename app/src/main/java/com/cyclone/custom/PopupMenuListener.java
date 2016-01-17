package com.cyclone.custom;

import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.loopback.repository.PlaylistAccountRepository;
import com.cyclone.model.Content;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by gilang on 12/12/2015.
 */
public class PopupMenuListener implements PopupMenu.OnMenuItemClickListener {

	private Activity activity;
	private Content content;
	private View anchorView;
	String contentId;

	public PopupMenuListener(Activity activity, Content content, View anchorView, String contentID){
		this.activity = activity;
		this.content = content;
		this.anchorView = anchorView;
		this.contentId = contentID;

		final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
		PlaylistAccountRepository playlistAccountRepository = restAdapter.createRepository(PlaylistAccountRepository.class);

		playlistAccountRepository.get(UtilUser.currentUser.getId(), new Adapter.Callback() {
			@Override
			public void onSuccess(String response) {
				try {
					JSONArray jsonArray = new JSONArray(response);
					if(PlaylistData.playlists.size()>0)
						PlaylistData.playlists.clear();
					for (int i = 0; i < jsonArray.length(); i++){
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						Playlist playlist = new Playlist(jsonObject.getString("name"), jsonObject.getString("id"));
						PlaylistData.playlists.add(playlist);
						System.out.println(""+jsonObject.getString("name")+" "+playlist.id);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onError(Throwable t) {

			}
		});

	}


	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_add_favorites:
				Toast.makeText(activity, "Add to Favorites Clicked", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_add_playlist:
				PopupMenu menu = new PopupMenu(activity, anchorView);
				menu.getMenu().add("New Playlist");
				for(Playlist p : PlaylistData.playlists){
					menu.getMenu().add(p.name);
				}
				menu.setOnMenuItemClickListener(new PopupPlaylistListener(activity, content, anchorView, contentId));
				menu.show();
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
