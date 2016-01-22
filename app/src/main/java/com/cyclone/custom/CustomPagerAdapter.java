package com.cyclone.custom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

	List<Fragment> fragments;

	public CustomPagerAdapter(FragmentManager manager){
		super(manager);
		fragments = new ArrayList<>();
	}

	public void add(Fragment fragment){
		fragments.add(fragment);
	}

	public void add(Fragment fragment, int position){
		fragments.add(position, fragment);
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
}
