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
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Section;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 05/12/2015.
 */
public class FavoritesFragment extends RecyclerFragment {

	List<Content> completeList;

	public FavoritesFragment(){}

	public static FavoritesFragment newInstance(String json){
		FavoritesFragment fragment = new FavoritesFragment();
		fragment.json = json;
		fragment.completeList = new ArrayList<>();
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
		List<Object> datas = new ArrayList<>();
		List<Content> contentList;
		Contents contents;

		contentList = new ArrayList<>();
		datas.add(new Section("Playlist", "palylist"));
		contentList.add(new Content("", "Playlist", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "Playlist", "My Playlist 1", "Imam Darto", null));
		contentList.add(new Content("", "Playlist", "My Playlist 2", "Siapa", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Mix", "mix"));
		contentList.add(new Content("", "Mix", "Mix max", "Nycta Gina", null));
		contentList.add(new Content("", "Mix", "Dubidududu", "Julia", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Tracks", "tracks"));
		contentList.add(new Content("", "Tracks", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Tracks", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Tracks", "Demons", "Imagine Dragons", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Artist", "artist"));
		contentList.add(new Content("", "Artist", "Sheila on 7", null, null));
		contentList.add(new Content("", "Artist", "Oasis", null, null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Album", "album"));
		contentList.add(new Content("", "Album", "Xscape", "Michael Jackson", null));
		contentList.add(new Content("", "Album", "Night Vision", "Imagine Dragons", null));
		contentList.add(new Content("", "Album", "The Very Best", "Nirvana", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Uploaded", "uploaded"));
		contentList.add(new Content("", "Uploaded", "It's me - cover", "Nycta Gina", null));
		contentList.add(new Content("", "Uploaded", "Don't remember - cover", "Julia", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		contentList = new ArrayList<>();
		datas.add(new Section("Radio Content", "radio content"));
		contentList.add(new Content("", "Radio Content", "Di atas sesuatu", "Apalah", null));
		contentList.add(new Content("", "Radio Content", "Hampir 30 film", "Hampir sebanyak 30 film terbaru ditayangkan dalam acara ini", null));
		contentList.add(new Content("", "Radio Content", "Melawan Asap", "Dalam perjuangannya melawan asap yang semakin memburuk", null));
		completeList.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText);
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String query){
		UniversalAdapter newAdapter;
		if(query.equals("")){
			recyclerView.setAdapter(adapter);
		}else{
			newAdapter = new UniversalAdapter(activity);
			List<Content> contentList;
			List<Content> searchResult = search(query);
			String currentType = "";
			while(searchResult.size() > 0){
				currentType = searchResult.get(0).tag;
				newAdapter.add(new Section(currentType, currentType, Section.TYPE_NONE, MasterActivity.FRAGMENT_GRID_MIX));
				newAdapter.add(new SubcategoryItem("", searchResult.get(0).txtPrimary, searchResult.get(0).txtSecondary));
				searchResult.remove(0);
				for(int i=0; i<searchResult.size(); i++){
					if(searchResult.get(i).tag.equals(currentType)){
						newAdapter.add(new SubcategoryItem("", searchResult.get(i).txtPrimary, searchResult.get(i).txtSecondary));
						searchResult.remove(i);
						i--;
					}
				}
			}
			recyclerView.setAdapter(newAdapter);
		}
	}

	public List<Content> search(String query){
		List<Content> result = new ArrayList<>();
		for(Content c : completeList){
			if(c.txtPrimary != null){
				if(c.txtPrimary.toLowerCase().contains(query.toLowerCase()))
					result.add(c);
			}
			if(c.txtSecondary != null){
				if(c.txtSecondary.toLowerCase().contains(query.toLowerCase()))
				result.add(c);
			}
			if(c.txtTertiary != null){
				if(c.txtTertiary.toLowerCase().contains(query.toLowerCase()))
				result.add(c);
			}
		}
		return result;
	}
}
