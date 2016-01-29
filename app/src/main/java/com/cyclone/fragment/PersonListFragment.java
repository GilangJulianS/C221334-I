package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Person;
import com.cyclone.model.Program;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonListFragment extends RecyclerFragment {

	private List<Person> completePerson;

	public PersonListFragment(){}

	public static PersonListFragment newInstance(String json){
		PersonListFragment fragment = new PersonListFragment();
		fragment.json = json;
		fragment.completePerson = new ArrayList<>();
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
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

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> persons = new ArrayList<>();
		completePerson = new ArrayList<>();
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
		for(Object o : persons){
			completePerson.add((Person)o);
		}
		return persons;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			UniversalAdapter newAdapter;

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText, newAdapter);
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String newText, UniversalAdapter newAdapter){
		if(!newText.equals("")) {
			newAdapter = new UniversalAdapter(activity);
			List<Person> searchResult = search(newText);
			for(Person p : searchResult){
				newAdapter.add(p);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Person> search(String query){
		List<Person> result = new ArrayList<>();
		for(Person p : completePerson){
			if(p.name.toLowerCase().contains(query.toLowerCase()) || p.username.contains(query.toLowerCase())){
				result.add(p);
			}
		}
		return result;
	}
}
