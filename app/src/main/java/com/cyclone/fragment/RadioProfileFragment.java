package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.custom.SnapGestureListener;

/**
 * Created by gilang on 09/10/2015.
 */
public class RadioProfileFragment extends Fragment {

	private Button btnMoreProgram;
	private Button btnMoreAnnouncer;
	private NestedScrollView nestedScrollView;
	private DrawerActivity activity;
	private GestureDetectorCompat gd;

	public RadioProfileFragment(){}

	public static RadioProfileFragment newInstance(){
		RadioProfileFragment fragment = new RadioProfileFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_radio_profile, parent, false);

		nestedScrollView = (NestedScrollView) v.findViewById(R.id.nested_scroll_view);
		if(activity != null){
			gd = new GestureDetectorCompat(activity, new SnapGestureListener(activity));
			nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					System.out.println("touch recycler");
					if(nestedScrollView.computeVerticalScrollOffset() == 0)
						return gd.onTouchEvent(event);
					return false;
				}
			});
		}


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
		return v;
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
			try{
				parallaxHeader.removeAllViews();
				parallaxHeader.addView(header);
			}
			catch (Exception e){}

		}
	}
}
