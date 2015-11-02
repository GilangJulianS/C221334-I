package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.R;
import com.cyclone.StandardActivity;

/**
 * Created by gilang on 09/10/2015.
 */
public class RadioProfileFragment extends Fragment {

	private Button btnMoreProgram;
	private Button btnMoreAnnouncer;

	public RadioProfileFragment(){}

	public static RadioProfileFragment newInstance(){
		RadioProfileFragment fragment = new RadioProfileFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_radio_profile, parent, false);
		btnMoreProgram = (Button) v.findViewById(R.id.btn_more_program);
		btnMoreProgram.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_PROGRAMS);
				i.putExtra("title", "Programs");
				startActivity(i);
			}
		});
		btnMoreAnnouncer = (Button) v.findViewById(R.id.btn_more_announcer);
		btnMoreAnnouncer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_ANNOUNCERS);
				i.putExtra("title", "DJs and Announcers");
				startActivity(i);
			}
		});
		return v;
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		AppCompatActivity activity;
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
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
