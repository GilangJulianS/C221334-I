package com.cyclone.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Playlist;
import com.cyclone.services.ServiceQueueJson;
import com.cyclone.services.ServiceStreamMusic;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 29/10/2015.
 */
public class PlayerFragment extends Fragment implements GestureDetector.OnGestureListener{

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private RecyclerView recyclerView;
	private List<Playlist> datas, persistentDatas;
	private UniversalAdapter adapter;
	private LinearLayoutManager layoutManager;
	private CollapseActivity activity;
	private GestureDetectorCompat gd;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ViewGroup btnArtist, btnAlbum;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime, txtCurTimePlaying;
	private SlidingLayer slidingLayer;
	private SwipeRefreshLayout swipeLayout;
	private SeekBar seekBar;
	private IntentFilter mIntentFilter;
	private Intent serviceIntent;

	private ProgressDialog pDialog;
	public static int counter = 0;
	public static final String mBroadcastStringAction = "com.cyclone.broadcast.string";


	List<Playlist> playlists;

	public PlayerFragment(){}

	public static PlayerFragment newInstance(){
		PlayerFragment fragment = new PlayerFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);



		bindView(v);

		serviceIntent = new Intent(getContext(), ServiceStreamMusic.class);

		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(mBroadcastStringAction);

		try {
			getActivity().registerReceiver(broadcastBufferReceiver, mIntentFilter);
		}
		catch (Exception e){}



		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);


		adapter = new UniversalAdapter(getActivity(), "");

		/*SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);*/
		/*recyclerView.setItemAnimator(slideAnimator);

		recyclerView.setAdapter(adapter);

		animate(datas.get(0));*/

		if(activity != null){
			gd = new GestureDetectorCompat(activity, this);
			recyclerView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					//System.out.println("touch recycler");


					if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && !activity.changing)
						return gd.onTouchEvent(event);
					else if (activity.changing)
						return true;
					return false;
				}
			});


		}



		return v;
	}

	public void bindView(View v){
		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

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
			activity.changing = true;
			activity.appBarLayout.setExpanded(true);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);

		SharedPreferences pref = context.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		/*datas = parse("");
		persistentDatas = new ArrayList<>();
		persistentDatas.addAll(datas);*/

		AppCompatActivity activity;
		if(context instanceof CollapseActivity){
			this.activity = (CollapseActivity) context;
		}
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_player, parallaxHeader,
					false);
			bindHeaderView(header);
			minimizedPlayer = activity.findViewById(R.id.minimized_player);
			minimizedPlayer.setVisibility(View.GONE);
			parallaxHeader.addView(header);
		}

		new getQueueArray().execute();
	}



	@Override
	public void onDetach() {
		super.onDetach();
		minimizedPlayer.setVisibility(View.VISIBLE);
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
		txtCurTimePlaying = (TextView) v.findViewById(R.id.txt_elapsed_time);
		groupInfo = (ViewGroup) v.findViewById(R.id.group_player_info);
		groupControl = (ViewGroup) v.findViewById(R.id.group_player_control);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		imgTemp = (ImageView) v.findViewById(R.id.img_temp);
		slidingLayer = (SlidingLayer) v.findViewById(R.id.sliding_layer);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		btnArtist = (ViewGroup) v.findViewById(R.id.btn_artist);
		btnAlbum = (ViewGroup) v.findViewById(R.id.btn_album);
		seekBar = (SeekBar) v.findViewById(R.id.seekbar);


		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state == STATE_STOP) {
					state = STATE_PLAYING;
					try {
						getActivity().registerReceiver(broadcastBufferReceiver, mIntentFilter);
					}
					catch (Exception e){}
					btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);
					startStreaming(v.getContext(), persistentDatas.get(counter % persistentDatas.size()).mp3);
				} else {
					state = STATE_STOP;
					btnPlay.setImageResource(R.drawable.ic_play_arrow_white_48dp);
					stopStreaming(v.getContext());
					try{
						getContext().unregisterReceiver(broadcastBufferReceiver);
					}
					catch (Exception e){}
				}
				SharedPreferences pref = getActivity().getSharedPreferences(getString(R.string
						.preference_key), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				editor.putInt("state", state);
				editor.commit();
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
				if(!activated) {
					btnRepeat.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnRepeat.setColorFilter(Color.WHITE);
					activated = false;
				}
			}
		});

		btnShuffle.setOnClickListener(new View.OnClickListener() {
			private boolean activated = false;
			@Override
			public void onClick(View v) {
				if(!activated) {
					btnShuffle.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));
					activated = true;
				}else{
					btnShuffle.setColorFilter(Color.WHITE);
					activated = false;
				}
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			//private int counter = 0;
			@Override
			public void onClick(View v) {

				counter++;

				if(counter > persistentDatas.size()-1){
					counter = 0;
				}

				new ImageLoadTask(persistentDatas.get(counter % persistentDatas.size()).cover, imgCover).execute();
				/*imgTemp.setImageDrawable(imgCover.getDrawable());
				if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}
				imgCover.setImageURI(Uri.parse(persistentDatas.get(counter % persistentDatas.size()).cover));*/


				//setPlayerColor();
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
//					showImage(groupInfo);
//					showImage(groupControl);
				}
				txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
				txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
				txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);

				if (state == STATE_PLAYING) {
					stopStreaming(v.getContext());
					startStreaming(v.getContext(), persistentDatas.get(counter % persistentDatas.size()).mp3);
				}



			}
		});

		btnPrevious.setOnClickListener(new View.OnClickListener() {
			//private int counter = 0;
			@Override
			public void onClick(View v) {

				counter--;
				if(counter < 0){
					counter = persistentDatas.size()-1;
				}

				new ImageLoadTask(persistentDatas.get(counter % persistentDatas.size()).cover, imgCover).execute();
				/*imgTemp.setImageDrawable(imgCover.getDrawable());
				*//*if (counter % 2 == 0) {
					imgCover.setImageResource(R.drawable.background_login);
				} else {
					imgCover.setImageResource(R.drawable.wallpaper);
				}*//*

				imgCover.setImageURI(Uri.parse(persistentDatas.get(counter % persistentDatas.size()).cover));
				setPlayerColor();*/
				if(Build.VERSION.SDK_INT >= 21) {
					showImage(imgCover);
//					showImage(groupInfo);
//					showImage(groupControl);
				}
				txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
				txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
				txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);

				if (state == STATE_PLAYING) {
					stopStreaming(v.getContext());
					startStreaming(v.getContext(), persistentDatas.get(counter % persistentDatas.size()).mp3);
				}

			}
		});

		btnMinimize = (ImageButton) v.findViewById(R.id.btn_minimize);
		btnMinimize.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (activity != null) {
					activity.changing = true;
					activity.appBarLayout.setExpanded(false);
				}
			}
		});
		/*imgCover.setImageResource(R.drawable.wallpaper);
		setPlayerColor();*/




		btnArtist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, CollapseActivity.class);
				i.putExtra("layout", CollapseActivity.LAYOUT_ARTIST);
				i.putExtra("title", "Artist Name");
				activity.startActivity(i);
			}
		});

		btnAlbum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, CollapseActivity.class);
				i.putExtra("layout", CollapseActivity.LAYOUT_ALBUM);
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
					r+=50;
					g+=50;
					b+=50;
					System.out.println("RGB : " + r + " " + g + " " + b);
					if(r > 255)
						r = 255;
					if(g > 255)
						g = 255;
					if(b > 255)
						b = 255;
					int lightColor = Color.rgb(r, g, b);
					groupInfo.setBackgroundColor(color);
					groupControl.setBackgroundColor(lightColor);
				}
			});
		}
		catch(Exception e){

		}

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		System.out.println("fling recycler: " + velocityX + " " + velocityY);
		AppBarLayout appBarLayout = activity.appBarLayout;
		if(Math.abs(velocityY) > 50){
			activity.changing = true;
			if(velocityY > 0){
				appBarLayout.setExpanded(true);
				System.out.println("expanded");
			}else if(velocityY < 0){
				appBarLayout.setExpanded(false);
				System.out.println("collapsed");
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

	private void animate(final Playlist playlist){
		final Handler handler = new Handler();
		final Playlist s = playlist;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(s);
				datas.remove(s);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animate(datas.get(0));
				}
			}
		}, 200);
	}

	public List<Playlist> parse(String json){
		/*playlists = new ArrayList<>();*/
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

		return playlists;
	}

	private class getQueueArray extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getContext());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance

			playlists = new ArrayList<>();

			ServiceQueueJson sh = new ServiceQueueJson();

			String url = "http://www.diradio.net/apis/data/lists/pop-indonesia";

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
						String id = mJsonObject.getString("id");
						String title = mJsonObject.getString("title");
						String album = mJsonObject.getString("post_date");
						String file = mJsonObject.getString("file");
						String cover = mJsonObject.getString("attachment");

						// adding contact to contact list
						playlists.add(new Playlist(title, album,id,cover,file ));

					}
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

			datas = parse("");
			persistentDatas = new ArrayList<>();
			persistentDatas.addAll(datas);

			SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
					DecelerateInterpolator());
			slideAnimator.setAddDuration(500);
			slideAnimator.setMoveDuration(500);
			recyclerView.setItemAnimator(slideAnimator);

			recyclerView.setAdapter(adapter);

			animate(datas.get(0));

			new ImageLoadTask(persistentDatas.get(counter % persistentDatas.size()).cover, imgCover).execute();
			txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
			txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
			txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);

		}

	}


	private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

		private String url;
		private ImageView imageView;

		public ImageLoadTask(String url, ImageView imageView) {
			this.url = url;
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			try {
				URL urlConnection = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlConnection
						.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				Bitmap myBitmap = BitmapFactory.decodeStream(input);
				return myBitmap;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			imageView.setImageBitmap(result);

			imgTemp.setImageDrawable(imageView.getDrawable());
			setPlayerColor();
		}

	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void showImage(final View v){

		// get the center for the clipping circle
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

	private BroadcastReceiver broadcastBufferReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent bufferIntent) {

			int modeBuffer = bufferIntent.getIntExtra("modeSendBuffer",-1);
			int finishPlaying = bufferIntent.getIntExtra("finishPlaying",-1);
			if(modeBuffer == ServiceStreamMusic.BRODCAST_FIRST_PLAY){
				progressPlayer(bufferIntent);
				System.out.println("recived Brodcast "+bufferIntent.getStringExtra("curPosisi")+"/"+bufferIntent.getStringExtra("durasi")+"  |"+bufferIntent.getStringExtra("buferProgres"));
			}
			else{
				Log.d("Brodcast", "tidak masuk");
			}

			if(finishPlaying == 1){
				next();
				System.out.println("Finished");
			}
			else{
				System.out.println("Playing");
			}
		}
	};
	private void progressPlayer(Intent bufferIntent) {
		String bufferValue = bufferIntent.getStringExtra("buffering");
		String durasi = bufferIntent.getStringExtra("durasi");
		String curPosisi = bufferIntent.getStringExtra("curPosisi");
		String bufferProgres = bufferIntent.getStringExtra("buferProgres");
		//int bufferIntValue = Integer.parseInt(bufferValue);

		Float buffr = Float.valueOf(bufferProgres);
		Float dur = Float.valueOf(durasi);
		Float showBuff1 = buffr/100*dur;
		int showBuff = Math.round(showBuff1);
		Log.i("recive", buffr+"/100"+"x"+dur+"="+showBuff);

		Log.i("bifer", ""+showBuff);

		txtTotalTime.setText(durasi);
		txtCurTimePlaying.setText(curPosisi);
		seekBar.setMax(Integer.valueOf(durasi));
		seekBar.setProgress(Integer.valueOf(curPosisi));
		seekBar.setSecondaryProgress(showBuff);

	}

	private void startStreaming(Context context, String url) {
		//stopStreaming(context);
		ServiceStreamMusic.URL_STREAM=url;
		try {
			context.startService(serviceIntent);
		} catch (Exception e) {
		}

	}

	private void stopStreaming(Context context) {
		try {
			context.stopService(serviceIntent);
			//UIisNotPlaying();
		} catch (Exception e) {

		}

	}

	private void next(){

		counter++;

		if(counter > persistentDatas.size()-1){
			counter = 0;
		}

		new ImageLoadTask(persistentDatas.get(counter % persistentDatas.size()).cover, imgCover).execute();

		if(Build.VERSION.SDK_INT >= 21) {
			showImage(imgCover);
		}
		txtTitle.setText(persistentDatas.get(counter % persistentDatas.size()).title);
		txtArtist.setText(persistentDatas.get(counter % persistentDatas.size()).artist);
		txtTotalTime.setText(persistentDatas.get(counter % persistentDatas.size()).duration);

		if (state == STATE_PLAYING) {
			stopStreaming(getContext());
			startStreaming(getContext(), persistentDatas.get(counter % persistentDatas.size()).mp3);
		}

	}
}
