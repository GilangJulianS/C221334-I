package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Request;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 07/11/2015.
 */
public class RequestFragment extends Fragment{

	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private UniversalAdapter adapter;
	private SwipeRefreshLayout swipeLayout;
	private List<Object> datas;
	private DrawerActivity activity;
	private FloatingActionButton btnMessage, btnPhone;

	public RequestFragment(){}

	public static RequestFragment newInstance(){
		RequestFragment fragment = new RequestFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeLayout.setRefreshing(false);
						adapter.datas.clear();
						adapter.notifyDataSetChanged();
						datas = parse("");
						animate(datas.get(0));
					}
				}, 5000);
			}
		});

		layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");

		recyclerView.setAdapter(adapter);

		datas = parse("");

		animate(datas.get(0));

		return v;
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
		return datas;
	}

	private void animate(final Object o){
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(o);
				datas.remove(o);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animate(datas.get(0));
				}
			}
		}, 200);
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if(context instanceof DrawerActivity){
			activity = (DrawerActivity) context;
			ViewGroup fabContainer = (ViewGroup) activity.findViewById(R.id.container_fab);
			LayoutInflater inflater = activity.getLayoutInflater();
			View fab = inflater.inflate(R.layout.fab_message_phone, fabContainer, false);
			btnMessage = (FloatingActionButton) fab.findViewById(R.id.fab_message);
			btnMessage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "fab message clicked", Toast.LENGTH_SHORT).show();
				}
			});
			btnPhone = (FloatingActionButton) fab.findViewById(R.id.fab_phone);
			btnPhone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "fab phone clicked", Toast.LENGTH_SHORT).show();
				}
			});
			fabContainer.addView(fab);
		}
	}
}
