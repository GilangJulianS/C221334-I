package com.cyclone.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Request;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class RequestFragment extends RecyclerFragment{

	private FloatingActionButton btnMessage, btnPhone;

	public RequestFragment(){}

	public static RequestFragment newInstance(String json){
		RequestFragment fragment = new RequestFragment();
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
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
		datas.add(new Request("", 0, "Imam Darto", "Request lagunya daft punk dong yang apa aja " +
				"deh tq", "18:21"));
		datas.add(new Request("", 0, "@danangpostman", "Mnt lg galau apa aja deh, salamnya buat " +
				"mantan @prambors", "18:15"));
		datas.add(new Request("", 0, "Christina Jennifer", "KK req lagunya Jessie J yang " +
				"flashlight ya", "18:11"));
		datas.add(new Request("", 0, "Deddy Mahendra", "Req lagunya the cash ya hahaha", "18:01"));
		datas.add(new Request("", 0, "Nycta Gina", "Request dong, request apa ya tapinya bingung",
				"18:21"));
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
}
