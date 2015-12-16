package com.cyclone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.player.gui.AudioPlayerContainerActivity;
import com.cyclone.player.util.Strings;

/**
 * Created by gilang on 07/11/2015.
 */
public abstract class MasterActivity extends AudioPlayerContainerActivity implements GestureDetector
		.OnGestureListener{

	public static final int FRAGMENT_PROGRAM_PAGE = 101;
	public static final int FRAGMENT_PERSON_PROFILE = 102;
	public static final int FRAGMENT_PLAYER = 103;
	public static final int FRAGMENT_ALBUM = 104;
	public static final int FRAGMENT_ARTIST = 105;
	public static final int FRAGMENT_RADIO_PROFILE = 106;
	public static final int FRAGMENT_VIRTUAL_CARD = 107;
	public static final int FRAGMENT_CLUB = 108;
	public static final int FRAGMENT_NOTIFICATION = 109;
	public static final int FRAGMENT_SETTINGS = 110;
	public static final int FRAGMENT_LIVE = 111;
	public static final int FRAGMENT_PROGRAMS = 112;
	public static final int FRAGMENT_ANNOUNCERS = 113;
	public static final int FRAGMENT_FEED = 114;
	public static final int FRAGMENT_PEOPLE = 115;
	public static final int FRAGMENT_ACCOUNT_SETTINGS = 116;
	public static final int FRAGMENT_STREAM_PLAYER = 117;
	public static final int FRAGMENT_REQUEST = 118;
	public static final int FRAGMENT_HOME = 119;
	public static final int FRAGMENT_CATEGORY = 120;
	public static final int FRAGMENT_SUBCATEGORY = 121;
	public static final int FRAGMENT_GRID_MIX = 122;
	public static final int FRAGMENT_ADD_MIX = 123;
	public static final int FRAGMENT_COMMENT = 124;
	public static final int FRAGMENT_MIX = 125;
	public static final int FRAGMENT_FAVORITES = 126;
	public static final int FRAGMENT_APP_SETTINGS = 127;
	public static final int FRAGMENT_NOTIFICATION_SETTINGS = 128;
	public static final int FRAGMENT_ABOUT = 129;
	public static final int FRAGMENT_ADD_PLAYLIST = 130;
	public static final int FRAGMENT_ADD_MIX_FORM = 131;
	public static final int FRAGMENT_ADD_PLAYLIST_FORM = 132;
	public static final int FRAGMENT_TRACK_LIST = 133;
	public static final int FRAGMENT_PLAYLIST = 134;
	public static final int FRAGMENT_UPLOAD = 135;
	public static final int FRAGMENT_UPLOAD_FINISHED = 136;

	public AppBarLayout appBarLayout;
	public boolean isExpanded = true;

	protected GestureDetectorCompat gd;
	protected View miniPlayer;
	protected ImageButton btnMiniPlay, btnMiniNext;
	protected OnOffsetChangedListener callback;
	protected Toolbar toolbar;

	public static final String ACTION_SHOW_PLAYER = Strings.buildPkgString("cyclone.ShowPlayer");
	public final static String EXIT_PLAYER = Strings.buildPkgString("cyclone.EXIT_PLAYER");
	public static final String ulr_stream = "http://stream.suararadio.com:8000/bandung_klitefm_mp3";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	protected void setupToolbar(){
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	protected void setupMiniPlayer(){
		miniPlayer = findViewById(R.id.minimized_player);
		btnMiniNext = (ImageButton) miniPlayer.findViewById(R.id.btn_next);
		btnMiniPlay = (ImageButton) miniPlayer.findViewById(R.id.btn_play);

		miniPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MasterActivity.this, DrawerActivity.class);
				i.putExtra("fragmentType", FRAGMENT_PLAYER);
				i.putExtra("title", "Player");
				startActivity(i);
			}
		});

		btnMiniNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MasterActivity.this, "Next Pressed", Toast.LENGTH_SHORT).show();
			}
		});

		btnMiniPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PlayerFragment.state == PlayerFragment.STATE_STOP) {
					PlayerFragment.state = PlayerFragment.STATE_PLAYING;
					btnMiniPlay.setImageResource(R.drawable.ic_pause_white_36dp);
				} else {
					PlayerFragment.state = PlayerFragment.STATE_STOP;
					btnMiniPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
				}
				SharedPreferences pref = getSharedPreferences(getString(R.string
						.preference_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt("state", PlayerFragment.state);
				editor.commit();
			}
		});

		SharedPreferences pref = getSharedPreferences(getString(R.string.preference_key), Context
				.MODE_PRIVATE);
		if(pref.getInt("state", PlayerFragment.STATE_STOP) == PlayerFragment.STATE_STOP){
			miniPlayer.setVisibility(View.GONE);
		}else{
			miniPlayer.setVisibility(View.VISIBLE);
		}
	}

	protected void setupGestureListener(){
		gd = new GestureDetectorCompat(this, this);
		CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
		layout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("touch");
				return gd.onTouchEvent(event);
			}
		});
	}

	protected void setupAppbarLayout(){
		appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				float percent = (float)Math.abs(verticalOffset) / (float)appBarLayout
						.getTotalScrollRange()
						* 100;
//				System.out.println(percent);
				if(percent == 0) {
					isExpanded = true;
					System.out.println("expanded blalbla");
				}
				else if(percent == 100) {
					isExpanded = false;
					System.out.println("collapsed blalbla");
				}
				if(percent == 100 || percent == 0){
					System.out.println("finish");
				}
				if(callback != null) {
					callback.onChanged(percent);
				}
			}
		});
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//		System.out.println("fling header: " + velocityX + " " + velocityY);
//		if(changing)
//			return true;
//		if(Math.abs(velocityY) > 50){
//			changing = true;
//			if(velocityY < 0){
//				appBarLayout.setExpanded(false);
//				System.out.println("collapsed");
//			}else if(velocityY > 0){
//				appBarLayout.setExpanded(true);
//				System.out.println("expanded");
//			}
//		}
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
//		if(changing && lastPercent > 50) {
//			appBarLayout.setExpanded(false);
//			return true;
//		}else if(changing && lastPercent < 50){
//			appBarLayout.setExpanded(true);
//			return true;
//		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
//		if(changing)
//			return true;
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		if(distanceY > 0){
			appBarLayout.setExpanded(false);
			if(isExpanded)
				return true;
			else
				return false;
		}else{
			appBarLayout.setExpanded(true);
		}
		return false;

	}

	@Override
	public void onLongPress(MotionEvent e) {

	}


}
