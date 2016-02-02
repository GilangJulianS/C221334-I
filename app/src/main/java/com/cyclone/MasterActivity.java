package com.cyclone;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.interfaces.getData;
import com.cyclone.model.RunningProgram;
import com.cyclone.player.PlaybackService;
import com.cyclone.player.gui.AudioPlayerContainerActivity;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.service.ServiceGetData;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gilang on 07/11/2015.
 */
public abstract class MasterActivity extends AudioPlayerContainerActivity implements GestureDetector
		.OnGestureListener, PlaybackService.Callback, getData {

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

	ImageView coverMiniPlayer;
	TextView txtTitle, txtArtist;
	ProgressBar progressBar;


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

		if (mService != null) {
			if(mService.isPlaying()){
				if(DrawerActivity.showMiniPlayer)
					miniPlayer.setVisibility(View.VISIBLE);
			}
			else{
				miniPlayer.setVisibility(View.GONE);
			}
		}
		else {
			miniPlayer.setVisibility(View.GONE);
		}
		btnMiniNext = (ImageButton) miniPlayer.findViewById(R.id.btn_next);
		btnMiniPlay = (ImageButton) miniPlayer.findViewById(R.id.btn_play);
		coverMiniPlayer =  (ImageView) miniPlayer.findViewById(R.id.coverMiniPlayer);
		txtArtist = (TextView) miniPlayer.findViewById(R.id.txt_artistMiniPlayer);
		txtTitle = (TextView) miniPlayer.findViewById(R.id.txtTitleMiniPlayer);
		progressBar = (ProgressBar) miniPlayer.findViewById(R.id.progressbar);
		txtTitle.setText("");
		txtArtist.setText("");
		progressBar.setVisibility(View.VISIBLE);
		coverMiniPlayer.setImageResource(R.drawable.radio_icon);
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
				if (mService == null)
					return;
				if (mService.hasNext())
					mService.next();
				else
					Snackbar.make(v, R.string.lastsong, Snackbar.LENGTH_SHORT).show();
			}
		});

		btnMiniPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mService == null)
					return;
				if (mService.isPlaying()) {
					mService.pause();
				} else {
					mService.play();
				}
			}
		});
		btnMiniPlay.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (mService != null)
					mService.stop();
				return false;
			}
		});
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
//					System.out.println("expanded blalbla");
				}
				else if(percent == 100) {
					isExpanded = false;
//					System.out.println("collapsed blalbla");
				}
				if(percent == 100 || percent == 0){
//					System.out.println("finish");
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

	@Override
	public void updateProgress() {

	}

	@Override
	public void update() {

		if (mService == null) {
			return;
		}
		if(!mService.hasMedia()){
			miniPlayer.setVisibility(View.GONE);
			return;
		}

		if (mService.isPlaying()) {
			if(DrawerActivity.showMiniPlayer)
				miniPlayer.setVisibility(View.VISIBLE);
			btnMiniPlay.setImageResource(R.drawable.ic_pause_white_36dp);
			btnMiniPlay.setContentDescription(getString(R.string.pause));
			progressBar.setVisibility(View.GONE);

		} else {
			btnMiniPlay.setImageResource(R.drawable.ic_play_arrow_white_36dp);
			btnMiniPlay.setContentDescription(getString(R.string.play));
		}
		if(mService.getCover() != null){
			coverMiniPlayer.setImageBitmap(mService.getCover());
		}
		else{
			UrlImageViewHelper.setUrlDrawable(coverMiniPlayer, mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).getArtworkURL(), R.drawable.radio_icon);
		}

		txtTitle.setText(mService.getTitle());
		txtArtist.setText(mService.getArtist() + " - " + mService.getAlbum());

		System.out.println("PLAYER UPDATE //////////////");

	}

	/*@Override
	public void onMediaEvent(Media.Event event) {

	}

	@Override
	public void onMediaPlayerEvent(MediaPlayer.Event event) {
		switch (event.type) {
			case MediaPlayer.Event.Opening:
				System.out.println("Opening ON MASTER ACTIVITY");
				if(DrawerActivity.showMiniPlayer)
					miniPlayer.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.VISIBLE);

				break;
			case MediaPlayer.Event.Stopped:
				//hide();
				System.out.println("STOPED ON MASTER ACTIVITY");
				miniPlayer.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				break;
			case MediaPlayer.Event.Playing:
				System.out.println("PLAYNG ON MASTER ACTIVITY");
				if(DrawerActivity.showMiniPlayer)
					miniPlayer.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				update();
				break;
		}

	}*/

	@Override
	public void onMediaEvent(Media.Event event) {

	}

	@Override
	public void onMediaPlayerEvent(MediaPlayer.Event event) {
		switch (event.type) {
			case MediaPlayer.Event.Opening:
				System.out.println("Opening ON MASTER ACTIVITY");
				if(DrawerActivity.showMiniPlayer)
					miniPlayer.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.VISIBLE);

				break;
			case MediaPlayer.Event.Stopped:
				//hide();
				System.out.println("STOPED ON MASTER ACTIVITY");
				miniPlayer.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
				break;
			case MediaPlayer.Event.Playing:
				System.out.println("PLAYNG ON MASTER ACTIVITY");
				if(DrawerActivity.showMiniPlayer)
					miniPlayer.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				update();
				break;
		}
	}

	@Override
	public void onConnected(PlaybackService service) {
		System.out.println("Service Conect on MasterActivity : retun to HomeFragment");

		mService = service;
		mService.addCallback(DrawerActivity.drawerActivity);
		update();

		if(mService.isPlaying() && mService.getCurrentMediaLocation() == ServerUrl.ulr_stream){
			System.out.println("looping");
			if(DrawerActivity.getFragmentType() != FRAGMENT_LIVE){
				System.out.println("is playing :"+mService.getCurrentMediaLocation());
				handler.postDelayed(runnable, 25000);
			}

		}
		else{

		}


		// AudioController.setPlabackService(service);
	}

	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			if(mService != null){
				if(mService.isPlaying() && mService.getCurrentMediaLocation() == ServerUrl.ulr_stream) {
					try {
						ServiceGetData serviceGetData = new ServiceGetData();
						serviceGetData.getDataStream(DrawerActivity.getmContext(), DrawerActivity.drawerActivity);
					}catch (Exception e){}
					handler.postDelayed(runnable, 25000);
				}
			}

		}


	};

	Handler handler = new Handler();

	@Override
	public void onDisconnected() {
		mService = null;
	}

	@Override
	public void onDataLoadedHome(Map<String, List> data) {

	}

	@Override
	public void onDataLoadedLiveStreaming(List<Object> data) {
		if(DrawerActivity.getFragmentType() != FRAGMENT_LIVE) {
			if(mService != null){
				if(mService.isPlaying() && mService.getCurrentMediaLocation() == ServerUrl.ulr_stream){
					RunningProgram program = UtilArrayData.program;
					MediaWrapper mMedia;
					MediaCustom MC;
					MediaDatabase mDB = MediaDatabase.getInstance();
					List<MediaWrapper> mw = new ArrayList<MediaWrapper>();
					MC = new MediaCustom();

					MC.setUri(Uri.parse(ServerUrl.ulr_stream));

					MC.setTitle(program.name);
					MC.setArtist(UtilArrayData.NAMA_RADIO);
					MC.setAlbum(program.description);
					MC.setAlbumArtist(UtilArrayData.NAMA_RADIO);
					MC.setArtworkURL("https://lh6.ggpht.com/cEwi4r2tcVC9neGWHxjt6ZLQ2TuAs_iPn3rL_YQAp4sZsit4dNHROrsH2Fk8gr94hlxw=w300");

					mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
							MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
							MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
							MC.getDiscNumber(), MC.getLastModified());
					try {
						mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).updateMeta(mMedia);
						mService.updateMetadataOnPlay();
					} catch (Exception e) {

					}
				}RunningProgram program = UtilArrayData.program;
				MediaWrapper mMedia;
				MediaCustom MC;
				MediaDatabase mDB = MediaDatabase.getInstance();
				List<MediaWrapper> mw = new ArrayList<MediaWrapper>();
				MC = new MediaCustom();

				MC.setUri(Uri.parse(ServerUrl.ulr_stream));

				MC.setTitle(program.name);
				MC.setArtist(UtilArrayData.NAMA_RADIO);
				MC.setAlbum(program.description);
				MC.setAlbumArtist(UtilArrayData.NAMA_RADIO);
				MC.setArtworkURL("https://lh6.ggpht.com/cEwi4r2tcVC9neGWHxjt6ZLQ2TuAs_iPn3rL_YQAp4sZsit4dNHROrsH2Fk8gr94hlxw=w300");

				mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
						MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
						MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
						MC.getDiscNumber(), MC.getLastModified());
				mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).updateMeta(mMedia);
				mService.updateMetadataOnPlay();
			}


		}
	}

	@Override
	public void onDataLoadedHomeCancel() {

	}


}
