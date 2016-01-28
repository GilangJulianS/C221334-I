package com.cyclone.fragment;

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
import android.widget.ProgressBar;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.interfaces.getData;
import com.cyclone.loopback.GetJsonFragment;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.Profile;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.model.radioProfile;
import com.cyclone.loopback.model.radioProgram;
import com.cyclone.loopback.repository.CommentRepository;
import com.cyclone.loopback.repository.FeedTimelineRepository;
import com.cyclone.loopback.repository.MusicRepository;
import com.cyclone.loopback.repository.ProfileRepository;
import com.cyclone.loopback.repository.RadioContentRepository;
import com.cyclone.loopback.repository.radioProfileRepository;
import com.cyclone.loopback.repository.radioProgramRepository;
import com.cyclone.service.ServiceGetData;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.wunderlist.slidinglayer.SlidingLayer;

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
	protected boolean swipeEnabled = true;

	public static int cnt = 0;
	protected ProgressBar progressBar;
	boolean isAnimate = true;
	protected Context recuclerContext;
	protected getData mGetData;
	protected String DataId;

	public int scc = 0;

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

		prepareDatas();
		prepareViews(v);

		//Check data radio profil
		if(UtilArrayData.rdioProfile == null){
			//jika radio profil null ambil data radio rofile
			getRadioProfile();
		}

		if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_HOME || DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){
			if(UtilArrayData.AllRadioContent.size() > 0){
				if (datas != null && datas.size() > 0) {
					try{
						progressBar.setVisibility(View.GONE);
						cnt = 0;
						datas.clear();
						prepareDatas();
						animate(datas.get(0));
					}catch (Exception e){
						System.out.println("exeption : "+e);
					}
				}
			}else{
				cnt = 0;
				if(progressBar != null)
					progressBar.setVisibility(View.VISIBLE);
				onRefresh();
			}
		}
		//Sementara matiin karenga fungsi belum jalan
		else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PLAYER){
			if (datas != null && datas.size() > 0){
				if(progressBar != null)
					progressBar.setVisibility(View.GONE);
				NoAnimate();
			}
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_LIVE){
			System.out.println("on LIve");
			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			onRefresh();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_REQUEST){
			System.out.println("on Request");
			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			onRefresh();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){

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

		} else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PERSON_PROFILE){
			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			//getDataProfile();
		}
		//Sementara matiin karenga fungsi belum jalan
		/*else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PROGRAMS){
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
		}*/else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_COMMENT){

			cnt = 0;
			if(progressBar != null)
				progressBar.setVisibility(View.VISIBLE);
			onRefresh();
		}
		else{
			progressBar.setVisibility(View.GONE);
			cnt = 0;
			try{
				datas.clear();
				prepareDatas();
				if(datas.size()>0)
					animate(datas.get(0));
			}catch (Exception e){}


		}

		onCreateView(v, parent, savedInstanceState);

		return v;
	}

	private void prepareDatas(){
		datas = getDatas();
	}

	protected void bindViews(View v){
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		progressBar =(ProgressBar)v.findViewById(R.id.progressbar);
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
		progressBar.setVisibility(View.GONE);
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

			//dikasih try biyar ga close
			try{
				prepareHeader(header);
				parallaxHeader.removeAllViews();
				parallaxHeader.addView(header);
			}catch (Exception e){}
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
	}

	@Override
	public void onRefresh() {
		//datas.clear();
		scc = 0;
		System.out.println("on refresh");
		swipeEnabled = false;
		swipeLayout.setRefreshing(true);
		if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_HOME ){
			System.out.println("get home");
			getDataHome();
		}
		else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_LIVE){
			getDataLive();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_SUBCATEGORY){
			getDataHome();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_CLUB){
			getDataClub();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_PROGRAMS){
			getDataRadioProgram();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_COMMENT){
			getDataComment();
		}else if(DrawerActivity.getFragmentType() == MasterActivity.FRAGMENT_REQUEST){
			getRequest();
		}
		else{
			showData();
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

	public void getDataHome(){
		System.out.println("on getdata home");
		final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
		final RadioContentRepository radioContentRepository = restAdapter.createRepository(RadioContentRepository.class);
		final MusicRepository musicRepository = restAdapter.createRepository(MusicRepository.class);
		scc = 0;
		radioContentRepository.get(new Adapter.Callback() {
			@Override
			public void onSuccess(String response) {
				scc++;
				System.out.println("scc : " + scc + " | " + response);
				if (scc == 2) showData();
			}

			@Override
			public void onError(Throwable t) {
				onRefresh();
			}
		});

		musicRepository.get(new Adapter.Callback() {
			@Override
			public void onSuccess(String response) {
				scc++;
				System.out.println("scc : " + scc + " | " + response);
				if (scc == 2) showData();
			}

			@Override
			public void onError(Throwable t) {

			}
		});
	}

	public void getDataLive(){
		try {
			ServiceGetData serviceGetData = new ServiceGetData();
			serviceGetData.getDataStream(recuclerContext, mGetData);
		} catch (Exception e) {
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);
			//onRefresh();
		}
	}

	public void getRequest(){
		System.out.println("on Requesttttttttttttt");
		try {
			ServiceGetData serviceGetData = new ServiceGetData();
			serviceGetData.getDataRequest(RequestFragment.getmContext(), RequestFragment.getmGetData());
		} catch (Exception e) {
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);
			//onRefresh();
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

	public void getDataProfile(final Adapter.Callback callback){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final ProfileRepository profileRepository = restAdapter.createRepository(ProfileRepository.class);

		profileRepository.get(DataId, new ListCallback<Profile>() {

			@Override
			public void onSuccess(List<Profile> objects) {
				//UtilArrayData.CurrentProfile = objects.get(0);
				//System.out.println(UtilArrayData.CurrentProfile.getUsername()+"}}}}}}}}}}}}}}}}}}}");
				//showData();
				callback.onSuccess("");
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

		commentRepository.get(DataId, 0, 10, new ListCallback<comment>() {
			@Override
			public void onSuccess(List<comment> objects) {
				System.out.println("comment data recived");
				showData();
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("comment data error : " + t);
			}
		});
	}

	public void getRadioProfile(){
		final RestAdapter restAdapter = new RestAdapter(getContext(), ServerUrl.API_URL);
		final radioProfileRepository profileRepository= restAdapter.createRepository(radioProfileRepository.class);

		profileRepository.get(ServerUrl.RADIO_ID, new ObjectCallback<radioProfile>() {
			@Override
			public void onSuccess(radioProfile object) {
				UtilArrayData.rdioProfile = object;
			}

			@Override
			public void onError(Throwable t) {

			}
		});
	}
}
