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
public class AnnouncersFragment extends Fragment {

	private RecyclerView recyclerView;
	private UniversalAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private List<Announcer> announcers;
	private SwipeRefreshLayout swipeLayout;

	public AnnouncersFragment(){}

	public static AnnouncersFragment newInstance(){
		AnnouncersFragment fragment = new AnnouncersFragment();
		fragment.announcers = new ArrayList<>();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		layoutManager = new GridLayoutManager(getContext(), 3);
		recyclerView.setLayoutManager(layoutManager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(300);
		slideAnimator.setMoveDuration(300);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		announcers = parseData("");

		recyclerView.setAdapter(adapter);

		animate(announcers.get(0));

		return v;
	}

	private void animate(final Announcer announcer){
		final Handler handler = new Handler();
		final Announcer a = announcer;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(a);
				announcers.remove(a);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!announcers.isEmpty()) {
					animate(announcers.get(0));
				}
			}
		}, 50);
	}

	public List<Announcer> parseData(String json){
		List<Announcer> announcers = new ArrayList<>();


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
