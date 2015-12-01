package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class AddMixFragment extends RecyclerFragment {

	public AddMixFragment(){}

	public static AddMixFragment newInstance(String json){
		AddMixFragment fragment = new AddMixFragment();
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
		List<Mix> mixList;
		Mixes mixes;

		datas.add(new Section2("Genre", "genre"));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Rock"));
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
		mixList.add(new Mix("", "Travel"));
		mixList.add(new Mix("", "Talk"));
		mixes = new Mixes(mixList);
		datas.add(mixes);

		return datas;
	}
}
