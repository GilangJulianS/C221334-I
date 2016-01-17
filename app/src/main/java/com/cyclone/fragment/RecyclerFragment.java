package com.cyclone.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.Utils.UtilUser;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.interfaces.getData;
import com.cyclone.loopback.GetJsonFragment;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.Profile;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.model.radioProgram;
import com.cyclone.loopback.repository.CommentRepository;
import com.cyclone.loopback.repository.FeedTimelineRepository;
import com.cyclone.loopback.repository.ProfileRepository;
import com.cyclone.loopback.repository.radioProgramRepository;
import com.cyclone.model.Comment;
import com.cyclone.service.ServiceGetData;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 20/11/2015.
 */
public abstract class RecyclerFragment extends GetJsonFragment implements OnOffsetChangedListener, SwipeRefreshLayout.OnRefreshListener {

	protected RecyclerView recyclerView;
	protected RecyclerView.LayoutManager layoutManager;
	protected UniversalAdapter adapter;
	protected List<Object> datas;
	protected SwipeRefreshLayout swipeLayout;
	protected DrawerActivity activity;
	protected SlidingLayer slidingLayer;
	protected GestureDetectorCompat gd;
	protected String json;
	protected String DataId;
	protected boolean swipeEnabled = false;
	protected ProgressBar progressBar;

	protected Context recuclerContext;
	protected getData mGetData;
	ProgressDialog progressDialog;
	ViewGroup CommentPlace;
	EditText formComment;
	ImageButton btnSendComment;

	protected String AccountId;

	boolean isAnimate = true;

	public RecyclerFragment(){}

	public abstract List<Object> getDatas();

	public abstract void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState);

	public abstract int getColumnNumber();

	public abstract boolean isRefreshEnabled();

	public abstract int getHeaderLayoutId();

	public abstract void prepareHeader(View v);

	public abstract int getSlidingLayoutId();

	public abstract void prepareSlidingMenu(View v);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);
		progressDialog = new ProgressDialog(getActivity(),R.style.transparentBackgroundProgesDialog);
		prepareDatas();
		prepareViews(v);

		if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_HOME || DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){
			if(UtilArrayData.ContentNews.size() > 0 ) {
				if (datas != null && datas.size() > 0) {
					try{
						progressBar.setVisibility(View.GONE);
						cnt = 0;
						datas.clear();
						prepareDatas();
						animate(datas.get(0));
					}catch (Exception e){}
				}
			} else{
				cnt = 0;
				if(progressBar != null)
					progressBar.setVisibility(View.VISIBLE);
				onRefresh();
			}
		} else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PLAYER){
			if (datas != null && datas.size() > 0){
				if(progressBar != null)
					progressBar.setVisibility(View.GONE);
				NoAnimate();
			}
		} else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_LIVE){
			System.out.println("on LIve");
			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);

		} else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){

		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_CLUB){
			if(UtilArrayData.feedTimelines.size() > 0){
				progressBar.setVisibility(View.GONE);
				cnt = 0;
				datas.clear();
				prepareDatas();
				animate(datas.get(0));
			}
			else{
				cnt = 0;
				if(progressBar != null)
					progressBar.setVisibility(View.VISIBLE);
				onRefresh();
			}

		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PERSON_PROFILE){
			progressBar.setVisibility(View.GONE);
			cnt = 0;
			datas.clear();
			prepareDatas();
			animate(datas.get(0));
			getDataProfile();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PROGRAMS){
			if(UtilArrayData.radioPrograms.size()>0){
				progressBar.setVisibility(View.GONE);
				cnt = 0;
				datas.clear();
				prepareDatas();
				animate(datas.get(0));
			}
			else{
				cnt = 0;
				if(progressBar != null)
					progressBar.setVisibility(View.VISIBLE);
				onRefresh();
			}
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_COMMENT){
			CommentPlace.setVisibility(View.VISIBLE);

				cnt = 0;
				if(progressBar != null)
					progressBar.setVisibility(View.VISIBLE);
				onRefresh();


			btnSendComment.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (formComment.getText().length()>0 && !formComment.getText()
					.toString().equalsIgnoreCase("") && !formComment.getText()
							.toString().equalsIgnoreCase(" ") && !formComment.getText()
							.toString().equalsIgnoreCase("  ") ){
						final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
						final CommentRepository commentRepository = restAdapter.createRepository(CommentRepository.class);
						commentRepository.post(DataId, formComment.getText().toString(), new Adapter.Callback() {
							@Override
							public void onSuccess(String response) {

							}

							@Override
							public void onError(Throwable t) {

							}
						});
						SimpleDateFormat s = new SimpleDateFormat("hh:mm");
						String format = s.format(new Date());
						List<Object> datas = new ArrayList<>();
						datas.add(new Comment("", UtilUser.currentUser.getUsername(), formComment.getText().toString(), format));
						adapter.datas.add(0,datas.get(0));
						formComment.setText(null);
						adapter.notifyDataSetChanged();
					}

				}
			});

		}
		else{
			progressBar.setVisibility(View.GONE);
			cnt = 0;
			datas.clear();
			prepareDatas();
			animate(datas.get(0));
		}
		onCreateView(v, parent, savedInstanceState);

		return v;
	}

	private void prepareDatas(){
		try{
			datas.clear();
		}catch (Exception e){}
		datas = getDatas();
	}

	protected void bindViews(View v){
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		progressBar =(ProgressBar)v.findViewById(R.id.progressbar);
		CommentPlace = (ViewGroup) v.findViewById(R.id.viewNewComent);
		formComment = (EditText) v.findViewById(R.id.formComment);
		btnSendComment = (ImageButton)v.findViewById(R.id.btnCommentSend);
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
		if (isRefreshEnabled()) {
			swipeLayout.setOnRefreshListener(this);
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

	public static int cnt = 0;
	protected void animate(final Object o){
		cnt ++;
		System.out.println("Count nimate : "+cnt);
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
					System.out.println("Count data ada !!");
					isAnimate = false;
					animate(datas.get(0));
					swipeLayout.setEnabled(false);
				} else {
					System.out.println("Count data habis");
					isAnimate = false;
					swipeLayout.setEnabled(true);
				}
			}
		}, delay);
	}

	protected void NoAnimate(){
		progressDialog.dismiss();
		for (int i = 0; i<datas.size(); i++){
			adapter.add(datas.get(i));
		}
		isAnimate = false;
		swipeLayout.setEnabled(true);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		if(context instanceof DrawerActivity) {
			activity = (DrawerActivity)context;
		}
		if(hasSlidingLayout()){
			LayoutInflater inflater = activity.getLayoutInflater();
			slidingLayer = (SlidingLayer) activity.findViewById(R.id.sliding_layer);
			View slidingMenu = inflater.inflate(getSlidingLayoutId(), slidingLayer, false);
			prepareSlidingMenu(slidingMenu);
			slidingLayer.removeAllViews();
			slidingLayer.addView(slidingMenu);
		}
		if(hasHeader()){
			activity = (DrawerActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(getHeaderLayoutId(), parallaxHeader, false);
			try{
				prepareHeader(header);
				parallaxHeader.removeAllViews();
				parallaxHeader.addView(header);
			}catch (Exception e){}

		}
	}

	@Override
	public void onChanged(float percent) {

		System.out.println("percent : " + percent);
		if(hasHeader()) {
			if (percent == 0 && !swipeEnabled && !isAnimate) {
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

	public boolean hasSlidingLayout(){
		return getSlidingLayoutId() != 0;
	}

	protected void setDataAfterService(){
		adapter.datas.clear();
		adapter.notifyDataSetChanged();
		prepareDatas();
		adapter.datas = datas;
		if(datas != null && datas.size() > 0)
			animateNoDelay(datas.get(0));
	}

	protected void animateNoDelay(final Object o){
		adapter.datas = datas;
		adapter.notifyDataSetChanged();
		/*int delay = 0;
		if(getColumnNumber() == 1)
			delay = 0;
		else
			delay = 0;

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(o);
				datas.remove(o);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animateNoDelay(datas.get(0));
					swipeLayout.setEnabled(false);
				} else {
					isAnimate = false;
					swipeLayout.setEnabled(true);
				}
			}
		}, delay);*/
	}

	@Override
	public void onRefresh() {
		//datas.clear();
		System.out.println("on refresh");
		swipeEnabled = false;
		swipeLayout.setRefreshing(true);
		if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_HOME ){
			if (UtilArrayData.program == null){
				getDataLive();
			}
			getDataHome();

			//getDataHome();
		}
		else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_LIVE){
			getDataLive();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){
			getDataHome();
			//getDataHome();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_CLUB){
			getDataClub();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PROGRAMS){
			getDataRadioProgram();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_COMMENT){
			getDataComment();
		}
	}

	protected void showData(){
			swipeLayout.setRefreshing(false);
			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);

			adapter.datas.clear();
			adapter.notifyDataSetChanged();

			prepareDatas();
			animate(datas.get(0));
	}

	void getDataHome(){
		try {
			ServiceGetData serviceGetData = new ServiceGetData();
			serviceGetData.getDataHome(recuclerContext, mGetData);
		} catch (Exception e) {
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			onRefresh();

		}
	}

	public void getDataLive(){
		try {
			ServiceGetData serviceGetData = new ServiceGetData();
			serviceGetData.getDataStream(recuclerContext, mGetData);
		} catch (Exception e) {
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			onRefresh();
		}
	}

	public void getDataClub(){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final FeedTimelineRepository feedTimelineRepository = restAdapter.createRepository(FeedTimelineRepository.class);

		feedTimelineRepository.createContract();
		feedTimelineRepository.get("0", "10", new ListCallback<FeedTimeline>() {
			@Override
			public void onSuccess(List<FeedTimeline> objects) {
				System.out.println("here here here");
				showData();

			}

			@Override
			public void onError(Throwable t) {

				//showData();
			}
		});
	}

	public void getDataProfile(){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final ProfileRepository profileRepository = restAdapter.createRepository(ProfileRepository.class);

		profileRepository.get("566f9561bf285a0273dada45", new ListCallback<Profile>() {

			@Override
			public void onSuccess(List<Profile> objects) {
				//UtilArrayData.CurrentProfile = objects.get(0);
				//System.out.println(UtilArrayData.CurrentProfile.getUsername()+"}}}}}}}}}}}}}}}}}}}");
			}

			@Override
			public void onError(Throwable t) {

			}
		});
	}

	public void getDataRadioProgram(){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final radioProgramRepository programRepository= restAdapter.createRepository(radioProgramRepository.class);

		programRepository.get(ServerUrl.RADIO_ID, new ListCallback<radioProgram>() {
			@Override
			public void onSuccess(List<radioProgram> objects) {
				System.out.println("on Successssss LLLLL :::: "+ objects.get(0).getName());
				showData();
			}

			@Override
			public void onError(Throwable t) {

			}
		});
	}

	public void getDataComment(){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final CommentRepository commentRepository = restAdapter.createRepository(CommentRepository.class);

		commentRepository.get(DataId, 0 , 10, new ListCallback<comment>() {
			@Override
			public void onSuccess(List<comment> objects) {
				System.out.println("comment data recived");
				showData();
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("comment data error : "+ t);
			}
		});
	}

}
