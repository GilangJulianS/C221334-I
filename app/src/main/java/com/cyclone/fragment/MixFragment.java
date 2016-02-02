package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class MixFragment extends RecyclerFragment {

	public MixFragment(){}

	public static MixFragment newInstance(String json){
		MixFragment fragment = new MixFragment();
		fragment.json = json;
		return fragment;
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
	public int getHeaderLayoutId(){
		return R.layout.part_header_mix;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeader(v);
	}

	@Override
	public int getSlidingLayoutId() {
		return R.layout.menu_mix;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void bindHeader(View v){
		ViewGroup groupLikes = (ViewGroup) v.findViewById(R.id.group_likes);
		ViewGroup groupComments = (ViewGroup) v.findViewById(R.id.group_comments);
		ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);

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
		List<Object> datas = new ArrayList<>();
		List<Mix> mixList;
		Mixes mixes;

		datas.add(new Section("Genre", "genre", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Pop", "Genre"));
		mixList.add(new Mix("", "Electronic", "Genre"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Artist", "artist", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Raisa", "Artist"));
		mixList.add(new Mix("", "Daft Punk", "Artist"));
		mixList.add(new Mix("", "Maroon 5", "Artist"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Radio Content", "content", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "News", "Radio Content"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		return datas;
	}

}
