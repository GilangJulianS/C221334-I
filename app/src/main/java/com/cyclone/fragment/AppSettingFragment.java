package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;

/**
 * Created by gilang on 06/12/2015.
 */
public class AppSettingFragment extends Fragment {

	private String json;

	public AppSettingFragment(){}

	public static AppSettingFragment newInstance(String json){
		AppSettingFragment fragment = new AppSettingFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_setting_app, parent, false);

		return v;
	}
}
