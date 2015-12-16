package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentFragment extends RecyclerFragment {

	public CommentFragment(){}

	public static CommentFragment newInstance(String json){
		CommentFragment fragment = new CommentFragment();
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
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		return datas;
	}
}
