package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Mix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class GridMixFragment extends RecyclerFragment {

	public GridMixFragment(){}

	public static GridMixFragment newInstance(String json){
		GridMixFragment fragment = new GridMixFragment();
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
		return 3;
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

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		return datas;
	}

}
