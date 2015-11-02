package com.cyclone.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyclone.R;
import com.cyclone.custom.Tools;

/**
 * Created by gilang on 25/10/2015.
 */
public class VirtualCardFragment extends Fragment {

	public VirtualCardFragment(){}

	public static VirtualCardFragment newInstance(){
		VirtualCardFragment fragment = new VirtualCardFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_virtual_card, parent, false);
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
			View header = inflater.inflate(R.layout.part_header_virtual_card, parallaxHeader,
					false);
			setupBackground(header);
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}

	public void setupBackground(View header){
		ImageView imgHeader = (ImageView) header.findViewById(R.id.img_header_background);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_login);
		bitmap = Tools.blur(bitmap, 0.5f, 10);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int x = width / 8;
		int y = height / 8;
		int newWidth = width * 6 / 8;
		int newHeight = height * 6 / 8;
		bitmap = Bitmap.createBitmap(bitmap, x, y, newWidth, newHeight);
		imgHeader.setImageBitmap(bitmap);
	}
}
