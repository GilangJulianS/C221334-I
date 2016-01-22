package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/12/2015.
 */
public class PlaylistFragment extends RecyclerFragment {

	public PlaylistFragment(){}

	public static PlaylistFragment newInstance(String json){
		PlaylistFragment fragment = new PlaylistFragment();
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
		datas.add(new SubcategoryItem("", "Could it Be", "Raisa"));
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
		datas.add(new SubcategoryItem("", "Get Lucky", "Daft Punk"));
		return datas;
	}
}
