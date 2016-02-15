package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cyclone.R;
import com.cyclone.custom.SettingsAdapter;

/**
 * Created by gilang on 26/10/2015.
 */
public class SettingsFragment extends Fragment {

	private RecyclerView recyclerView;
	private SettingsAdapter adapter;
	private RecyclerView.LayoutManager layoutManager;
	private SwipeRefreshLayout swipeLayout;
	ProgressBar progressBar;

	public SettingsFragment(){}

	public static SettingsFragment newInstance(){
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
		progressBar.setVisibility(View.GONE);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);

		adapter = new SettingsAdapter(getActivity());
		recyclerView.setAdapter(adapter);

		return v;
	}
}
