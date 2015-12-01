package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Person;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonListFragment extends RecyclerFragment {

	public PersonListFragment(){}

	public static PersonListFragment newInstance(String json){
		PersonListFragment fragment = new PersonListFragment();
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
		List<Object> persons = new ArrayList<>();
		persons.add(new Person("", "Imam Darto", "@imamdarto"));
		persons.add(new Person("", "Dimas Danang", "@dimasdanang"));
		persons.add(new Person("", "Budi Susanto", "@busus"));
		persons.add(new Person("", "Ahmad Ikhsan", "@madis"));
		persons.add(new Person("", "Imam Darto", "@imamdarto"));
		persons.add(new Person("", "Dimas Danang", "@dimasdanang"));
		persons.add(new Person("", "Budi Susanto", "@busus"));
		persons.add(new Person("", "Ahmad Ikhsan", "@madis"));
		persons.add(new Person("", "Imam Darto", "@imamdarto"));
		persons.add(new Person("", "Dimas Danang", "@dimasdanang"));
		persons.add(new Person("", "Budi Susanto", "@busus"));
		persons.add(new Person("", "Ahmad Ikhsan", "@madis"));
		persons.add(new Person("", "Imam Darto", "@imamdarto"));
		persons.add(new Person("", "Dimas Danang", "@dimasdanang"));
		persons.add(new Person("", "Budi Susanto", "@busus"));
		persons.add(new Person("", "Ahmad Ikhsan", "@madis"));
		return persons;
	}
}
