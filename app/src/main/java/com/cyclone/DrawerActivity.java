package com.cyclone;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.fragment.AccountSettingFragment;
import com.cyclone.fragment.AlbumFragment;
import com.cyclone.fragment.AnnouncersFragment;
import com.cyclone.fragment.ArtistFragment;
import com.cyclone.fragment.CategoryFragment;
import com.cyclone.fragment.ClubRadioFragment;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.fragment.LiveStreamFragment;
import com.cyclone.fragment.NotificationFragment;
import com.cyclone.fragment.PersonListFragment;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.fragment.PlayerFragment;
import com.cyclone.fragment.ProgramPageFragment;
import com.cyclone.fragment.ProgramsFragment;
import com.cyclone.fragment.RadioProfileFragment;
import com.cyclone.fragment.RequestFragment;
import com.cyclone.fragment.SettingsFragment;
import com.cyclone.fragment.StreamPlayerFragment;
import com.cyclone.fragment.SubcategoryFragment;
import com.cyclone.fragment.VirtualCardFragment;
import com.cyclone.player.CompatErrorActivity;
import com.cyclone.player.MediaDatabase;
import com.cyclone.player.MediaLibrary;
import com.cyclone.player.PreferencesActivity;
import com.cyclone.player.VLCApplication;
import com.cyclone.player.audio.AudioService;
import com.cyclone.player.audio.AudioServiceController;
import com.cyclone.player.audio.ServiceQueueJson;
import com.cyclone.player.util.VLCInstance;
import com.cyclone.player.util.WeakHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.LibVlcException;
import org.videolan.libvlc.LibVlcUtil;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaList;

import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends MasterActivity
		implements NavigationView.OnNavigationItemSelectedListener {
	public final static String TAG = "VLC/DrawerActivity";

	protected static final String ACTION_SHOW_PROGRESSBAR = "com.cyclone.ShowProgressBar";
	protected static final String ACTION_HIDE_PROGRESSBAR = "com.cyclone.HideProgressBar";
	protected static final String ACTION_SHOW_TEXTINFO = "com.cyclone.ShowTextInfo";
	public static final String ACTION_SHOW_PLAYER = "com.cyclone.ShowPlayer";

	private static final String PREF_FIRST_RUN = "first_run";


	private static final int ACTIVITY_RESULT_PREFERENCES = 1;
	private static final int ACTIVITY_SHOW_INFOLAYOUT = 2;

	public static final String PREF_GOOGLE = "google_login";
	public static final String PREF_GOOGLE_USR = "google_username";
	public static final String PREF_GOOGLE_EMAIL = "google_email";

	private Context mContext;
	private ActionBar mActionBar;
	private PlayerFragment mAudioPlayer;
	private AudioServiceController mAudioController;

	private ServiceConnection mAudioServiceConnection;

	private SharedPreferences mSettings;

	int layout;

	private int mVersionNumber = -1;
	private boolean mFirstRun = false;
	private boolean mScanNeeded = true;

	private Handler mHandler = new MainActivityHandler(this);
	private int mFocusedPrior = 0;
	private int mActionBarIconId = -1;

	private boolean isParentView = false;
	private boolean isCollapseLayout = false;
	private ActionBarDrawerToggle toggle;
	CollapsingToolbarLayout toolbarLayout;
	public static boolean showMiniPlayer = true;
	private ProgressDialog pDialog;

	private String mFilePath = "http://stream.suararadio.com/bloom-mae.mp3";
	private String mFilePath2 = "http://stream.suararadio.com/the-ballad-of-distant-love.mp3";
	private String mFilePath3 = "http://stream.suararadio.com/the-balloonist.mp3";
	private String mFilePath4 = "http://stream.suararadio.com/the-knife.mp3";
	private String mFilePath5 = "http://stream.suararadio.com/this-bed.mp3";
	private String mFilePath6 = "http://stream.suararadio.com/took-my-soul.mp3";

	// try for add
	private MediaLibrary mMediaLibrary;

	List<Media> mAudioList;
	public final static int MSG_LOADING = 0;

	LibVLC mlibVLC;
	private View miniPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		if (!LibVlcUtil.hasCompatibleCPU(this)) {
			Log.e(TAG, LibVlcUtil.getErrorMsg());
			Intent i = new Intent(this, CompatErrorActivity.class);
			startActivity(i);
			finish();
			super.onCreate(savedInstanceState);
			return;
		}
		AudioServiceController.getInstance().bindAudioService(this);
		mContext = this;
        /* Get the current version from package */
		PackageInfo pinfo = null;
		try {
			pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "package info not found.");
		}
		if (pinfo != null)
			mVersionNumber = pinfo.versionCode;

        /* Get settings */
		mSettings = PreferenceManager.getDefaultSharedPreferences(this);

		if(!mSettings.getBoolean(PREF_GOOGLE, false)){
			Intent intent = new Intent(this, EmptyActivity.class);
			intent.putExtra("title", "Login");
			startActivity(intent);
		}

        /* Check if it's the first run */
		mFirstRun = mSettings.getInt(PREF_FIRST_RUN, -1) != mVersionNumber;
		if (mFirstRun) {
			SharedPreferences.Editor editor = mSettings.edit();
			editor.putInt(PREF_FIRST_RUN, mVersionNumber);
			editor.commit();
		}



		try {
			// Start LibVLC
			mlibVLC = VLCInstance.getLibVlcInstance();
		} catch (LibVlcException e) {
			e.printStackTrace();
			Intent i = new Intent(this, CompatErrorActivity.class);
			i.putExtra("runtimeError", true);
			i.putExtra("message", "LibVLC failed to initialize (LibVlcException)");
			startActivity(i);
			finish();
			super.onCreate(savedInstanceState);
			return;
		}

        /* Load media items from database and storage */
		if (mScanNeeded)
			MediaLibrary.getInstance().loadMediaItems();

		super.onCreate(savedInstanceState);

		/*** Start initializing the UI ***/


		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean enableBlackTheme = pref.getBoolean("enable_black_theme", false);
		if (enableBlackTheme) {
			//setTheme(R.style.Theme_VLC_Black);
			//We need to manually change statusbar color, otherwise, it remains orange.
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				getWindow().setStatusBarColor(Color.DKGRAY);
			}
		}

		Intent caller = getIntent();
		if(caller != null && caller.getExtras() != null) {
			int rootLayout = caller.getExtras().getInt("activity", R.layout.activity_drawer);
			setContentView(rootLayout);
			if(rootLayout == R.layout.activity_drawer)
				isCollapseLayout = true;
		}
		else {
			setContentView(R.layout.activity_drawer);
			isCollapseLayout = true;
		}


		setupToolbar();
		setupMiniPlayer();
		setupAppbarLayout();
		setupGestureListener();

		miniPlayer = findViewById(R.id.minimized_player);

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.getMenu().getItem(3).setTitle(mSettings.getString(PREF_GOOGLE_USR, "User"));
		if(isCollapseLayout) {
			toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
					.collapsing_toolbar_layout);
			toolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
			toolbarLayout.setTitle("K-Lite FM Bandung");
		}

		if(caller != null && caller.getExtras() != null) {
			isParentView = caller.getExtras().getBoolean("parent", false);
			String title = caller.getExtras().getString("title", "");
			layout = caller.getExtras().getInt("layout", LAYOUT_HOME);
			int mode = caller.getExtras().getInt("mode", -1);
			int menuId = caller.getExtras().getInt("menuId", 0);
			String transitionId = caller.getExtras().getString("transition", "profile");
			FragmentManager manager = getSupportFragmentManager();
			if(title != null && !title.equals("")) {
				if(toolbarLayout != null)
					toolbarLayout.setTitle(title);
				else
					getSupportActionBar().setTitle(title);
			}
			if(layout == LAYOUT_RADIO_PROFILE){
				manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
				showMiniPlayer = true;

			}else if(layout == LAYOUT_HOME){
				manager.beginTransaction().replace(R.id.container, HomeFragment.newInstance("")).commit();
				showMiniPlayer = true;

			}else if(layout == LAYOUT_VIRTUAL_CARD){
				manager.beginTransaction().replace(R.id.container, VirtualCardFragment.newInstance()).commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_CLUB){
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_NOTIFICATION){
				manager.beginTransaction().replace(R.id.container, NotificationFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_SETTINGS){
				manager.beginTransaction().replace(R.id.container, SettingsFragment.newInstance()).commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_LIVE){
				manager.beginTransaction().replace(R.id.container, LiveStreamFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if (layout == LAYOUT_PROGRAM_PAGE) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramPageFragment
						.newInstance()).commit();
				showMiniPlayer = true;
			} else if (layout == LAYOUT_PERSON_PROFILE) {
				PersonProfileFragment fragment = PersonProfileFragment.newInstance(mode,
						transitionId, "");
				callback = fragment;
				manager.beginTransaction().replace(R.id.container, fragment).commit();
			} else if (layout == LAYOUT_PLAYER) {
				callback = null;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, PlayerFragment.newInstance(""))
						.commit();
			} else if (layout == LAYOUT_ALBUM) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, AlbumFragment.newInstance(""))
						.commit();
				showMiniPlayer = true;
			} else if (layout == LAYOUT_ARTIST) {
				callback = null;
				manager.beginTransaction().replace(R.id.container, ArtistFragment.newInstance(""))
						.commit();
				showMiniPlayer = true;
			} else if (layout == LAYOUT_PROGRAMS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ProgramsFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if (layout == LAYOUT_ANNOUNCERS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AnnouncersFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if (layout == LAYOUT_FEED){
				callback = null;
				manager.beginTransaction().replace(R.id.container, ClubRadioFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if (layout == LAYOUT_PEOPLE){
				callback = null;
				manager.beginTransaction().replace(R.id.container, PersonListFragment.newInstance("")).commit();
				showMiniPlayer = true;
			}else if (layout == LAYOUT_ACCOUNT_SETTINGS){
				callback = null;
				manager.beginTransaction().replace(R.id.container, AccountSettingFragment.newInstance()).commit();
			}else if(layout == LAYOUT_STREAM_PLAYER){
				callback = null;
				showMiniPlayer = false;
				manager.beginTransaction().replace(R.id.container, StreamPlayerFragment
						.newInstance()).commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_REQUEST){
				callback = null;
				manager.beginTransaction().replace(R.id.container, RequestFragment.newInstance(""))
						.commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_CATEGORY){
				callback = null;
				manager.beginTransaction().replace(R.id.container, CategoryFragment.newInstance(""))
						.commit();
				showMiniPlayer = true;
			}else if(layout == LAYOUT_SUBCATEGORY){
				callback = null;
				manager.beginTransaction().replace(R.id.container, SubcategoryFragment.newInstance(""))
						.commit();
				showMiniPlayer = true;
			}
			navigationView.getMenu().getItem(menuId).setChecked(true);
		}else{
			isParentView = true;
			FragmentManager manager = getSupportFragmentManager();
			manager.beginTransaction().replace(R.id.container, RadioProfileFragment.newInstance()).commit();
			navigationView.getMenu().getItem(0).setChecked(true);
		}

		if(isParentView){
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			toggle = new ActionBarDrawerToggle(
					this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			toggle.syncState();
		}else{
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			toggle = new ActionBarDrawerToggle(
					this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
			drawer.setDrawerListener(toggle);
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}


		 /* Set up the audio player */
		//mAudioPlayer = new PlayerFragment();
		mAudioController = AudioServiceController.getInstance();
		mAudioController.bindAudioService(this);
		mMediaLibrary = MediaLibrary.getInstance();

		/* Prepare the progressBar */
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_SHOW_PROGRESSBAR);
		filter.addAction(ACTION_HIDE_PROGRESSBAR);
		filter.addAction(ACTION_SHOW_TEXTINFO);
		filter.addAction(ACTION_SHOW_PLAYER);
		registerReceiver(messageReceiver, filter);

        /* Reload the latest preferences */
		reloadPreferences();

		imgCoverMini = (ImageView)findViewById(R.id.imgCoverMini);

		txtJudul = (TextView) findViewById(R.id.txtTitleMini);
		txtArtist = (TextView)findViewById(R.id.txtArtstMini);

		btnPlay = (ImageButton) findViewById(R.id.btnPlayMini);
		btnNext = (ImageButton) findViewById(R.id.btnNextMini);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mAudioController.isPlaying()) {
					mAudioController.pause();
					//btnPlay.setBackgroundResource(R.drawable.ic_pause_white_48dp);
					btnPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
				} else {
					mAudioController.play();
					//btnPlay.setBackgroundResource(R.drawable.ic_play_arrow_white_48dp);
					btnPlay.setImageResource(R.drawable.ic_pause_white_36dp);
				}
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mAudioController.next();
			}
		});

		miniPlayer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, DrawerActivity.class);
				intent.putExtra("parent", true);
				intent.putExtra("title", "Player");
				intent.putExtra("layout", MasterActivity.LAYOUT_PLAYER);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				finish();
			}
		});

		setupHandler();

	}

	@Override
	protected void onResume() {
		super.onResume();
		setupMiniPlayer();
		setupHandler();
		//mAudioController.addAudioPlayer(mAudioPlayer);
		AudioServiceController.getInstance().bindAudioService(this);

        /* FIXME: this is used to avoid having MainActivity twice in the backstack */
		if (getIntent().hasExtra(AudioService.START_FROM_NOTIFICATION))
			getIntent().removeExtra(AudioService.START_FROM_NOTIFICATION);

		if (mMediaLibrary.isWorking())
			mHandler.sendEmptyMessageDelayed(MSG_LOADING, 300);
		updateLists();
		mMediaLibrary.addUpdateHandler(mHandler1);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMediaLibrary.removeUpdateHandler(mHandler1);
		handler.removeCallbacks(sendToUi);
	}

	/**
	 * Handle changes on the list
	 */
	private Handler mHandler1 = new AudioBrowserHandler(this);
	private static class AudioBrowserHandler extends WeakHandler<DrawerActivity> {
		public AudioBrowserHandler(DrawerActivity owner) {
			super(owner);
		}

		@Override
		public void handleMessage(Message msg) {
			DrawerActivity fragment = getOwner();
			if(fragment == null) return;

			switch (msg.what) {
				case MediaLibrary.MEDIA_ITEMS_UPDATED:
					fragment.updateLists();
					break;
				case MSG_LOADING:
					/*if (fragment.mArtistsAdapter.isEmpty() && fragment.mAlbumsAdapter.isEmpty() &&
							fragment.mSongsAdapter.isEmpty() && fragment.mGenresAdapter.isEmpty())
						fragment.mSwipeRefreshLayout.setRefreshing(true);*/
			}
		}
	};
	private void updateLists() {

		mAudioList = MediaLibrary.getInstance().getAudioItems();
		if (mAudioList.isEmpty()){

		} else {

			/*mHandler1.sendEmptyMessageDelayed(MSG_LOADING, 300);

			ExecutorService tpe = Executors.newSingleThreadExecutor();
			ArrayList<Runnable> tasks = new ArrayList<Runnable>();
			tasks.add(updateArtists);
			tasks.add(updateAlbums);
			tasks.add(updateSongs);
			tasks.add(updateGenres);
			//process the visible list first
			tasks.add(0, tasks.remove(mFlingViewPosition));
			tasks.add(new Runnable() {
				@Override
				public void run() {
					synchronized (mAdaptersToNotify) {
						if (!mAdaptersToNotify.isEmpty())
							display();
					}
				}
			});
			for (Runnable task : tasks)
				tpe.submit(task);*/
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			unregisterReceiver(messageReceiver);
		} catch (IllegalArgumentException e) {}
		handler.removeCallbacks(sendToUi);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ACTIVITY_RESULT_PREFERENCES) {
			if (resultCode == PreferencesActivity.RESULT_RESCAN)
				MediaLibrary.getInstance().loadMediaItems(this, true);
			else if (resultCode == PreferencesActivity.RESULT_RESTART) {
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if(isParentView){
			super.onBackPressed();
		}else{
			supportFinishAfterTransition();
		}

		/*Intent intent = new Intent(this, DrawerActivity.class);
		intent.putExtra("parent", true);

		intent.putExtra("layout", MasterActivity.LAYOUT_HOME);
		intent.putExtra("activity", R.layout.activity_drawer);
		startActivity(intent);
		finish();*/
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
				if(!isParentView) {
					onBackPressed();
					return true;
				}
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		Intent intent = new Intent(this, DrawerActivity.class);
		intent.putExtra("parent", true);
		switch (id){
			case R.id.nav_home:
				intent.putExtra("layout", MasterActivity.LAYOUT_HOME);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_live:
				intent.putExtra("layout", MasterActivity.LAYOUT_LIVE);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				finish();
				break;
			case R.id.nav_klub:
				intent.putExtra("title", "Imam Darto");
				intent.putExtra("layout", MasterActivity.LAYOUT_CLUB);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_profile:
				intent.putExtra("layout", MasterActivity.LAYOUT_PERSON_PROFILE);
				intent.putExtra("title", mSettings.getString(PREF_GOOGLE_USR, "User"));
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				break;
			case R.id.nav_notification:
				intent.putExtra("title", "Notifications");
				intent.putExtra("layout", MasterActivity.LAYOUT_NOTIFICATION);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_virtual_card:
				intent.putExtra("title", "Virtual Card");
				intent.putExtra("layout", MasterActivity.LAYOUT_VIRTUAL_CARD);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);
				break;
			case R.id.nav_setting:
				intent.putExtra("title", "Settings");
				intent.putExtra("layout", MasterActivity.LAYOUT_SETTINGS);
				intent.putExtra("activity", R.layout.activity_drawer_standard);
				startActivity(intent);
				break;
			case R.id.nav_player:
				/*intent.putExtra("title", "Player");
				intent.putExtra("layout", MasterActivity.LAYOUT_PLAYER);
				intent.putExtra("activity", R.layout.activity_drawer);
				startActivity(intent);*/
				loadMediaQueue();
				break;
			case R.id.loguot:
				Intent in = new Intent(this, EmptyActivity.class);
				in.putExtra("title", "Login");
				startActivity(in);
				break;
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void reloadPreferences() {
		SharedPreferences sharedPrefs = getSharedPreferences("MainActivity", MODE_PRIVATE);
		//mCurrentFragment = sharedPrefs.getString("fragment", "video");
	}

	private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();


			if (action.equalsIgnoreCase(ACTION_SHOW_PROGRESSBAR)) {
				setSupportProgressBarIndeterminateVisibility(true);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else if (action.equalsIgnoreCase(ACTION_HIDE_PROGRESSBAR)) {
				setSupportProgressBarIndeterminateVisibility(false);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			} else if (action.equalsIgnoreCase(ACTION_SHOW_TEXTINFO)) {
				String info = intent.getStringExtra("info");
				int max = intent.getIntExtra("max", 0);
				int progress = intent.getIntExtra("progress", 100);
				//mInfoText.setText(info);
				//mInfoProgress.setMax(max);
				//mInfoProgress.setProgress(progress);

				if (info == null) {
                    /* Cancel any upcoming visibility change */
					mHandler.removeMessages(ACTIVITY_SHOW_INFOLAYOUT);
					//mInfoLayout.setVisibility(View.GONE);
				}
				else {
                    /* Slightly delay the appearance of the progress bar to avoid unnecessary flickering */
					if (!mHandler.hasMessages(ACTIVITY_SHOW_INFOLAYOUT)) {
						Message m = new Message();
						m.what = ACTIVITY_SHOW_INFOLAYOUT;
						mHandler.sendMessageDelayed(m, 300);
					}
				}
			} else if (action.equalsIgnoreCase(ACTION_SHOW_PLAYER)) {
				showAudioPlayer();
			}
			if (action.equalsIgnoreCase(AudioService.ACTION_SHOW_HIDE_MINI_PLAYER)) {
				setupMiniPlayer();
			}

		}
	};

	private static class MainActivityHandler extends WeakHandler<DrawerActivity> {
		public MainActivityHandler(DrawerActivity owner) {
			super(owner);
		}

		@Override
		public void handleMessage(Message msg) {
			DrawerActivity ma = getOwner();
			if(ma == null) return;

			switch (msg.what) {
				case ACTIVITY_SHOW_INFOLAYOUT:
					//ma.mInfoLayout.setVisibility(View.VISIBLE);
					break;
			}
		}
	};

	public static void showProgressBar() {
		Intent intent = new Intent();
		intent.setAction(ACTION_SHOW_PROGRESSBAR);
		VLCApplication.getAppContext().sendBroadcast(intent);
	}

	public static void hideProgressBar() {
		Intent intent = new Intent();
		intent.setAction(ACTION_HIDE_PROGRESSBAR);
		VLCApplication.getAppContext().sendBroadcast(intent);
	}

	public static void sendTextInfo(String info, int progress, int max) {
		Intent intent = new Intent();
		intent.setAction(ACTION_SHOW_TEXTINFO);
		intent.putExtra("info", info);
		intent.putExtra("progress", progress);
		intent.putExtra("max", max);
		VLCApplication.getAppContext().sendBroadcast(intent);
	}

	public static void clearTextInfo() {
		sendTextInfo(null, 0, 100);
	}

	/**
	 * Show the audio player.
	 */
	public void showAudioPlayer() {
		// Open the pane only if is entirely opened.
		/*if (mSlidingPane.getState() == mSlidingPane.STATE_OPENED_ENTIRELY)
			mSlidingPane.openPane();
		mAudioPlayerFilling.setVisibility(View.VISIBLE);*/
		/*Intent intent = new Intent(this, DrawerActivity.class);
		intent.putExtra("parent", true);
		intent.putExtra("title", "Player");
		intent.putExtra("layout", MasterActivity.LAYOUT_PLAYER);
		intent.putExtra("activity", R.layout.activity_drawer);
		startActivity(intent);*/
	}

	public void hideAudioPlayer() {
		/*mSlidingPane.openPaneEntirely();
		mAudioPlayerFilling.setVisibility(View.GONE);*/
	}

	public void addMediaToDB(){
		String location;
		long time;
		long length;
		int type;
		Bitmap picture;
		String title;
		String artist;
		String genre;
		String album;
		String albumArtist;
		int width;
		int height;
		String artworkURL;
		int audio;
		int spu;
		int trackNumber;

		List <Media> alMedia = new ArrayList<Media>();

		MediaList mediaList = null;

		mediaList = mlibVLC.getMediaList();

		Media m;

		MediaDatabase mDB = MediaDatabase.getInstance();


		location = mFilePath;
		time = 0;
		length = 0;
		type = -1;
		picture = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.background_login);
		title = "JUDULLLLLL";
		artist = "ARTISSSS";
		genre = "";
		album = "ALBUUUM";
		albumArtist = "ALBUM ARTISSSS";
		width = 0;
		height = 0;
		artworkURL = "";
		audio = 0;
		spu = 0;
		trackNumber = 0;

		m = new Media(location, time, length, type,
				picture, title, artist, genre, album, albumArtist,
				width, height, artworkURL, audio, spu, trackNumber);


		mDB.addMedia(m);

		location = mFilePath2;
		time = 0;
		length = 0;
		type = -1;
		picture = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.wallpaper);
		title = "JUD 222";
		artist = "ART222";
		genre = "";
		album = "ALBU222";
		albumArtist = "ALBUM ARTISS2222";
		width = 0;
		height = 0;
		artworkURL = "";
		audio = 0;
		spu = 0;
		trackNumber = 0;

		m = new Media(location, time, length, type,
				picture, title, artist, genre, album, albumArtist,
				width, height, artworkURL, audio, spu, trackNumber);


		mDB.addMedia(m);



	}

	public void loadMediaQueue(){
		/*String location;
		long time;
		long length;
		int type;
		Bitmap picture;
		String title;
		String artist;
		String genre;
		String album;
		String albumArtist;
		int width;
		int height;
		String artworkURL;
		int audio;
		int spu;
		int trackNumber;

		List <Media> alMedia = new ArrayList<Media>();

		//MediaList mediaList = null;

		//mediaList = mlibVLC.getMediaList();

		Media m;

		MediaDatabase mDB = MediaDatabase.getInstance();


		location = mFilePath;
		time = 0;
		length = 0;
		type = -1;
		picture = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.background_login);
		title = "JUDULLLLLL";
		artist = "ARTISSSS";
		genre = "";
		album = "ALBUUUM";
		albumArtist = "ALBUM ARTISSSS";
		width = 0;
		height = 0;
		artworkURL = "http://ecx.images-amazon.com/images/I/61RKZnb8kBL.jpg";
		audio = 0;
		spu = 0;
		trackNumber = 0;

		m = new Media(location, time, length, type,
				picture, title, artist, genre, album, albumArtist,
				width, height, artworkURL, audio, spu, trackNumber);


		mDB.addMedia(m);
		alMedia.add(m);

		location = mFilePath2;
		time = 0;
		length = 0;
		type = -1;
		picture = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.wallpaper);
		title = "JUD 222";
		artist = "ART222";
		genre = "";
		album = "ALBU222";
		albumArtist = "ALBUM ARTISS2222";
		width = 0;
		height = 0;
		artworkURL = "http://static.asiawebdirect.com/m/phuket/portals/thaiwave-com/homepage/mae-hong-son/pagePropertiesImage/mae-hong-son.jpg";
		audio = 0;
		spu = 0;
		trackNumber = 0;

		m = new Media(location, time, length, type,
				picture, title, artist, genre, album, albumArtist,
				width, height, artworkURL, audio, spu, trackNumber);


		mDB.addMedia(m);

		alMedia.add(m);
		System.out.println("]]]]]]]]]]]]]]]]]]]]]load media");
		mAudioController.loadMedia(alMedia);

		*//**//*System.out.println("]]]]]]]]]]]]]]]]]]]]]]]reload ueue");
		mAudioController.reloadQueue();*//**//*

		List<String> mediaLocation = new ArrayList<String>();
		for (int i =0; i< alMedia.size();i++){
			mediaLocation.add(alMedia.get(i).getLocation());
		}

		mAudioController.load(mediaLocation,0);*/
		new getQueueArray().execute();

	}

	private class getQueueArray extends AsyncTask<Void, Void, Void> {
		String location;
		long time;
		long length;
		int type;
		Bitmap picture;
		String title;
		String artist;
		String genre;
		String album;
		String albumArtist;
		int width;
		int height;
		String artworkURL;
		int audio;
		int spu;
		int trackNumber;

		List <Media> alMedia = new ArrayList<Media>();

		//MediaList mediaList = null;

		//mediaList = mlibVLC.getMediaList();

		Media m;

		MediaDatabase mDB = MediaDatabase.getInstance();
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(mContext);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance


			ServiceQueueJson sh = new ServiceQueueJson();

			String url = "http://www.1071klitefm.com/apis/data/lists/pop-indonesia";

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceQueueJson.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					//JSONObject jsonObj = new JSONObject(jsonStr);
					JSONArray mJsonArray = new JSONArray(jsonStr);

					// looping through All Contacts
					for (int i = 0; i < mJsonArray.length(); i++) {
						//JSONObject mJsonObjectProperty = mJsonArray.getJSONObject(i);
						JSONObject mJsonObject = mJsonArray.getJSONObject(i);
						/*String id = mJsonObject.getString("id");
						String title = mJsonObject.getString("title");
						String album = mJsonObject.getString("post_date");
						String file = mJsonObject.getString("file");
						String cover = mJsonObject.getString("attachment");

						// adding contact to contact list
						playlists.add(new Playlist(title, album,id,cover,file, id ));*/

						location = mJsonObject.getString("file");
						time = 0;
						length = 0;
						type = -1;
						picture = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.background_login);
						title = mJsonObject.getString("title");
						artist = mJsonObject.getString("title");
						genre = "";
						album = mJsonObject.getString("title");
						albumArtist = mJsonObject.getString("title");
						width = 0;
						height = 0;
						artworkURL = mJsonObject.getString("attachment");
						audio = 0;
						spu = 0;
						trackNumber = 0;

						m = new Media(location, time, length, type,
								picture, title, artist, genre, album, albumArtist,
								width, height, artworkURL, audio, spu, trackNumber);

						mDB.addMedia(m);
						alMedia.add(m);

					}

					mAudioController.loadMedia(alMedia);


					List<String> mediaLocation = new ArrayList<String>();
					for (int i =0; i< alMedia.size();i++){
						mediaLocation.add(alMedia.get(i).getLocation());
					}
					mAudioController.load(mediaLocation,0);

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			Intent intent = new Intent(mContext, DrawerActivity.class);
			intent.putExtra("parent", true);
			intent.putExtra("title", "Player");
			intent.putExtra("layout", MasterActivity.LAYOUT_PLAYER);
			intent.putExtra("activity", R.layout.activity_drawer);
			startActivity(intent);
			finish();


		}

	}

	private final Handler handler = new Handler();
	private void setupHandler() {
		handler.removeCallbacks(sendToUi);
		handler.postDelayed(sendToUi, 1000); // 1 second
	}


	private Runnable sendToUi = new Runnable() {
		public void run() {

			//setupMiniPlayer();
			txtJudul.setText(mAudioController.getTitle());
			txtArtist.setText(mAudioController.getArtist());

			imgCoverMini.setImageBitmap(mAudioController.getCover());

			if(!mAudioController.hasNext()){
				btnNext.setVisibility(View.INVISIBLE);
			}
			else{
				btnNext.setVisibility(View.VISIBLE);
			}

			if(mAudioController.isPlaying()){
				btnPlay.setImageResource(R.drawable.ic_pause_white_36dp);
			}
			else{
				btnPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
			}
			handler.postDelayed(this, 1000); // 2 seconds
		}
	};




}
