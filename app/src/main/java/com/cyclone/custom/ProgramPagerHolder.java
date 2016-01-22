package com.cyclone.custom;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cyclone.R;
import com.cyclone.fragment.PagerFragment;
import com.cyclone.model.Program;
import com.cyclone.model.ProgramPager;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * Created by gilang on 07/11/2015.
 */
public class ProgramPagerHolder extends UniversalHolder {

	public ViewPager viewPager;
	private CirclePageIndicator indicator;
	private CustomPagerAdapter adapter;

	public ProgramPagerHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		viewPager = (ViewPager) v.findViewById(R.id.view_pager);
		indicator = (CirclePageIndicator) v.findViewById(R.id.view_pager_indicator);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((ProgramPager) object, (AppCompatActivity)activity);
	}

	public void bind(ProgramPager p, AppCompatActivity activity){

		adapter = new CustomPagerAdapter(activity.getSupportFragmentManager());
		int id = 0;
		for(Program program : p.programs) {
			adapter.add(PagerFragment.newInstance(program, id));
			id++;
		}
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(1);
		indicator.setViewPager(viewPager);
	}
}
