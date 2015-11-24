package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 20/11/2015.
 */
public abstract class RecyclerFragment extends Fragment implements OnOffsetChangedListener {

	protected RecyclerView recyclerView;
	protected RecyclerView.LayoutManager layoutManager;
	protected UniversalAdapter adapter;
	protected List<Object> datas;
	protected SwipeRefreshLayout swipeLayout;
	protected DrawerActivity activity;
	protected GestureDetectorCompat gd;
	protected String json;
	protected boolean swipeEnabled = true;

	public RecyclerFragment(){}

	public abstract List<Object> getDatas();

	public abstract void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState);

	public abstract int getColumnNumber();

	public abstract boolean isRefreshEnabled();

	public abstract int getHeaderLayoutId();

	public abstract void prepareHeader(View v);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		prepareDatas();
		prepareViews(v);

		if(datas != null && datas.size() > 0)
			animate(datas.get(0));

		return v;
	}

	private void prepareDatas(){
		datas = getDatas();
	}

	protected void bindViews(View v){
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
	}

	protected void prepareViews(View v){
		bindViews(v);
		setupSwipeLayout();
		setupLayoutManager();
		setupAnimator();
		setupAdapter();
		if(hasHeader()) setupGestureDetector();
	}

	private void setupSwipeLayout(){
		if(isRefreshEnabled()) {
			swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							swipeLayout.setRefreshing(false);
							adapter.datas.clear();
							adapter.notifyDataSetChanged();
							prepareDatas();
							animate(datas.get(0));
						}
					}, 5000);
				}
			});
		}else{
			swipeLayout.setEnabled(false);
		}
	}

	private void setupLayoutManager(){
		if(getColumnNumber() == 1){
			layoutManager = new LinearLayoutManager(activity);
		}else{
			layoutManager = new GridLayoutManager(activity, getColumnNumber());
		}
		recyclerView.setLayoutManager(layoutManager);
	}

	private void setupAnimator(){
		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);
	}

	private void setupAdapter(){
		adapter = new UniversalAdapter(activity);
		recyclerView.setAdapter(adapter);
	}

	private void setupGestureDetector(){
		if(activity != null){
			gd = new GestureDetectorCompat(activity, new SnapGestureListener(activity));
			recyclerView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if((getColumnNumber() == 1 && ((LinearLayoutManager)layoutManager)
							.findFirstCompletelyVisibleItemPosition() == 0) || (getColumnNumber()
							> 1 && ((GridLayoutManager)layoutManager)
							.findFirstCompletelyVisibleItemPosition() == 0)) {
						return gd.onTouchEvent(event);
					}
					return false;
				}
			});

		}
	}

	protected void animate(final Object o){
		int delay = 0;
		if(getColumnNumber() == 1)
			delay = 200;
		else
			delay = 50;

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
		}, delay);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		if(hasHeader()){
			if(context instanceof DrawerActivity) {
				activity = (DrawerActivity)context;
				ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
						.parallax_header);
				LayoutInflater inflater = activity.getLayoutInflater();
				View header = inflater.inflate(getHeaderLayoutId(), parallaxHeader, false);
				prepareHeader(header);
				parallaxHeader.removeAllViews();
				parallaxHeader.addView(header);
			}
		}else if(context instanceof DrawerActivity) {
			activity = (DrawerActivity)context;
		}
	}

	@Override
	public void onChanged(float percent) {
		if(hasHeader()) {
			if (percent == 0 && !swipeEnabled) {
				swipeEnabled = true;
				swipeLayout.setEnabled(true);
			} else if (percent != 0 && swipeEnabled) {
				swipeEnabled = false;
				swipeLayout.setEnabled(false);
			}
		}
	}

	public boolean hasHeader(){
		return getHeaderLayoutId() != 0;
	}
}
