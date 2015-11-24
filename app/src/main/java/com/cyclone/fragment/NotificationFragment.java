package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 25/10/2015.
 */
public class NotificationFragment extends RecyclerFragment {

	public NotificationFragment(){}

	public static NotificationFragment newInstance(String json){
		NotificationFragment fragment = new NotificationFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return false;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		datas.add(new Notification("", "Ivan: checkout the playlist! Morning SunShine Play", "32 " +
				"Minutes Ago"));
		datas.add(new Notification("", "Kujang is now your followers", "2 Hours Ago"));
		datas.add(new Notification("", "Kujang commented on your feed", "3 Hours Ago"));
		datas.add(new Notification("", "KlubRadio added 1 content on Morning SunShine",
				"Yesterday, 20:15"));
		return datas;
	}
}
