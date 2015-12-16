package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;

/**
 * Created by gilang on 26/10/2015.
 */
public class AccountSettingFragment extends Fragment {

	public AccountSettingFragment(){}

	public static AccountSettingFragment newInstance(){
		AccountSettingFragment fragment = new AccountSettingFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_setting_account, parent, false);
		return v;
	}

}
