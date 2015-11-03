package com.cyclone;
// ####################### EDITED DISINI BUAT TES #######################
// ####################### EDITED DISINI BUAT TES #######################

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.fragment.AlbumFragment;
import com.cyclone.fragment.ArtistFragment;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.ProgramPageFragment;

public class CollapseActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

	public static final int LAYOUT_PROGRAM_PAGE = 101;
	public static final int LAYOUT_PERSON_PROFILE = 102;
	public static final int LAYOUT_PLAYER = 103;
	public static final int LAYOUT_ALBUM = 104;
	public static final int LAYOUT_ARTIST = 105;

	private GestureDetectorCompat gd;
	public AppBarLayout appBarLayout;
	public boolean isExpanded = true;
	public boolean changing = false;

	private View miniPlayer;
	private OnOffsetChangedListener callback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!io.vov.vitamio.LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.activity_collapse);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		miniPlayer = (View) findViewById(R.id.minimized_player);
		SharedPreferences pref = getSharedPreferences(getString(R.string.preference_key), Context
				.MODE_PRIVATE);
		if(pref.getInt("state", PlayerFragment.STATE_STOP) == PlayerFragment.STATE_STOP){
			miniPlayer.setVisibility(View.GONE);
		}else{
			miniPlayer.setVisibility(View.VISIBLE);
		}

		CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
				.collapsing_toolbar_layout);
		toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);

		FragmentManager manager = getSupportFragmentManager();
		final Intent caller = getIntent();
		if(caller != null) {
			int layout = caller.getExtras().getInt("layout", -1);
			int mode = caller.getExtras().getInt("mode", -1);
			String transitionId = caller.getExtras().getString("transition", "profile");
			String title = caller.getExtras().getString("title", "");
			if (!title.equals("")) {
				toolbarLayout.setTitle(title);
			}
			if (layout == LAYOUT_PROGRAM_PAGE) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramPageFragment
						.newInstance()).commit();
			} else if (layout == LAYOUT_PERSON_PROFILE) {
				PersonProfileFragment fragment = PersonProfileFragment.newInstance(mode, transitionId);
				callback = fragment;
				manager.beginTransaction().replace(R.id.container, fragment).commit();
			} else if(layout == LAYOUT_PLAYER){
				callback = null;
				manager.beginTransaction().replace(R.id.container, PlayerFragment.newInstance())
						.commit();
			}else if(layout == LAYOUT_ALBUM){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AlbumFragment.newInstance())
						.commit();
			}else if(layout == LAYOUT_ARTIST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ArtistFragment.newInstance())
						.commit();
			}
		}

//		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//		fab.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//						.setAction("Action", null).show();
//			}
//		});
		gd = new GestureDetectorCompat(this, this);
		CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
		layout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println("touch");
				if(!changing)
					return gd.onTouchEvent(event);
				else
					return true;
			}
		});


		appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
		appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				float percent = (float)Math.abs(verticalOffset) / (float)appBarLayout
						.getTotalScrollRange()
						* 100;
//				System.out.println(percent);
				if(percent == 100 || percent == 0){
					changing = false;
//					System.out.println("finish");
				}
				if(callback != null) {
					callback.onChanged(percent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id){
			case android.R.id.home:
				supportFinishAfterTransition();
//				onBackPressed();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		System.out.println("fling header: " + velocityX + " " + velocityY);
		if(Math.abs(velocityY) > 50){
			changing = true;
			if(velocityY < 0){
				appBarLayout.setExpanded(false);
				System.out.println("collapsed");
			}else if(velocityY > 0){
				appBarLayout.setExpanded(true);
				System.out.println("expanded");
			}
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

}
