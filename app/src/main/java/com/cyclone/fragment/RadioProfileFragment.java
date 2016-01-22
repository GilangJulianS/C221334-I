package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;

/**
 * Created by gilang on 09/10/2015.
 */
public class RadioProfileFragment extends Fragment {

	private Button btnMoreProgram;
	private Button btnMoreAnnouncer;
	private NestedScrollView nestedScrollView;
	private DrawerActivity activity;
	private GestureDetectorCompat gd;
	private CardView cardProgram1, cardProgram2;
	private CardView cardDj1, cardDj2, cardDj3;

	public RadioProfileFragment(){}

	public static RadioProfileFragment newInstance(){
		RadioProfileFragment fragment = new RadioProfileFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_radio_profile, parent, false);

		nestedScrollView = (NestedScrollView) v.findViewById(R.id.nested_scroll_view);
//		if(activity != null){
//			gd = new GestureDetectorCompat(activity, new SnapGestureListener(activity));
//			nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					System.out.println("touch recycler");
//					if(nestedScrollView.computeVerticalScrollOffset() == 0)
//						return gd.onTouchEvent(event);
//					return false;
//				}
//			});
//		}


		btnMoreProgram = (Button) v.findViewById(R.id.btn_more_program);
		btnMoreProgram.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PROGRAMS);
				i.putExtra("title", "Programs");
				startActivity(i);
			}
		});
		btnMoreAnnouncer = (Button) v.findViewById(R.id.btn_more_announcer);
		btnMoreAnnouncer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_ANNOUNCERS);
				i.putExtra("title", "DJs and Announcers");
				startActivity(i);
			}
		});

		bindViews(v);

		return v;
	}


	public void bindViews(View v){
		cardProgram1 = (CardView) v.findViewById(R.id.card_featured_program_1);
		cardProgram2 = (CardView) v.findViewById(R.id.card_featured_program_2);
		cardDj1 = (CardView) v.findViewById(R.id.card_featured_announcer_1);
		cardDj2 = (CardView) v.findViewById(R.id.card_featured_announcer_2);
		cardDj3 = (CardView) v.findViewById(R.id.card_featured_announcer_3);

		cardProgram1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PROGRAM_PAGE);
				i.putExtra("title", "Hit the Beat");
				if(Build.VERSION.SDK_INT >= 16) {
					ImageView imageView = (ImageView) cardProgram1.findViewById(R.id.img_cover);
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
							(activity, imageView, "program");
					activity.startActivity(i, options.toBundle());
				}else{
					activity.startActivity(i);
				}
			}
		});

		cardProgram2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PROGRAM_PAGE);
				i.putExtra("title", "Hit the Beat");
				if(Build.VERSION.SDK_INT >= 16) {
					ImageView imageView = (ImageView) cardProgram2.findViewById(R.id.img_cover);
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
							(activity, imageView, "program");
					activity.startActivity(i, options.toBundle());
				}else{
					activity.startActivity(i);
				}
			}
		});

		cardDj1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		cardDj2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		cardDj3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		if(context instanceof DrawerActivity) {
			activity = (DrawerActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_radio_profile, parallaxHeader,
					false);
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}
}
