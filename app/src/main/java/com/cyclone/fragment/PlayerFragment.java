package com.cyclone.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Playlist;
import com.cyclone.player.MediaDatabase;
import com.cyclone.player.audio.AudioServiceController;
import com.cyclone.player.audio.RepeatType;
import com.cyclone.player.interfaces.IAudioPlayer;
import com.cyclone.player.util.Strings;
import com.cyclone.player.util.VLCInstance;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.videolan.libvlc.LibVlcException;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends RecyclerFragment implements IAudioPlayer, RecyclerView.OnClickListener{
	public static final String TAG = "VLC/AudioPlayer";

	private String mFilePath = "http://stream.suararadio.com/bloom-mae.mp3";
	private String mFilePath2 = "http://stream.suararadio.com/the-ballad-of-distant-love.mp3";
	private String mFilePath3 = "http://stream.suararadio.com/the-balloonist.mp3";
	private String mFilePath4 = "http://stream.suararadio.com/the-knife.mp3";
	private String mFilePath5 = "http://stream.suararadio.com/this-bed.mp3";
	private String mFilePath6 = "http://stream.suararadio.com/took-my-soul.mp3";

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private List<Object> persistentDatas;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ViewGroup btnArtist, btnAlbum;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime, txtCurTimePlaying;
	private SlidingLayer slidingLayer;
	private SwipeRefreshLayout swipeLayout;
	private SeekBar seekBar;

	private RecyclerView recyclerView;
	private Activity mactivity;

	private AudioServiceController mAudioController;
	private boolean mShowRemainingTime = false;

	public PlayerFragment(){}

	public static PlayerFragment newInstance(String json){
		PlayerFragment fragment = new PlayerFragment();
		fragment.json = json;
		return fragment;
	}


	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return R.layout.part_header_player;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAudioController = AudioServiceController.getInstance();
		mAudioController.bindAudioService(getContext());

		mactivity = getActivity();

		getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		/*try {
			mLibVLC = VLCInstance.getLibVlcInstance();
		} catch (LibVlcException e) {
			e.printStackTrace();
		}*/



		//List<String> mediaLocation = new ArrayList<String>();
		//mediaLocation.add(mFilePath);
		//mAudioController.load(mediaLocation, 0);

		//MediaDatabase mdb = MediaDatabase.getInstance();
		//System.out.println(""+mdb.getMedia(mFilePath).getTitle());
		//loadFromDB();
		setupHandler();

	}


	@Override
	public void prepareHeader(View v) {
		bindHeaderView(v);
		SharedPreferences pref = activity.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		persistentDatas = parse(json);
		minimizedPlayer = activity.findViewById(R.id.minimized_player);
		minimizedPlayer.setVisibility(View.GONE);
		setPlayerColor();
		update();



	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.player, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.btn_collapse){
			activity.appBarLayout.setExpanded(true);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();

		handler.removeCallbacks(sendToUi);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(sendToUi);
	}

	@Override
	public void onResume() {
		super.onResume();
		update();
		setupHandler();
	}

	public void bindHeaderView(View v){
		btnRepeat = (ImageButton) v.findViewById(R.id.btn_repeat);
		btnPrevious = (ImageButton) v.findViewById(R.id.btn_previous);
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
		btnNext = (ImageButton) v.findViewById(R.id.btn_next);
		btnShuffle = (ImageButton) v.findViewById(R.id.btn_shuffle);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		txtArtist = (TextView) v.findViewById(R.id.txt_artist);
		txtTotalTime = (TextView) v.findViewById(R.id.txt_total_time);
		groupInfo = (ViewGroup) v.findViewById(R.id.group_player_info);
		groupControl = (ViewGroup) v.findViewById(R.id.group_player_control);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		imgTemp = (ImageView) v.findViewById(R.id.img_temp);
		slidingLayer = (SlidingLayer) v.findViewById(R.id.sliding_layer);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		btnArtist = (ViewGroup) v.findViewById(R.id.btn_artist);
		btnAlbum = (ViewGroup) v.findViewById(R.id.btn_album);

		txtCurTimePlaying = (TextView) v.findViewById(R.id.txt_elapsed_time);

		seekBar = (SeekBar) v.findViewById(R.id.seekbar);

		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*if (state == STATE_STOP) {
					state = STATE_PLAYING;
					btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
				} else {
					state = STATE_STOP;
					btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
				}
				SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string
						.preference_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt("state", state);
				editor.commit();*/
				onPlayPauseClick(v);

				//mAudioController.playIndex(0);


				//Toast.makeText(getContext(),""+mAudioController.getArtist()+","+mLibVLC.getMediaList().getMedia(0).getArtist()+","+mLibVLC.getMediaList().getMedia(0).getTitle(), Toast.LENGTH_LONG).show();
			}
		});


		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingLayer.openLayer(true);
			}
		});

		btnRepeat.setOnClickListener(new View.OnClickListener() {
			private boolean activated = false;
			@Override
			public void onClick(View v) {
				/*if(!activated) {
					btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnRepeat.setColorFilter(Color.WHITE);
					activated = false;
				}*/
				onRepeatClick(v);
			}
		});

		btnShuffle.setOnClickListener(new View.OnClickListener() {
			private boolean activated = false;
			@Override
			public void onClick(View v) {
				/*if(!activated) {
					btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnShuffle.setColorFilter(Color.WHITE);
					activated = false;
				}*/
				onShuffleClick(v);

			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			private int counter = 0;
			@Override
			public void onClick(View v) {
				/*imgTemp.setImageDrawable(imgCover.getDrawable());
				if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
				txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
				txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
				txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);
				counter++;*/
				onNextClick(v);
			}
		});

		btnPrevious.setOnClickListener(new View.OnClickListener() {
			private int counter = 0;
			@Override
			public void onClick(View v) {
				/*imgTemp.setImageDrawable(imgCover.getDrawable());
				if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
				txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
				txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
				txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);
				counter++;*/
				onPreviousClick(v);
			}
		});

		btnMinimize = (ImageButton) v.findViewById(R.id.btn_minimize);
		btnMinimize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity != null) {
					activity.appBarLayout.setExpanded(false);
				}
			}
		});
		imgCover.setImageResource(R.drawable.wallpaper);
		setPlayerColor();


		btnArtist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_ARTIST);
				i.putExtra("title", "Artist Name");
				activity.startActivity(i);
			}
		});

		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_ALBUM);
				i.putExtra("title", "Album Name");
				activity.startActivity(i);
			}
		});

		seekBar.setOnSeekBarChangeListener(mTimelineListner);

		update();

	}

	public void setPlayerColor(){
		Bitmap bitmap = ((BitmapDrawable)imgCover.getDrawable()).getBitmap();
		Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

			@Override
			public void onGenerated(Palette palette) {
				int color = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
				color = palette.getDarkVibrantColor(color);
				int r = (color >> 16) & 0xFF;
				int g = (color >> 8) & 0xFF;
				int b = (color >> 0) & 0xFF;
				System.out.println("RGB : " + r + " " + g + " " + b);
				r += 50;
				g += 50;
				b += 50;
				System.out.println("RGB : " + r + " " + g + " " + b);
				if (r > 255)
					r = 255;
				if (g > 255)
					g = 255;
				if (b > 255)
					b = 255;
				int lightColor = Color.rgb(r, g, b);
				groupInfo.setBackgroundColor(color);
				groupControl.setBackgroundColor(lightColor);
			}
		});
	}

	public List<Object> parse(String json){
		List<Object> playlists = new ArrayList<>();
		/*playlists.add(new Playlist("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Playlist("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Playlist("Drones", "Muse", "05:45"));
		playlists.add(new Playlist("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Playlist("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Playlist("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Playlist("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Playlist("Drones", "Muse", "05:45"));
		playlists.add(new Playlist("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Playlist("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Playlist("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Playlist("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Playlist("Drones", "Muse", "05:45"));
		playlists.add(new Playlist("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Playlist("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Playlist("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Playlist("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Playlist("Drones", "Muse", "05:45"));
		playlists.add(new Playlist("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Playlist("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));
		playlists.add(new Playlist("The Celestials", "The Smashing Pumpkins", "03:20"));
		playlists.add(new Playlist("Track 5 of 30 Playlist", "Morning Songs", "1:08:20"));
		playlists.add(new Playlist("Drones", "Muse", "05:45"));
		playlists.add(new Playlist("Extraordinary", "Clean Bandit", "04:48"));
		playlists.add(new Playlist("Heart Like Yours", "Willamette Willamette Willamette", "03:15"));*/

		/*List<Playlist> playlists = new ArrayList<>();*/

		MediaList mList = null;
		try {
			mList = VLCInstance.getLibVlcInstance().getMediaList();
		} catch (LibVlcException e) {
			e.printStackTrace();
		}

		for (int i = 0 ; i < mList.size(); i++){
			Media md = mList.getMedia(i);
			playlists.add(new Playlist(md.getArtist(), md.getTitle(),""+md.getLength()));
		}

		return playlists;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void showImage(final View v){

		// get the center for the clipping circle
		int cx = v.getWidth() / 2;
		int cy = v.getHeight() / 2;
		// get the final radius for the clipping circle
		int finalRadius = Math.max(v.getWidth(), v.getHeight());
		try{
			// create the animator for this view (the start radius is zero)
			Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);

			// make the view visible and start the animation
			v.setVisibility(View.VISIBLE);
			anim.start();
		}catch (Exception e){}
	}
	@Override
	public synchronized void update() {

		if (mAudioController == null)
			return;

		if (mAudioController.hasMedia()) {
			show();
		} else {
			hide();
			return;
		}
		changeCover();
		//mHeaderMediaSwitcher.updateMedia();
		//mCoverMediaSwitcher.updateMedia();

		FragmentActivity act = getActivity();

		if (mAudioController.isPlaying()) {
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
			btnPlay.setContentDescription(getString(R.string.pause));

		} else {
			btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
			btnPlay.setContentDescription(getString(R.string.play));

		}
		if (mAudioController.isShuffling()) {
			btnShuffle.setImageResource(R.drawable.ic_shuffle_white_48dp);
			btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
		} else {
			btnShuffle.setImageResource(R.drawable.ic_shuffle_white_48dp);
			btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
		}
		switch(mAudioController.getRepeatType()) {
			case None:
				btnRepeat.setImageResource(R.drawable.ic_repeat_white_48dp);
				btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));
				break;
			case Once:
				btnRepeat.setImageResource(R.drawable.ic_repeat_one_white_48dp);
				btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
				break;
			default:
			case All:
				btnRepeat.setImageResource(R.drawable.ic_repeat_white_48dp);
				btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
				break;
		}
		if (mAudioController.hasNext())
			btnNext.setVisibility(ImageButton.VISIBLE);
		else
			btnNext.setVisibility(ImageButton.INVISIBLE);
		if (mAudioController.hasPrevious())
			btnPrevious.setVisibility(ImageButton.VISIBLE);
		else
			btnPrevious.setVisibility(ImageButton.INVISIBLE);


		try{
			txtTitle.setText(mAudioController.getTitle());
			txtArtist.setText(mAudioController.getArtist());
		}
		catch (Exception e){}


		updateList();
	}

	@Override
	public synchronized void updateProgress() {

			/*int time = (int) mLibVLC.getTime();
			int length = (int) mLibVLC.getLength();

			txtCurTimePlaying.setText(Strings.millisToString(time));
			txtTotalTime.setText(Strings.millisToString(length));
			seekBar.setMax(length);
			seekBar.setMax(length);*/

		update();



	}

	private void updateList() {
		/*ArrayList<Media> audioList = new ArrayList<Media>();
		String currentItem = null;
		int currentIndex = -1;

		LibVLC libVLC = LibVLC.getExistingInstance();
		for (int i = 0; i < libVLC.getMediaList().size(); i++) {
			audioList.add(libVLC.getMediaList().getMedia(i));
		}
		currentItem = mAudioController.getCurrentMediaLocation();

		//mSongsListAdapter.clear();

		for (int i = 0; i < audioList.size(); i++) {
			Media media = audioList.get(i);
			if (currentItem != null && currentItem.equals(media.getLocation()))
				currentIndex = i;
			//mSongsListAdapter.add(media);
		}*/
		//mSongsListAdapter.setCurrentIndex(currentIndex);
		//mSongsList.setSelection(currentIndex);

		//mSongsListAdapter.notifyDataSetChanged();
	}

	SeekBar.OnSeekBarChangeListener mTimelineListner = new SeekBar.OnSeekBarChangeListener() {
		int pros = 0;
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			mAudioController.setTime(pros);
			txtCurTimePlaying.setText(Strings.millisToString(mShowRemainingTime ? pros - mAudioController.getLength() : pros));
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar sb, int prog, boolean fromUser) {
			if (fromUser) {
				pros = prog;
				txtCurTimePlaying.setText(Strings.millisToString(prog));
			}
		}
	};

	public void onTimeLabelClick(View view) {
		mShowRemainingTime = !mShowRemainingTime;
		update();
	}

	public void onPlayPauseClick(View view) {
		if (mAudioController.isPlaying()) {
			mAudioController.pause();
		} else {
			mAudioController.play();

		}
		update();
	}

	public void onStopClick(View view) {
		mAudioController.stop();
	}

	public void onNextClick(View view) {
		mAudioController.next();
		/*imgTemp.setImageDrawable(imgCover.getDrawable());
		imgCover.setImageBitmap(mAudioController.getCover());*/
		update();
	}

	public void onPreviousClick(View view) {
		mAudioController.previous();
		/*imgTemp.setImageDrawable(imgCover.getDrawable());
		imgCover.setImageBitmap(mAudioController.getCover());*/
		update();
	}

	public void onRepeatClick(View view) {
		switch (mAudioController.getRepeatType()) {
			case None:
				mAudioController.setRepeatType(RepeatType.All);
				break;
			case All:
				mAudioController.setRepeatType(RepeatType.Once);
				break;
			default:
			case Once:
				mAudioController.setRepeatType(RepeatType.None);
				break;
		}
		update();
	}

	public void onShuffleClick(View view) {
		mAudioController.shuffle();
		update();
	}

	public void showAdvancedOptions(View v) {
		//CommonDialogs.advancedOptions(getActivity(), v, MenuType.Audio);
	}

	public void show() {
		DrawerActivity activity = (DrawerActivity)getActivity();
		if (activity != null)
			activity.showAudioPlayer();
	}

	public void hide() {
		DrawerActivity activity = (DrawerActivity)getActivity();
		if (activity != null)
			activity.hideAudioPlayer();
	}



	public void addMusic(){
		String location;
		long time;
		long length;
		int type;
		int picture;
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

		//mediaList = mLibVLC.getMediaList();

		Media m;

		//MediaDatabase mDB = MediaDatabase.getInstance();


		location = mFilePath;
		time = 0;
		length = 0;
		type = -1;
		picture =  R.drawable.background_login;
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

		/*m = new Media(location, time, length, type,
		picture, title, artist, genre, album, albumArtist,
		width, height, artworkURL, audio, spu, trackNumber);*/

		//mDB.addMedia(m);

	}

	public void loadFromDB(){
		MediaDatabase mDB = MediaDatabase.getInstance();

		HashMap<String, Media> hashMapMediaList = mDB.getMedias();


		Iterator mykey = hashMapMediaList.keySet().iterator();
		//MediaList mediaList = mLibVLC.getMediaList();

		while (mykey.hasNext()){
			String key = (String)mykey.next();
			//	mediaList.add(hashMapMediaList.get(key));
		}
	}


	private void changeCover(){

		Bitmap bitmap = ((BitmapDrawable)imgCover.getDrawable()).getBitmap();
		if(bitmap != mAudioController.getCover()){
			try{
				imgTemp.setImageDrawable(imgCover.getDrawable());
				imgCover.setImageBitmap(mAudioController.getCover());
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
			}catch (Exception e){
				imgTemp.setImageDrawable(imgCover.getDrawable());
				imgCover.setImageResource(R.drawable.radio_icon);
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
				imgTemp.setImageResource(R.drawable.wallpaper);
			}


		}



	}


	private final Handler handler = new Handler();
	private void setupHandler() {
		handler.removeCallbacks(sendToUi);
		handler.postDelayed(sendToUi, 1000); // 1 second
	}


	private Runnable sendToUi = new Runnable() {
		public void run() {
			update();
			int time = mAudioController.getTime();
			int length = mAudioController.getLength();

			txtCurTimePlaying.setText(Strings.millisToString(time));
			txtTotalTime.setText(Strings.millisToString(length));
			seekBar.setMax(length);
			seekBar.setProgress(time);

			if (mAudioController.isPlaying()) {
				btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
				btnPlay.setContentDescription(getString(R.string.pause));

			} else {
				btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
				btnPlay.setContentDescription(getString(R.string.play));

			}

			if (mAudioController.hasNext())
				btnNext.setVisibility(ImageButton.VISIBLE);
			else
				btnNext.setVisibility(ImageButton.INVISIBLE);
			if (mAudioController.hasPrevious())
				btnPrevious.setVisibility(ImageButton.VISIBLE);
			else
				btnPrevious.setVisibility(ImageButton.INVISIBLE);

			if(mAudioController.hasMedia()){
				txtArtist.setText(mAudioController.getArtist());
				txtTitle.setText(mAudioController.getTitle());
			}
			else {
				txtArtist.setText("Artist");
				txtTitle.setText("Title");
			}

			handler.postDelayed(this, 1000); // 2 seconds
		}
	};


	@Override
	public void onClick(View v) {
		System.out.println("sfsadsdsdsdasdsad}}}}}}}}}}}}}}|}||}}}}}}}}}}}}}}}}|");
	}
}
