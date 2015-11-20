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
public class ArtistFragment extends Fragment {

	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private UniversalAdapter adapter;
	private List<Object> objects;
	private SwipeRefreshLayout swipeLayout;
	private DrawerActivity activity;
	private GestureDetectorCompat gd;

	public ArtistFragment(){}

	public static ArtistFragment newInstance(){
		ArtistFragment fragment = new ArtistFragment();
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

		if(activity != null){
			gd = new GestureDetectorCompat(activity, new SnapGestureListener(activity));
			recyclerView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("touch recycler");
					if(layoutManager.findFirstCompletelyVisibleItemPosition() == 0)
						return gd.onTouchEvent(event);
					return false;
				}
			});
		}

		return v;
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		if(context instanceof DrawerActivity) {
			activity = (DrawerActivity) context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_artist, parallaxHeader,
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
		datas.add(new Section("Popular Tracks"));
		datas.add(new Song("Counting Stars", "389,233"));
		datas.add(new Song("Love Runs Out", "210,321"));
		datas.add(new Song("If I Lose Myself", "231,312"));
		datas.add(new Song("Feel Again", "255,912"));
		datas.add(new Song("What You Wanted", "187,512"));
		datas.add(new Section("Albums"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Section("Appears on Showlist"));
		datas.add(new Album("", "Funky Sunshine", "Playlist - By Imam Darto"));
		datas.add(new Album("", "Waking Up", "Mix - By Dimas Danang"));
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
