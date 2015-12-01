package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Announcer;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 11/10/2015.
 */
public class AnnouncersFragment extends RecyclerFragment {

	public AnnouncersFragment(){}

	public static AnnouncersFragment newInstance(String json){
		AnnouncersFragment fragment = new AnnouncersFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return null;
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
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	public List<Object> parseData(String json){
		List<Object> announcers = new ArrayList<>();


//		--------------- dummy ------------
		announcers.add(new Announcer("http://imgurl.com", "Adiel"));
		announcers.add(new Announcer("http://imgurl.com", "Arien"));
		announcers.add(new Announcer("http://imgurl.com", "Arie W"));
		announcers.add(new Announcer("http://imgurl.com", "Chandra"));
		announcers.add(new Announcer("http://imgurl.com", "Erdina"));
		announcers.add(new Announcer("http://imgurl.com", "Hasanah"));
		announcers.add(new Announcer("http://imgurl.com", "Indira"));
		announcers.add(new Announcer("http://imgurl.com", "Indra"));
		announcers.add(new Announcer("http://imgurl.com", "Ivan"));
		announcers.add(new Announcer("http://imgurl.com", "Kujang"));
		announcers.add(new Announcer("http://imgurl.com", "Rini"));
		announcers.add(new Announcer("http://imgurl.com", "Reno"));
		announcers.add(new Announcer("http://imgurl.com", "Sam"));
		announcers.add(new Announcer("http://imgurl.com", "Tira"));

		return announcers;
	}
}
