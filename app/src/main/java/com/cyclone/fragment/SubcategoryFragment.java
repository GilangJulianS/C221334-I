package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.data.GetData;
import com.cyclone.data.GoPlay;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.media.MediaWrapper;

import java.util.List;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryFragment extends PlaybackServiceRecyclerFragment implements PlayOnHolder {
	String category;
	public static SubcategoryFragment fragment;
	public SubcategoryFragment(){}

	public static SubcategoryFragment newInstance(String json, String category){
		fragment = new SubcategoryFragment();
		fragment.json = json;
		fragment.category = category;
		System.out.println("subfragment category : "+ category);
		return fragment;
	}
	public static SubcategoryFragment getInstance(){
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
		/*List<Object> datas = new ArrayList<>();
		datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson"));
		datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons"));
		datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta"));
		datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo"));
		datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake"));
		datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson"));
		datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons"));
		datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta"));
		datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo"));
		datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake"));*/
		System.out.println("on parse");
		return GetData.getInstance().showData(GetData.DATA_SUB_CATEGORY,category);
	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {

	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {
		if(mService != null){
			mService.load(GoPlay.getInstance().getMedia(GoPlay.HOME_PLAY_ON_HOLDER, category), 0);
			mService.playIndex(position);
		} else{
			System.out.println("service null");
		}
	}
}
