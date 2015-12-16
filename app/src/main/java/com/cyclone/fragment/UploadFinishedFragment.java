package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;

/**
 * Created by gilang on 10/12/2015.
 */
public class UploadFinishedFragment extends Fragment {

	public UploadFinishedFragment(){}

	public static UploadFinishedFragment newInstance(){
		UploadFinishedFragment fragment = new UploadFinishedFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_upload_finished, parent, false);

		return v;
	}
}
