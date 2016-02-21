package com.cyclone.fragment;

import android.Manifest;
import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.model.Queue;
import com.cyclone.model.Song;
import com.cyclone.player.PlaybackService;
import com.cyclone.player.VLCApplication;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.interfaces.updateCoverFromUrl;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;
import com.cyclone.player.util.Strings;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends PlaybackServiceRecyclerFragment implements PlaybackService.Callback, updateCoverFromUrl, PlayOnHolder {

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private List<Object> persistentDatas;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ViewGroup btnArtist, btnAlbum, btnInfo, btnLyric, btnAddShowlist, btnAddFavorites, btnShare;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime, txtCurTime;
	private SeekBar seekbar;
	private boolean mPreviewingSeek = false;
	private MenuItem btnCollapse;
	private List<Queue> completeQueue;

	static PlayerFragment fragment;
	public PlayerFragment(){}

	Context mContext;

	public static PlayerFragment newInstance(String json){
		fragment = new PlayerFragment();
		fragment.json = json;
		fragment.completeQueue = new ArrayList<>();
		return fragment;
	}

	public static PlayerFragment getInstance(){
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
	public void prepareHeader(View v) {
		bindHeaderView(v);
		SharedPreferences pref = activity.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		persistentDatas = parse(json);
		minimizedPlayer = activity.findViewById(R.id.minimized_player);
		minimizedPlayer.setVisibility(View.GONE);

		if (mService != null)
			changeImage();
	}

	@Override
	public int getSlidingLayoutId() {
		return R.layout.menu_song;
	}

	@Override
	public void prepareSlidingMenu(View v) {
		btnArtist = (ViewGroup) v.findViewById(R.id.btn_artist);
		btnAlbum = (ViewGroup) v.findViewById(R.id.btn_album);
		btnInfo = (ViewGroup) v.findViewById(R.id.btn_info);
		btnAddFavorites = (ViewGroup) v.findViewById(R.id.btn_add_favorites);
		btnAddShowlist = (ViewGroup) v.findViewById(R.id.btn_add_showlist);
		btnShare = (ViewGroup) v.findViewById(R.id.btn_share);
		btnLyric = (ViewGroup) v.findViewById(R.id.btn_show_lyric);

		btnArtist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ARTIST);
				i.putExtra("title", "Artist Name");
				activity.startActivity(i);
			}
		});

		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ALBUM);
				i.putExtra("title", "Album Name");
				activity.startActivity(i);
			}
		});

		btnAddFavorites.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Add to favorites clicked", Toast.LENGTH_SHORT).show();
			}
		});

		btnAddShowlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("")
						.setTitle("Select type")
						.setPositiveButton("Add to mix", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(activity, "Add to mix clicked", Toast.LENGTH_SHORT).show();
							}
						})
						.setNegativeButton("Add to playlist", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(activity, "Add to playlist clicked", Toast.LENGTH_SHORT).show();
							}
						});
				builder.create().show();
			}
		});

		btnInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Info clicked", Toast.LENGTH_SHORT).show();
			}
		});

		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Share clicked", Toast.LENGTH_SHORT).show();
			}
		});

		btnLyric.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Lyric clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.btn_collapse){
			activity.appBarLayout.setExpanded(true);
		}
		return super.onOptionsItemSelected(item);
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
		txtCurTime = (TextView) v.findViewById(R.id.txt_elapsed_time);
		groupInfo = (ViewGroup) v.findViewById(R.id.group_player_info);
		groupControl = (ViewGroup) v.findViewById(R.id.group_player_control);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		imgTemp = (ImageView) v.findViewById(R.id.img_temp);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		seekbar = (SeekBar) v.findViewById(R.id.seekbar);

		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		seekbar.setPadding(0, 0, 0, 0);

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


			}
		});

		btnPlay.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				onStopClick(v);
				return true;
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
				Queue p = (Queue) persistentDatas.get(counter % persistentDatas.size());
				txtTitle.setText(p.title);
				txtArtist.setText(p.artist);
				txtTotalTime.setText(p.duration);
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
				Queue p = (Queue) persistentDatas.get(counter % persistentDatas.size());
				txtTitle.setText(p.title);
				txtArtist.setText(p.artist);
				txtTotalTime.setText(p.duration);
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
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ARTIST);
				i.putExtra("title", "Artist Name");
				activity.startActivity(i);
			}
		});

		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ALBUM);
				i.putExtra("title", "Album Name");
				activity.startActivity(i);
			}
		});
	}

	public void setPlayerColor(){
		try{
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
		}catch (Exception e){}

	}

	public List<Object> parse(String json){
		List<Object> playlists = new ArrayList<>();
		completeQueue = new ArrayList<>();
		/*playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20",0));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20",1));
		playlists.add(new Queue("Drones", "Muse", "05:45",2));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48",3));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15",4));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20",5));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20",6));
		playlists.add(new Queue("Drones", "Muse", "05:45",7));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48",8));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15",9));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20",10));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20",11));
		playlists.add(new Queue("Drones", "Muse", "05:45",12));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48",13));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15",14));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20",15));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20",16));
		playlists.add(new Queue("Drones", "Muse", "05:45",17));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48",18));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15",19));
		playlists.add(new Queue("The Celestials", "The Smashing Pumpkins", "03:20",20));
		playlists.add(new Queue("Track 5 of 30 Playlist", "Morning Songs", "1:08:20",21));
		playlists.add(new Queue("Drones", "Muse", "05:45",22));
		playlists.add(new Queue("Extraordinary", "Clean Bandit", "04:48",23));
		playlists.add(new Queue("Heart Like Yours", "Willamette Willamette Willamette", "03:15",24));*/
		MediaWrapperList mediaWrapperList;

		MediaWrapper mediaWrapper;
		if(mService != null){
			mediaWrapperList = mService.getMediaList();

			for(int i = 0; i< mediaWrapperList.size(); i++){
				mediaWrapper = mediaWrapperList.getMedia(i);
				playlists.add(new Queue(mediaWrapper.getTitle(), mediaWrapper.getArtist(), Strings.millisToString(mediaWrapper.getLength()), i));
				System.out.println("tampil di queue : "+mediaWrapper.getTitle());
			}
		}
		for(Object o : playlists){
			completeQueue.add((Queue)o);
		}
		return playlists;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void showImage(final View v){

		try{
			int cx = v.getWidth() / 2;
			int cy = v.getHeight() / 2;

			// get the final radius for the clipping circle
			int finalRadius = Math.max(v.getWidth(), v.getHeight());

			// create the animator for this view (the start radius is zero)
			Animator anim =
					ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);

			// make the view visible and start the animation
			v.setVisibility(View.VISIBLE);
			anim.start();
		}
		catch (Exception e){

		}

		// get the center for the clipping circle

	}

	@Override
	public void update() {
		if (mService == null || getActivity() == null)
			return;
		if(!mService.hasMedia()){
			System.out.println("NO MEDIA / STOPED");
			DrawerActivity.showMiniPlayer = true;
			DrawerActivity.setFragmentType(DrawerActivity.FRAGMENT_HOME);
			getActivity().finish();
			return;
		}

		/*if (mService.hasMedia() && !mService.isVideoPlaying()) {
			SharedPreferences mSettings= PreferenceManager.getDefaultSharedPreferences(getActivity());
			if (mSettings.getBoolean(PreferencesActivity.VIDEO_RESTORE, false)){
				Util.commitPreferences(mSettings.edit().putBoolean(PreferencesActivity.VIDEO_RESTORE, false));
				mService.switchToVideo();
				return;
			} else
				show();
		} else {
			hide();
			return;
		}

		mHeaderMediaSwitcher.updateMedia(mService);
		mCoverMediaSwitcher.updateMedia(mService);*/

		FragmentActivity act = getActivity();
		//mResumeToVideo.setVisibility(mService.getVideoTracksCount() > 0 ? View.VISIBLE : View.GONE);

		if (mService.isPlaying()) {
			//btnPlay.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_pause));
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
			btnPlay.setContentDescription(getString(R.string.pause));
			//mHeaderPlayPause.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_pause));
			//mHeaderPlayPause.setContentDescription(getString(R.string.pause));
		} else {
			//btnPlay.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_play));
			btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
			btnPlay.setContentDescription(getString(R.string.play));
			//mHeaderPlayPause.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_play));
			//mHeaderPlayPause.setContentDescription(getString(R.string.play));
		}
		if (mService.isShuffling()) {
			//btnShuffle.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_shuffle_on));
			btnShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
			btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));

			//btnShuffle.setContentDescription(getResources().getString(R.string.shuffle_on));
		} else {
			//btnShuffle.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_shuffle));
			btnShuffle.setImageResource(R.drawable.ic_shuffle_white_24dp);
			btnShuffle.setColorFilter(Color.WHITE);
			//btnShuffle.setContentDescription(getResources().getString(R.string.shuffle));
		}
		switch(mService.getRepeatType()) {
			case PlaybackService.REPEAT_NONE:
				//btnRepeat.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_repeat));
				btnRepeat.setImageResource(R.drawable.ic_repeat_white_24dp);
				btnRepeat.setColorFilter(Color.WHITE);
				//btnRepeat.setContentDescription(getResources().getString(R.string.repeat));
				break;
			case PlaybackService.REPEAT_ONE:
				//btnRepeat.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_repeat_one));
				btnRepeat.setImageResource(R.drawable.ic_repeat_one_white_24dp);
				btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
				//btnRepeat.setContentDescription(getResources().getString(R.string.repeat_single));
				break;
			default:
			case PlaybackService.REPEAT_ALL:
				//btnRepeat.setImageResource(UiTools.getResourceFromAttribute(act, R.attr.ic_repeat_on));
				btnRepeat.setImageResource(R.drawable.ic_repeat_white_24dp);
				btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
				//btnRepeat.setContentDescription(getResources().getString(R.string.repeat_all));
				break;
		}

		final List<String> mediaLocations = mService.getMediaLocations();
		//mShuffle.setVisibility(mediaLocations != null && mediaLocations.size() > 2 ? View.VISIBLE : View.INVISIBLE);
		seekbar.setOnSeekBarChangeListener(mTimelineListner);

		updateList();

		txtTitle.setText(mService.getArtist() + " - " + mService.getAlbum());
		txtArtist.setText(mService.getTitle());

		System.out.println("PLAYER UPDATE //////////////");


	}

	public void changeImage(){
		try{
			if(mService.getCover() == null){
				UrlImageViewHelper.loadUrlDrawable(getContext(), mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).getArtworkURL(), new UrlImageViewCallback() {
					@Override
					public void onLoaded(ImageView imageView, Bitmap bitmap, String s, boolean b) {
						imgTemp.setImageDrawable(imgCover.getDrawable());
						imgCover.setImageBitmap(bitmap);
						setPlayerColor();
						if(Build.VERSION.SDK_INT >= 21) {
							showImage(imgCover);
						}
					}
				});
			}
			else{
				imgTemp.setImageDrawable(imgCover.getDrawable());
				imgCover.setImageBitmap(mService.getCover());
				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
			}



		}catch (Exception e){}


	}

	@Override
	public void updateProgress() {
		if (mService == null)
			return;
		int time = (int) mService.getTime();
		int length = (int) mService.getLength();

		txtCurTime.setText(Strings.millisToString(time));
		txtTotalTime.setText(Strings.millisToString(length));
		seekbar.setProgress(time);
		seekbar.setMax(length);

		/*if(!mPreviewingSeek) {
			mTime.setText(Strings.millisToString(mShowRemainingTime ? time-length : time));
			mTimeline.setProgress(time);
			mProgressBar.setProgress(time);
		}*/

	}

	@Override
	public void onChanged(float percent) {
		super.onChanged(percent);
		System.out.println(percent);
		if(btnCollapse != null) {
			if (percent == 0) {
				btnCollapse.setVisible(false);
			} else if (percent == 100) {
				btnCollapse.setVisible(true);
			}
		}
	}

	@Override
	public void onMediaEvent(Media.Event event) {

	}

	@Override
	public void onMediaPlayerEvent(MediaPlayer.Event event) {
		switch (event.type) {
			case MediaPlayer.Event.Opening:
				changeImage();
				break;
			case MediaPlayer.Event.Stopped:
				//hide();
				System.out.println("STOPED");
				DrawerActivity.showMiniPlayer = true;
				super.getActivity().onBackPressed();
				break;
		}
	}


	public void updateList() {
		if (mService == null)
			return;

		/*int currentIndex = -1, oldCount = mPlaylistAdapter.getItemCount();
		if (mService == null)
			return;

		final List<MediaWrapper> previousAudioList = mPlaylistAdapter.getMedias();
		mPlaylistAdapter.clear();

		final List<MediaWrapper> audioList = mService.getMedias();
		final String currentItem = mService.getCurrentMediaLocation();

		if (audioList != null) {
			for (int i = 0; i < audioList.size(); i++) {
				final MediaWrapper media = audioList.get(i);
				if (currentItem != null && currentItem.equals(media.getLocation()))
					currentIndex = i;
				mPlaylistAdapter.add(media);
			}
		}
		mPlaylistAdapter.setCurrentIndex(currentIndex);
		int count = mPlaylistAdapter.getItemCount();
		if (oldCount != count)
			mPlaylistAdapter.notifyDataSetChanged();
		else
			mPlaylistAdapter.notifyItemRangeChanged(0, count);

		final int selectionIndex = currentIndex;
		if (!previousAudioList.equals(audioList))
			mSongsList.post(new Runnable() {
				@Override
				public void run() {
					mPlaylistAdapter.setCurrentIndex(selectionIndex);
				}
			});*/
	}

	SeekBar.OnSeekBarChangeListener mTimelineListner = new SeekBar.OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar sb, int prog, boolean fromUser) {
			if (fromUser && mService != null) {
				mService.setTime(prog);
				txtCurTime.setText(Strings.millisToString(prog));
				//mHeaderTime.setText(Strings.millisToString(prog));
			}
		}
	};

	public void onPlayPauseClick(View view) {
		if (mService == null)
			return;
		if (mService.isPlaying()) {
			mService.pause();
		} else {
			mService.play();
		}
	}

	public void onStopClick(View view) {
		if (mService != null)
			mService.stop();
	}

	public void onNextClick(View view) {
		if (mService == null)
			return;
		if (mService.hasNext())
			mService.next();
		else
			Snackbar.make(getView(), R.string.lastsong, Snackbar.LENGTH_SHORT).show();
	}

	public void onPreviousClick(View view) {
		if (mService == null)
			return;
		if (mService.hasPrevious())
			mService.previous();
		else
			Snackbar.make(getView(), R.string.firstsong, Snackbar.LENGTH_SHORT).show();
	}

	public void onRepeatClick(View view) {
		if (mService == null)
			return;

		switch (mService.getRepeatType()) {
			case PlaybackService.REPEAT_NONE:
				mService.setRepeatType(PlaybackService.REPEAT_ALL);
				break;
			case PlaybackService.REPEAT_ALL:
				mService.setRepeatType(PlaybackService.REPEAT_ONE);
				break;
			default:
			case PlaybackService.REPEAT_ONE:
				mService.setRepeatType(PlaybackService.REPEAT_NONE);
				break;
		}
		update();
	}

	public void onShuffleClick(View view) {
		if (mService != null)
			mService.shuffle();
		update();
	}

	@Override
	public void onConnected(PlaybackService service) {
		super.onConnected(service);
		System.out.println("service on player connected");
		mService.addCallback(this);
		//mPlaylistAdapter.setService(service);


		if(mService.hasMedia()){
			changeImage();
			setDataAfterService();
			//persistentDatas = parse(json);
		}

		else {
			mService.setVolume(0);
			mService.loadLastPlaylist(PlaybackService.TYPE_AUDIO);
			mService.pause();
			mService.setVolume(100);

			setDataAfterService();
			//addWithoutAnimate();


			//persistentDatas = parse(json);
		}

		update();
	}

	@Override
	public void onStop() {
        /* unregister before super.onStop() since mService is set to null from this call */
		if (mService != null)
			mService.removeCallback(this);
		super.onStop();
	}

	@Override
	public void onComplateLoadCoverUrl(Bitmap cover) {
		System.out.println("recived update cover");
		if(cover != null){
			/*try {
				imgTemp.setImageDrawable(imgCover.getDrawable());
			}catch (Exception e){
				try{
					imgTemp.setImageBitmap(mService.getCoverPrev());
				}
				catch (Exception q){
					imgTemp.setImageBitmap(cover);

				}
			}
				imgCover.setImageBitmap(cover);

				setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
				}
			//}catch (Exception e){
			//	System.out.println("bitmap gagal");
			//}*/

			//imgCover.setImageBitmap(cover);

		}
		else{
			System.out.println("cover null");
		}

	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {
		if(mService != null)
			mService.playIndex(position);
		else{
			System.out.println("onLoadedPlayOnHolder : service null");
		}
	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {

	}

	class LongSeekListener implements View.OnTouchListener {
		boolean forward;
		int normal, pressed;
		long length;

		public LongSeekListener(boolean forwards, int normalRes, int pressedRes) {
			this.forward = forwards;
			this.normal = normalRes;
			this.pressed = pressedRes;
			this.length = -1;
		}

		int possibleSeek;
		boolean vibrated;

		@RequiresPermission(Manifest.permission.VIBRATE)
		Runnable seekRunnable = new Runnable() {
			@Override
			public void run() {
				if(!vibrated) {
					((android.os.Vibrator) VLCApplication.getAppContext().getSystemService(Context.VIBRATOR_SERVICE))
							.vibrate(80);
					vibrated = true;
				}

				if(forward) {
					if(length <= 0 || possibleSeek < length)
						possibleSeek += 4000;
				} else {
					if(possibleSeek > 4000)
						possibleSeek -= 4000;
					else if (possibleSeek <= 4000)
						possibleSeek = 0;
				}

				txtCurTime.setText(Strings.millisToString(possibleSeek-length));
				seekbar.setProgress(possibleSeek);
				//mProgressBar.setProgress(possibleSeek);
				h.postDelayed(seekRunnable, 50);
			}
		};

		Handler h = new Handler();

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (mService == null)
				return false;
			switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					(forward ? btnNext : btnPrevious).setImageResource(this.pressed);

					possibleSeek = (int) mService.getTime();

					mPreviewingSeek = true;
					vibrated = false;
					length = mService.getLength();

					h.postDelayed(seekRunnable, 1000);
					return true;

				case MotionEvent.ACTION_UP:
					(forward ? btnNext : btnPrevious).setImageResource(this.normal);
					h.removeCallbacks(seekRunnable);
					mPreviewingSeek = false;

					if(event.getEventTime()-event.getDownTime() < 1000) {
						if(forward)
							onNextClick(v);
						else
							onPreviousClick(v);
					} else {
						if(forward) {
							if(possibleSeek < mService.getLength())
								mService.setTime(possibleSeek);
							else
								onNextClick(v);
						} else {
							if(possibleSeek > 0)
								mService.setTime(possibleSeek);
							else
								onPreviousClick(v);
						}
					}
					return true;
			}
			return false;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		fragment = null;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.player, menu);

		btnCollapse = menu.findItem(R.id.btn_collapse);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			UniversalAdapter newAdapter;

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText, newAdapter);
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String newText, UniversalAdapter newAdapter){
		if(!newText.equals("")) {
			newAdapter = new UniversalAdapter(activity);
			List<Queue> searchResult = search(newText);
			for(Queue q : searchResult){
				newAdapter.add(q);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Queue> search(String query){
		List<Queue> result = new ArrayList<>();
		for(Queue q : completeQueue){
			if(q.artist.toLowerCase().contains(query.toLowerCase()) || q.title.contains(query.toLowerCase())){
				result.add(q);
			}
		}
		return result;
	}

	/*public static PlaybackService getService(){
		return mService;
	}*/

}
