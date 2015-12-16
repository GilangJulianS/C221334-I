package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Album;
import com.cyclone.model.Section;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 02/11/2015.
 */
public class ArtistFragment extends RecyclerFragment {

	public ArtistFragment(){}

	public static ArtistFragment newInstance(String json){
		ArtistFragment fragment = new ArtistFragment();
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
		return R.layout.part_header_artist;
	}

	@Override
	public void prepareHeader(View v) {
		setupHeader(v, json);
	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void setupHeader(View v, String json){

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Section("Popular Tracks", null));
		datas.add(new Song("Counting Stars", "389,233"));
		datas.add(new Song("Love Runs Out", "210,321"));
		datas.add(new Song("If I Lose Myself", "231,312"));
		datas.add(new Song("Feel Again", "255,912"));
		datas.add(new Song("What You Wanted", "187,512"));
		datas.add(new Section("Albums", null));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Section("Appears on Showlist", null));
		datas.add(new Album("", "Funky Sunshine", "Playlist - By Imam Darto"));
		datas.add(new Album("", "Waking Up", "Mix - By Dimas Danang"));
		return datas;
	}
}
