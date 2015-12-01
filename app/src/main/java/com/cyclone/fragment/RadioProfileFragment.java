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


	ImageView cover1, cover2, imgDj1,imgDj2,imgDj3;

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
				i.putExtra("layout", DrawerActivity.LAYOUT_PROGRAMS);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Programs");
				startActivity(i);
			}
		});
		btnMoreAnnouncer = (Button) v.findViewById(R.id.btn_more_announcer);
		btnMoreAnnouncer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_ANNOUNCERS);
				i.putExtra("activity", R.layout.activity_drawer_standard);
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

		cover1 = (ImageView) v.findViewById(R.id.img_cover);
		cover2 = (ImageView) v.findViewById(R.id.img_cover2);
		imgDj1 = (ImageView) v.findViewById(R.id.img_announcer);
		imgDj2 = (ImageView) v.findViewById(R.id.img_announcer2);
		imgDj3 = (ImageView) v.findViewById(R.id.img_announcer3);


		cover1.setImageResource(R.drawable.aa_the_dandless);
		cover2.setImageResource(R.drawable.aa_desta_gina);

		imgDj1.setImageResource(R.drawable.aaa_dimas_danang);
		imgDj2.setImageResource(R.drawable.aaa_darto);
		imgDj3.setImageResource(R.drawable.aaa_desta);



		cardProgram1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_PROGRAM_PAGE);
				i.putExtra("activity", R.layout.activity_drawer);
				i.putExtra("title", "Hit the Beat");
				if(Build.VERSION.SDK_INT >= 16) {
					ImageView imageView = (ImageView) cardProgram1.findViewById(R.id.img_cover);
					imageView.setImageDrawable(cover1.getDrawable());
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
				i.putExtra("layout", DrawerActivity.LAYOUT_PROGRAM_PAGE);
				i.putExtra("activity", R.layout.activity_drawer);
				i.putExtra("title", "Hit the Beat");
				if(Build.VERSION.SDK_INT >= 16) {
					ImageView imageView = (ImageView) cardProgram2.findViewById(R.id.img_cover2);
					imageView.setImageDrawable(cover2.getDrawable());
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
