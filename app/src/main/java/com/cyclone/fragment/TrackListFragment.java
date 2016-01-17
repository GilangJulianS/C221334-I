package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Content;
import com.cyclone.model.Data;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/12/2015.
 */
public class TrackListFragment extends RecyclerFragment {

	public TrackListFragment(){}

	public static TrackListFragment newInstance(String json){
		TrackListFragment fragment = new TrackListFragment();
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

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		for(Object o : Data.getData()) {
			Content c = (Content) o;
			datas.add(new SubcategoryItem("", c.txtPrimary, c.txtSecondary, SubcategoryItem.TYPE_DELETABLE, ""));
		}
		return datas;
	}
}
