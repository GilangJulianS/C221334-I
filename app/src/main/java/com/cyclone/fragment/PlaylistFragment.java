package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.data.GetData;
import com.cyclone.data.GoPlay;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.model.SubcategoryItem;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.media.MediaWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/12/2015.
 */
public class PlaylistFragment extends PlaybackServiceRecyclerFragment implements PlayOnHolder {

	private List<SubcategoryItem> completeItem;

	private static String Id;
	private static PlaylistFragment fragment;

	public PlaylistFragment(){}

	public static PlaylistFragment newInstance(String json, String id) {
		fragment = new PlaylistFragment();
		fragment.json = json;
		fragment.completeItem = new ArrayList<>();
		fragment.Id = id;
		fragment.playOnHolder = fragment;

		return fragment;
	}

	public static PlaylistFragment getInstance() {
		return fragment;
	}

	@Override
	public void onResume() {
		super.onResume();
		DrawerActivity.setFragmentType(DrawerActivity.FRAGMENT_PLAYLIST);
	}

	public static String getIdPlaylist() {
		return Id;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return R.layout.part_header_playlist;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeader(v);
	}

	@Override
	public int getSlidingLayoutId() {
		return R.layout.menu_playlist;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void bindHeader(View v){
		ViewGroup groupLikes = (ViewGroup) v.findViewById(R.id.group_likes);
		ViewGroup groupComments = (ViewGroup) v.findViewById(R.id.group_comments);
		ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		Button btnPLayyAll = (Button) v.findViewById(R.id.btn_play_all);
		Button btnLike = (Button) v.findViewById(R.id.btn_like);

		btnPLayyAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mService != null) {
					mService.load(GoPlay.getInstance().getMedia(GoPlay.HOME_PLAY_ON_HOLDER), 0);
					mService.playIndex(0);
				} else {
					System.out.println("service null");
				}
			}
		});

		groupLikes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_PEOPLE);
				i.putExtra("title", "Likes");
				startActivity(i);
			}
		});

		groupComments.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_COMMENT);
				i.putExtra("title", "Comments");
				startActivity(i);
			}
		});

		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingLayer.openLayer(true);
			}
		});
	}

	public List<Object> parse(String json){
		List<Object> datas = GetData.getInstance().showData(GetData.DATA_PLAYLIST);

//		System.out.println("ambillllll : "+ datas.get(0).toString());
		completeItem = new ArrayList<>();
		/*datas.add(new SubcategoryItem("", "Could it Be", "Raisa"));
		datas.add(new SubcategoryItem("", "Something About Us", "Daft Punk"));
		datas.add(new SubcategoryItem("", "Sugar", "Maroon 5"));
		datas.add(new SubcategoryItem("", "Get Lucky", "Daft Punk"));
		datas.add(new SubcategoryItem("", "Could it Be", "Raisa"));
		datas.add(new SubcategoryItem("", "Something About Us", "Daft Punk"));
		datas.add(new SubcategoryItem("", "Sugar", "Maroon 5"));
		datas.add(new SubcategoryItem("", "Get Lucky", "Daft Punk"));
		datas.add(new SubcategoryItem("", "Could it Be", "Raisa"));
		datas.add(new SubcategoryItem("", "Something About Us", "Daft Punk"));
		datas.add(new SubcategoryItem("", "Sugar", "Maroon 5"));
		datas.add(new SubcategoryItem("", "Get Lucky", "Daft Punk"));*/
		try {
			for (Object o : datas) {
				completeItem.add((SubcategoryItem) o);
			}
		} catch (Exception e) {
		}

		return datas;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);

		//ini masih forceclose

		/*SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			UniversalAdapter newAdapter;

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText, newAdapter);
				return true;
			}
		});*/

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String newText, UniversalAdapter newAdapter){
		if(!newText.equals("")) {
			newAdapter = new UniversalAdapter(activity);
			List<SubcategoryItem> searchResult = search(newText);
			for(SubcategoryItem s : searchResult){
				newAdapter.add(s);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<SubcategoryItem> search(String query){
		List<SubcategoryItem> result = new ArrayList<>();
		for(SubcategoryItem s : completeItem){
			if(s.txtPrimary.toLowerCase().contains(query.toLowerCase()) || s.txtSecondary.contains(query.toLowerCase())){
				result.add(s);
			}
		}
		return result;
	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {
		if (mService != null) {
			mService.load(GoPlay.getInstance().getMedia(GoPlay.HOME_PLAY_ON_HOLDER), 0);
			mService.playIndex(position);
		} else {
			System.out.println("service null");
		}

	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {

	}
}
