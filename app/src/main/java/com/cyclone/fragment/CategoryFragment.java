package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 21/11/2015.
 */
public class CategoryFragment extends RecyclerFragment {

	public CategoryFragment(){}

	public static CategoryFragment newInstance(String json){
		CategoryFragment fragment = new CategoryFragment();
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

	public List<Object> parse(String json){
		Contents contents;
		List<Object> datas = new ArrayList<>();
		List<Content> contentList;

		datas.add(new Section("News", "news"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contentList.add(new Content("", "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contentList.add(new Content("", "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", "talk"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talkshow GOWASDSA", "Prambors FM Jakarta", null));
		contentList.add(new Content("", "Hampir 30 film", "Prambors FM Jakarta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Music", "music"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Love never feel", "Michael Jackson", null));
		contentList.add(new Content("", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Smells like te", "Nirvana", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Travel", "travel"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Its My Life", "Bon Jovi", null));
		contentList.add(new Content("", "Don't Look Back", "Oasis", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Advertisement", "ads"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "Rock Yeah", "Imam Darto", null));
		contentList.add(new Content("", "HipHopYo!", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
