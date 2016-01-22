package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.MasterActivity;
import com.cyclone.custom.ContentsHolder;
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

	public AddPlaylistFragment(){}

	public static AddPlaylistFragment newInstance(String json){
		AddPlaylistFragment fragment = new AddPlaylistFragment();
		fragment.json = json;
		fragment.dataHolder = new ArrayList<>();
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
		datas.add(contents);

		datas.add(new Section("Talk", "talk", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talk", Content.FAVORITABLE, "Talkshow GOWASDSA", "Prambors FM Jakarta", null,false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		contentList.add(new Content("", "Talk", Content.FAVORITABLE, "Hampir 30 film", "Prambors FM Jakarta", null,false, Content.TYPE_RADIO_CONTENT,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("New Release", "release", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Love never feel", "Michael Jackson", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Demons", "Imagine Dragons", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "New Release", Content.FAVORITABLE, "Smells like te", "Nirvana", null,false, Content.TYPE_TRACKS,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Recommended Music", Content.FAVORITABLE, "Its My Life", "Bon Jovi", null,false, Content.TYPE_TRACKS,"", 0, ""));
		contentList.add(new Content("", "Recommended Music", Content.FAVORITABLE, "Don't Look Back", "Oasis", null,false, Content.TYPE_TRACKS,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "Morning Sunshine", "Dimas Danang", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "Rock Yeah", "Imam Darto", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		contentList.add(new Content("", "Hits Playlist", Content.FAVORITABLE, "HipHopYo!", "Desta", null,false, Content.TYPE_PLAYLIST,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Top mix", Content.FAVORITABLE, "Mix max", "Nycta Gina", null,false, Content.TYPE_MIX,"", 0, ""));
		contentList.add(new Content("", "Top mix", Content.FAVORITABLE, "DUbldbldb", "Julio", null,false, Content.TYPE_MIX,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Love", "Dimas Danang", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Demons", "Imam Darto", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Most Played Upload", Content.FAVORITABLE, "Cover Smells Like", "Desta", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Newly Uploaded", Content.FAVORITABLE, "Cover Its-me", "Nycta Gina", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		contentList.add(new Content("", "Newly Uploaded", Content.FAVORITABLE, "Cover Don't Look Back", "Julio", null,false, Content.TYPE_UPLOADED,"", 0, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
