package com.cyclone.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section2;

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

	public void bindHeader(View v){
		ViewGroup groupLikes = (ViewGroup) v.findViewById(R.id.group_likes);
		ViewGroup groupComments = (ViewGroup) v.findViewById(R.id.group_comments);
		ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);

		groupLikes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("layout", MasterActivity.LAYOUT_PEOPLE);
				i.putExtra("title", "Likes");
				startActivity(i);
			}
		});

		groupComments.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("layout", MasterActivity.LAYOUT_COMMENT);
				i.putExtra("title", "Comments");
				startActivity(i);
			}
		});


		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		List<Mix> mixList;
		Mixes mixes;

		datas.add(new Section2("Genre", "genre"));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Pop"));
		mixList.add(new Mix("", "Electronic"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section2("Artist", "artist"));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Raisa"));
		mixList.add(new Mix("", "Daft Punk"));
		mixList.add(new Mix("", "Maroon 5"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section2("Radio Content", "content"));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "News"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		return datas;
	}

}
