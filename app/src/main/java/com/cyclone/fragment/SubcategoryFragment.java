package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Album;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryFragment extends RecyclerFragment {

	public SubcategoryFragment(){}

	public static SubcategoryFragment newInstance(String json){
		SubcategoryFragment fragment = new SubcategoryFragment();
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
		datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson"));
		datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons"));
		datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta"));
		datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo"));
		datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake"));
		datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson"));
		datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons"));
		datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta"));
		datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo"));
		datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake"));
		return datas;
	}
}
