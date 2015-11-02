package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
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
public class AlbumFragment extends Fragment {

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private UniversalAdapter adapter;
	private List<Object> objects;
	private SwipeRefreshLayout swipeLayout;

	public AlbumFragment(){}

	public static AlbumFragment newInstance(){
		AlbumFragment fragment = new AlbumFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		recyclerView.setAdapter(adapter);

		objects = parse("");

		animate(objects.get(0));

		return v;
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		AppCompatActivity activity;
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_album, parallaxHeader,
					false);
			setupHeader(header, "");
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}

	public void setupHeader(View v, String json){

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
		datas.add(new Section("More By OneRepublic"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		return datas;
	}

	private void animate(final Object object){
		final Handler handler = new Handler();
		final Object o = object;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(o);
				objects.remove(o);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!objects.isEmpty()) {
					animate(objects.get(0));
				}
			}
		}, 50);
	}
}
