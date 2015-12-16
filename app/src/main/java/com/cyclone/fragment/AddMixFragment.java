package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class AddMixFragment extends RecyclerFragment {

	private List<Mix> completeMix;

	public AddMixFragment(){}

	public static AddMixFragment newInstance(String json){
		AddMixFragment fragment = new AddMixFragment();
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
		List<Mix> mixList;
		Mixes mixes;

		datas.add(new Section("Genre", "genre", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Rock", "Genre"));
		mixList.add(new Mix("", "Pop", "Genre"));
		mixList.add(new Mix("", "Electronic", "Genre"));
		completeMix.addAll(mixList);
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Artist", "artist", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "Raisa", "Artist"));
		mixList.add(new Mix("", "Daft Punk", "Artist"));
		mixList.add(new Mix("", "Maroon 5", "Artist"));
		completeMix.addAll(mixList);
		mixes = new Mixes(mixList);
		datas.add(mixes);

		datas.add(new Section("Radio Content", "content", Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
		mixList = new ArrayList<>();
		mixList.add(new Mix("", "News", "Radio Content"));
		mixList.add(new Mix("", "Travel",  "Radio Content"));
		mixList.add(new Mix("", "Talk",  "Radio Content"));
		completeMix.addAll(mixList);
		mixes = new Mixes(mixList);
		datas.add(mixes);

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
			List<Mix> mixList;
			List<Mix> searchResult = search(newText);
			String currentType = "";
			while(searchResult.size() > 0){
				currentType = searchResult.get(0).tag;
				mixList = new ArrayList<Mix>();
				mixList.add(searchResult.get(0));
				searchResult.remove(0);
				for(int i=0; i<searchResult.size() && mixList.size() < 3; i++){
					if(searchResult.get(i).tag.equals(currentType)){
						mixList.add(searchResult.get(i));
						searchResult.remove(i);
						i--;
					}
				}
				if(mixList.size() >= 3){
					newAdapter.add(new Section(currentType, currentType, Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
				}else{
					newAdapter.add(new Section(currentType, currentType, Section.TYPE_NONE, MasterActivity.FRAGMENT_GRID_MIX));
				}
				newAdapter.add(new Mixes(mixList));
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Mix> search(String query){
		List<Mix> result = new ArrayList<>();
		for(Mix m : completeMix){
			if(m.text.toLowerCase().contains(query.toLowerCase())){
				result.add(m);
			}
		}
		return result;
	}
}
