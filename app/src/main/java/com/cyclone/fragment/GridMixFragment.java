package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Mix;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class GridMixFragment extends RecyclerFragment {

	private List<Mix> completeMix;

	public GridMixFragment(){}

	public static GridMixFragment newInstance(String json){
		GridMixFragment fragment = new GridMixFragment();
		fragment.json = json;
		fragment.completeMix = new ArrayList<>();
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
		return 3;
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
		completeMix = new ArrayList<>();
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		datas.add(new Mix("", "Raisa", "Artist"));
		datas.add(new Mix("", "Daft Punk", "Artist"));
		datas.add(new Mix("", "Maroon 5", "Artist"));
		datas.add(new Mix("", "Noah", "Artist"));
		datas.add(new Mix("", "Coldplay", "Artist"));
		datas.add(new Mix("", "Oasis", "Artist"));
		datas.add(new Mix("", "Taylor Swift", "Artist"));
		datas.add(new Mix("", "Green Day", "Artist"));
		datas.add(new Mix("", "Iwan Fals", "Artist"));
		datas.add(new Mix("", "Zedd", "Artist"));
		for(Object o : datas){
			completeMix.add((Mix)o);
		}
		return datas;

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
			List<Mix> searchResult = search(newText);
			for(Mix m : searchResult){
				newAdapter.add(m);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Mix> search(String query){
		List<Mix> result = new ArrayList<>();
		for(Mix m : completeMix){
			if(m.tag.toLowerCase().contains(query.toLowerCase()) || m.text.contains(query.toLowerCase())){
				result.add(m);
			}
		}
		return result;
	}

}
