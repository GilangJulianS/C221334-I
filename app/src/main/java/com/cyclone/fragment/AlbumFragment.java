package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.model.Album;
import com.cyclone.model.Section;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 01/11/2015.
 */
public class AlbumFragment extends RecyclerFragment {

	public AlbumFragment(){}

	public static AlbumFragment newInstance(String json){
		AlbumFragment fragment = new AlbumFragment();
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
		return R.layout.part_header_album;
	}

	@Override
	public void prepareHeader(View v) {
		setupHeader(v, json);
	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void setupHeader(View v, String json){
		ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);

		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Menu button clicked", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Song("Counting Stars", "OneRepublic", ""));
		datas.add(new Song("Love Runs Out", "OneRepublic",""));
		datas.add(new Song("If I Lose Myself", "OneRepublic", ""));
		datas.add(new Song("Feel Again", "OneRepublic", ""));
		datas.add(new Song("What You Wanted", "OneRepublic", ""));
		datas.add(new Song("I Lived", "OneRepublic", ""));
		datas.add(new Song("Light It Up", "OneRepublic", ""));
		datas.add(new Song("Life In Color", "OneRepublic", ""));
		datas.add(new Song("Counting Stars", "OneRepublic", ""));
		datas.add(new Song("Love Runs Out", "OneRepublic", ""));
		datas.add(new Song("If I Lose Myself", "OneRepublic", ""));
		datas.add(new Song("Feel Again", "OneRepublic", ""));
		datas.add(new Song("What You Wanted", "OneRepublic", ""));
		datas.add(new Song("I Lived", "OneRepublic", ""));
		datas.add(new Song("Light It Up", "OneRepublic", ""));
		datas.add(new Song("Life In Color", "OneRepublic", ""));
		datas.add(new Section("More By OneRepublic", null));
		datas.add(new Album("", "Native", "2014",""));
		datas.add(new Album("", "Waking Up", "2009", ""));
		datas.add(new Album("", "Native", "2014", ""));
		datas.add(new Album("", "Waking Up", "2009", ""));
		return datas;
	}
}
