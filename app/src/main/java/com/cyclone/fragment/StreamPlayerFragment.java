package com.cyclone.fragment;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.wunderlist.slidinglayer.SlidingLayer;

import java.util.ArrayList;

/**
 * Created by gilang on 07/11/2015.
 */
public class StreamPlayerFragment extends Fragment {

	public static final int STATE_PLAYING = 100;
	public static final int STATE_STOP = 101;
	public static int state;
	private DrawerActivity activity;
	private ImageButton btnMinimize, btnRepeat, btnPrevious, btnPlay, btnNext, btnShuffle, btnMenu;
	private ViewGroup groupInfo, groupControl;
	private ViewGroup btnArtist, btnAlbum;
	private ImageView imgCover, imgTemp;
	private View minimizedPlayer;
	private TextView txtTitle, txtArtist, txtTotalTime;
	private SlidingLayer slidingLayer;
	private SwipeRefreshLayout swipeLayout;

	public StreamPlayerFragment(){}

	public static StreamPlayerFragment newInstance(){
		StreamPlayerFragment fragment = new StreamPlayerFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = new View(parent.getContext());
		return v;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		SharedPreferences pref = context.getSharedPreferences(getString(R.string
				.preference_key), Context.MODE_PRIVATE);
		state = pref.getInt("state", STATE_STOP);

		System.out.println("attached player");
		if(context instanceof DrawerActivity){
			activity = (DrawerActivity) context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.fragment_player, parallaxHeader,
					false);
			bindHeaderView(header);
			minimizedPlayer = activity.findViewById(R.id.minimized_player);
			minimizedPlayer.setVisibility(View.GONE);
			System.out.println("hide mini player");
			parallaxHeader.addView(header);
		}
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

		if(state == STATE_PLAYING)
			btnPlay.setImageResource(R.drawable.ic_pause_white_48dp);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (state == STATE_STOP) {
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
				editor.commit();
			}
		});

		btnRepeat.setEnabled(false);
		btnShuffle.setEnabled(false);
		btnNext.setEnabled(false);
		btnPrevious.setEnabled(false);

		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				slidingLayer.openLayer(true);
			}
		});

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

		imgCover.setImageResource(R.drawable.wallpaper);
		setPlayerColor();
	}

	@Override
	public void onDetach() {
		super.onDetach();
//		minimizedPlayer.setVisibility(View.VISIBLE);
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
}
