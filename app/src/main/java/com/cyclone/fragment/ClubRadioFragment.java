package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 17/10/2015.
 */
public class ClubRadioFragment extends RecyclerFragment {

	public ClubRadioFragment(){}

	public static ClubRadioFragment newInstance(String json){
		ClubRadioFragment fragment = new ClubRadioFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		return datas;
	}
}
