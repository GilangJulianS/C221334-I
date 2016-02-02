package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.ContentsHolder;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.AddPlaylist;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Data;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 09/12/2015.
 */
public class AddPlaylistFragment extends RecyclerFragment {

	private List<Content> dataHolder;
	private List<Content> completeContent;
	private static AddPlaylist addPlaylist;
	private static AddPlaylistFragment fragment;

	public AddPlaylistFragment(){}

	public static AddPlaylistFragment newInstance(String json, AddPlaylist addPlaylist){
		fragment = new AddPlaylistFragment();
		fragment.json = json;
		fragment.dataHolder = new ArrayList<>();
		fragment.completeContent = new ArrayList<>();
		fragment.addPlaylist = addPlaylist;
		return fragment;
	}

	public static AddPlaylist getAddPlaylist() {
		return addPlaylist;
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

	@Override
	public void onResume() {
		super.onResume();
		for(Content c : dataHolder){
			if(Data.searchObject(c) == null){
				c.isFavorited = false;
				adapter.notifyDataSetChanged();
			}
		}

		ContentsHolder.createSnackBar(activity).show();
	}

	public List<Object> parse(String json){
		Contents contents;
		Categories categories;
		List<Object> datas = new ArrayList<>();
		List<Category> categoryList;
		List<Content> contentList;
		completeContent = new ArrayList<>();

		categoryList = new ArrayList<>();
		categoryList.add(new Category("Radio Content", "Radio Content"));
		categoryList.add(new Category("Music", "Music"));
		categoryList.add(new Category("Showlist", "Showlist"));
		categoryList.add(new Category("Uploaded", "Uploaded"));
		categories = new Categories(categoryList);
		datas.add(categories);

		datas.add(new Section("Latest News", "news", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Latest News", Content.FAVORITABLE, "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05",false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		contentList.add(new Content("", "Latest News", Content.FAVORITABLE, "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05",false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		contentList.add(new Content("", "Latest News", Content.FAVORITABLE, "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05",false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", "talk", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talk", Content.FAVORITABLE, "Talkshow GOWASDSA", "Prambors FM Jakarta", null,false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		contentList.add(new Content("", "Talk", Content.FAVORITABLE, "Hampir 30 film", "Prambors FM Jakarta", null,false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("New Release", "release", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Love never feel", "Michael Jackson", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Demons", "Imagine Dragons", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Smells like te", "Nirvana", null,false, Content.TYPE_TRACKS,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Recommended Music", Content.FAVORITABLE, "Its My Life", "Bon Jovi", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "Recommended Music", Content.FAVORITABLE, "Don't Look Back", "Oasis", null,false, Content.TYPE_TRACKS,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "Morning Sunshine", "Dimas Danang", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "Rock Yeah", "Imam Darto", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "HipHopYo!", "Desta", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Top mix", Content.FAVORITABLE, "Mix max", "Nycta Gina", null,false, Content.TYPE_MIX,"", 0, ""));
		contentList.add(new Content("", "Top mix", Content.FAVORITABLE, "DUbldbldb", "Julio", null,false, Content.TYPE_MIX,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Love", "Dimas Danang", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Demons", "Imam Darto", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Smells Like", "Desta", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Newly Uploaded", Content.FAVORITABLE, "Cover Its-me", "Nycta Gina", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Newly Uploaded", Content.FAVORITABLE, "Cover Don't Look Back", "Julio", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		completeContent.addAll(contentList);
		datas.add(contents);

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
			List<Content> contentList;
			List<Content> searchResult = search(newText);
			String currentType = "";
			System.out.println("size " + searchResult.size());
			while(searchResult.size() > 0){
				currentType = searchResult.get(0).tag;
				contentList = new ArrayList<Content>();
				contentList.add(searchResult.get(0));
				searchResult.remove(0);
				for(int i=0; i<searchResult.size() && contentList.size() < 3; i++){
					if(searchResult.get(i).tag.equals(currentType)){
						contentList.add(searchResult.get(i));
						searchResult.remove(i);
						i--;
					}
				}
				if(contentList.size() >= 3){
					newAdapter.add(new Section(currentType, currentType, Section.TYPE_TRANSPARENT, MasterActivity.FRAGMENT_GRID_MIX));
				}else{
					newAdapter.add(new Section(currentType, currentType, Section.TYPE_NONE, MasterActivity.FRAGMENT_GRID_MIX));
				}
				newAdapter.add(new Contents(contentList));
				System.out.println("iteration size " + contentList.size());
			}

			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Content> search(String query){
		List<Content> result = new ArrayList<>();
		for(Content c : completeContent){
			if(c.txtPrimary.toLowerCase().contains(query.toLowerCase()) || c.txtSecondary.contains(query.toLowerCase())){
				result.add(c);
			}
		}
		return result;
	}
}
