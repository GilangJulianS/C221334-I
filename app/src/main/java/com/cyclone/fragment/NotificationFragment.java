package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Notification;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 25/10/2015.
 */
public class NotificationFragment extends Fragment {

	private RecyclerView recyclerView;
	private UniversalAdapter adapter;
	private RecyclerView.LayoutManager manager;
	private List<Notification> notifications;
	private SwipeRefreshLayout swipeLayout;

	public NotificationFragment(){}

	public static NotificationFragment newInstance(){
		NotificationFragment fragment = new NotificationFragment();
		fragment.notifications = new ArrayList<>();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		manager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(manager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		recyclerView.setAdapter(adapter);

		notifications = parse("");

		animate(notifications.get(0));

		return v;
	}

	public List<Notification> parse(String json){
		List<Notification> datas = new ArrayList<>();
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		return datas;
	}


	private void animate(final Notification notification){
		final Handler handler = new Handler();
		final Notification n = notification;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(n);
				notifications.remove(n);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!notifications.isEmpty()) {
					animate(notifications.get(0));
				}
			}
		}, 50);
	}
}
