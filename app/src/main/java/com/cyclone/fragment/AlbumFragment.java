package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
 * Created by gilang on 01/11/2015.
 */
public class AlbumFragment extends RecyclerFragment {

	public AlbumFragment(){}

	public static AlbumFragment newInstance(String json){
		AlbumFragment fragment = new AlbumFragment();
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
		return R.layout.part_header_album;
	}

	@Override
	public void prepareHeader(View v) {
		setupHeader(v, json);
	}

	public void setupHeader(View v, String json){
		ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);

		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Menu button clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Song("Counting Stars", "OneRepublic"));
		datas.add(new Song("Love Runs Out", "OneRepublic"));
		datas.add(new Song("If I Lose Myself", "OneRepublic"));
		datas.add(new Song("Feel Again", "OneRepublic"));
		datas.add(new Song("What You Wanted", "OneRepublic"));
		datas.add(new Song("I Lived", "OneRepublic"));
		datas.add(new Song("Light It Up", "OneRepublic"));
		datas.add(new Song("Life In Color", "OneRepublic"));
		datas.add(new Song("Counting Stars", "OneRepublic"));
		datas.add(new Song("Love Runs Out", "OneRepublic"));
		datas.add(new Song("If I Lose Myself", "OneRepublic"));
		datas.add(new Song("Feel Again", "OneRepublic"));
		datas.add(new Song("What You Wanted", "OneRepublic"));
		datas.add(new Song("I Lived", "OneRepublic"));
		datas.add(new Song("Light It Up", "OneRepublic"));
		datas.add(new Song("Life In Color", "OneRepublic"));
		datas.add(new Section("More By OneRepublic", null));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		return datas;
	}
}
