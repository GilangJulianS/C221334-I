package com.cyclone.fragment;

import android.content.Context;
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
	public static String title;
	public AddPlaylistFragment(){}
	public static Context mContext;

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
		mContext = getContext();
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
		contentList.add(new Content("", "Latest News", Content.TYPE_FAVORITABLE, "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05", ""));
		contentList.add(new Content("", "Latest News", Content.TYPE_FAVORITABLE, "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05", ""));
		contentList.add(new Content("", "Latest News", Content.TYPE_FAVORITABLE, "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05", ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", "talk", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talk", Content.TYPE_FAVORITABLE, "Talkshow GOWASDSA", "Prambors FM Jakarta", null, ""));
		contentList.add(new Content("", "Talk", Content.TYPE_FAVORITABLE, "Hampir 30 film", "Prambors FM Jakarta", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("New Release", "release", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "New Release", Content.TYPE_FAVORITABLE, "Love never feel", "Michael Jackson", null, ""));
		contentList.add(new Content("", "New Release", Content.TYPE_FAVORITABLE, "Demons", "Imagine Dragons", null, ""));
		contentList.add(new Content("", "New Release", Content.TYPE_FAVORITABLE, "Smells like te", "Nirvana", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Recommended Music", Content.TYPE_FAVORITABLE, "Its My Life", "Bon Jovi", null, ""));
		contentList.add(new Content("", "Recommended Music", Content.TYPE_FAVORITABLE, "Don't Look Back", "Oasis", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Hits Playlist", Content.TYPE_FAVORITABLE, "Morning Sunshine", "Dimas Danang", null, ""));
		contentList.add(new Content("", "Hits Playlist", Content.TYPE_FAVORITABLE, "Rock Yeah", "Imam Darto", null, ""));
		contentList.add(new Content("", "Hits Playlist", Content.TYPE_FAVORITABLE, "HipHopYo!", "Desta", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Top mix", Content.TYPE_FAVORITABLE, "Mix max", "Nycta Gina", null, ""));
		contentList.add(new Content("", "Top mix", Content.TYPE_FAVORITABLE, "DUbldbldb", "Julio", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Most Played Upload", Content.TYPE_FAVORITABLE, "Cover Love", "Dimas Danang", null, ""));
		contentList.add(new Content("", "Most Played Upload", Content.TYPE_FAVORITABLE, "Cover Demons", "Imam Darto", null, ""));
		contentList.add(new Content("", "Most Played Upload", Content.TYPE_FAVORITABLE, "Cover Smells Like", "Desta", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Newly Uploaded", Content.TYPE_FAVORITABLE, "Cover Its-me", "Nycta Gina", null, ""));
		contentList.add(new Content("", "Newly Uploaded", Content.TYPE_FAVORITABLE, "Cover Don't Look Back", "Julio", null, ""));
		dataHolder.addAll(contentList);
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
