package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 20/11/2015.
 */
public class HomeFragment extends RecyclerFragment {

	public HomeFragment(){}

	public static HomeFragment newInstance(String json){
		HomeFragment fragment = new HomeFragment();
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
		return R.layout.part_header_home;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeader(v, json);
	}

	public void bindHeader(View v, String json){
		Button btnPlay = (Button) v.findViewById(R.id.btn_play);
		Button btnMix = (Button) v.findViewById(R.id.btn_mix);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Play radio pressed", Toast.LENGTH_SHORT).show();
			}
		});

		btnMix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Mix for you pressed", Toast.LENGTH_SHORT).show();
			}
		});
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

		datas.add(new Section("Latest News", "news"));
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

		datas.add(new Section("New Release", "release"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Love never feel", "Michael Jackson", null));
		contentList.add(new Content("", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "Smells like te", "Nirvana", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Its My Life", "Bon Jovi", null));
		contentList.add(new Content("", "Don't Look Back", "Oasis", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "Rock Yeah", "Imam Darto", null));
		contentList.add(new Content("", "HipHopYo!", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Mix max", "Nycta Gina", null));
		contentList.add(new Content("", "DUbldbldb", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Cover Love", "Dimas Danang", null));
		contentList.add(new Content("", "Cover Demons", "Imam Darto", null));
		contentList.add(new Content("", "Cover Smells Like", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload"));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Cover Its-me", "Nycta Gina", null));
		contentList.add(new Content("", "Cover Don't Look Back", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}
}
