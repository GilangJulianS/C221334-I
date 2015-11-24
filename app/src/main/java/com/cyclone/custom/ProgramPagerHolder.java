package com.cyclone.custom;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.fragment.SimpleImageFragment;
import com.cyclone.model.ProgramPager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramPagerHolder extends UniversalHolder {

	public ViewPager viewPager;
	private CirclePageIndicator indicator;
	private CustomPagerAdapter adapter;

	public ProgramPagerHolder(View v, Activity activity) {
		super(v, activity);
		viewPager = (ViewPager) v.findViewById(R.id.view_pager);
		indicator = (CirclePageIndicator) v.findViewById(R.id.view_pager_indicator);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((ProgramPager) object, (AppCompatActivity)activity);
	}

	public void bind(ProgramPager p, AppCompatActivity activity){

		adapter = new CustomPagerAdapter(activity.getSupportFragmentManager());
		for(String imgUrl : p.imgUrls) {
			adapter.add(SimpleImageFragment.newInstance(imgUrl));
		}
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(1);
		indicator.setViewPager(viewPager);
	}
}
