package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.cyclone.model.Announcer;
import com.cyclone.model.Program;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 11/10/2015.
 */
public class AnnouncersFragment extends RecyclerFragment {

	private List<Announcer> completeAnnouncer;

	public AnnouncersFragment(){}

	public static AnnouncersFragment newInstance(String json){
		AnnouncersFragment fragment = new AnnouncersFragment();
		fragment.json = json;
		fragment.completeAnnouncer = new ArrayList<>();
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parseData("");
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
	}

	@Override
	public int getColumnNumber() {
		return 3;
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

	public List<Object> parseData(String json){
		List<Object> announcers = new ArrayList<>();
		completeAnnouncer = new ArrayList<>();

//		--------------- dummy ------------
		announcers.add(new Announcer("http://imgurl.com", "Adiel"));
		announcers.add(new Announcer("http://imgurl.com", "Arien"));
		announcers.add(new Announcer("http://imgurl.com", "Arie W"));
		announcers.add(new Announcer("http://imgurl.com", "Chandra"));
		announcers.add(new Announcer("http://imgurl.com", "Erdina"));
		announcers.add(new Announcer("http://imgurl.com", "Hasanah"));
		announcers.add(new Announcer("http://imgurl.com", "Indira"));
		announcers.add(new Announcer("http://imgurl.com", "Indra"));
		announcers.add(new Announcer("http://imgurl.com", "Ivan"));
		announcers.add(new Announcer("http://imgurl.com", "Kujang"));
		announcers.add(new Announcer("http://imgurl.com", "Rini"));
		announcers.add(new Announcer("http://imgurl.com", "Reno"));
		announcers.add(new Announcer("http://imgurl.com", "Sam"));
		announcers.add(new Announcer("http://imgurl.com", "Tira"));
		for(Object o : announcers){
			completeAnnouncer.add((Announcer)o);
		}

		return announcers;
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
			List<Announcer> searchResult = search(newText);
			for(Announcer a : searchResult){
				newAdapter.add(a);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Announcer> search(String query){
		List<Announcer> result = new ArrayList<>();
		for(Announcer a : completeAnnouncer){
			if(a.name.toLowerCase().contains(query.toLowerCase())){
				result.add(a);
			}
		}
		return result;
	}
}
