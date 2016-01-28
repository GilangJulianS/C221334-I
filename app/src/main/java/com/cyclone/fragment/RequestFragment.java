package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.interfaces.getData;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class RequestFragment extends PlaybackServiceRecyclerFragment {

	private FloatingActionButton btnMessage, btnPhone;
	private static RequestFragment fragment;
	private static Context mContext;
	private Activity mActivity;
	private static getData mGetData;
	public RequestFragment(){}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGetData = this;
		mContext = getContext();
		mActivity = getActivity();
	}
	public static RequestFragment newInstance(String json){
		fragment = new RequestFragment();
		fragment.json = json;
		return fragment;
	}

	public static getData getmGetData() {
		return mGetData;
	}

	public static Context getmContext() {
		return mContext;
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
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		if(UtilArrayData.RequestList.size()>0){
			datas = UtilArrayData.RequestList;
		}
		return datas;
	}

	@Override
	public void onAttach(final Context context) {
		super.onAttach(context);
		if(context instanceof DrawerActivity){
			activity = (DrawerActivity) context;
			ViewGroup fabContainer = (ViewGroup) activity.findViewById(R.id.container_fab);
			LayoutInflater inflater = activity.getLayoutInflater();
			View fab = inflater.inflate(R.layout.fab_message_phone, fabContainer, false);
			btnMessage = (FloatingActionButton) fab.findViewById(R.id.fab_message);
			btnMessage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "fab message clicked", Toast.LENGTH_SHORT).show();
				}
			});
			btnPhone = (FloatingActionButton) fab.findViewById(R.id.fab_phone);
			btnPhone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(context, "fab phone clicked", Toast.LENGTH_SHORT).show();
				}
			});
			fabContainer.addView(fab);
		}
	}

	@Override
	public void onDataLoadedLiveStreaming(List<Object> data) {
		System.out.println("jumlah Data : " + data.size());
		if(data.size() > 0){
			showData();
		}
		else{
			System.out.println("nol dalam Data");
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);
			swipeLayout.setEnabled(true);
			swipeLayout.setRefreshing(false);
		}
	}
}
