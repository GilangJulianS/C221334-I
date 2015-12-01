package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.DrawerActivity;
import com.cyclone.R;


/**
 * Created by gilang on 11/10/2015.
 */
public class ProgramPageFragment extends Fragment {

	private NestedScrollView nestedScrollView;
	private DrawerActivity activity;
	private GestureDetectorCompat gd;


	public ProgramPageFragment(){}

	public static ProgramPageFragment newInstance(){
		ProgramPageFragment fragment = new ProgramPageFragment();

		return fragment;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_program_page, parent, false);
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
			View header = inflater.inflate(R.layout.part_header_program_page, parallaxHeader,
					false);
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
	}
}
