package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyclone.R;

/**
 * Created by gilang on 07/11/2015.
 */
public class SimpleImageFragment extends Fragment {

	private ImageView image;
	private String imgUrl;

	public SimpleImageFragment(){}

	public static SimpleImageFragment newInstance(String imgUrl){
		SimpleImageFragment fragment = new SimpleImageFragment();
		fragment.imgUrl = imgUrl;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_simple_image, parent, false);

		image = (ImageView) v.findViewById(R.id.img);
		image.setImageResource(R.drawable.wallpaper);

		return v;
	}
}
