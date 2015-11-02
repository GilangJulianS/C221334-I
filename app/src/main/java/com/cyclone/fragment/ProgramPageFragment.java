package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;


/**
 * Created by gilang on 11/10/2015.
 */
public class ProgramPageFragment extends Fragment {

	public ProgramPageFragment(){}

	public static ProgramPageFragment newInstance(){
		ProgramPageFragment fragment = new ProgramPageFragment();
		return fragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_program_page, parent, false);
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
			View header = inflater.inflate(R.layout.part_header_program_page, parallaxHeader,
					false);
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}
}
