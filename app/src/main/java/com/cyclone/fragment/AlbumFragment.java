package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.SnapGestureListener;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Album;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 01/11/2015.
 */
public class AlbumFragment extends RecyclerFragment {

	private ImageButton btnMenu;
	private Button btnPlay;
	private Button btnAddShowlist;
	private List<Song> completeSong;

	public AlbumFragment(){}

	public static AlbumFragment newInstance(String json){
		AlbumFragment fragment = new AlbumFragment();
		fragment.json = json;
		fragment.completeSong = new ArrayList<>();
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
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		btnPlay = (Button) v.findViewById(R.id.btn_play);
		btnAddShowlist = (Button) v.findViewById(R.id.btn_add_showlist);

		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Menu button clicked", Toast.LENGTH_SHORT).show();
			}
		});

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DrawerActivity.class);
				intent.putExtra("title", "Player");
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_PLAYER);
				startActivity(intent);
			}
		});
	}

	public List<Object> parse(String json){
		completeSong = new ArrayList<>();
		List<Object> datas = new ArrayList<>();
		datas.add(new Song("Counting Stars", "OneRepublic"));
		datas.add(new Song("Love Runs Out", "OneRepublic"));
		datas.add(new Song("If I Lose Myself", "OneRepublic"));
		datas.add(new Song("Feel Again", "OneRepublic"));
		datas.add(new Song("What You Wanted", "OneRepublic"));
		datas.add(new Song("I Lived", "OneRepublic"));
		datas.add(new Song("Light It Up", "OneRepublic"));
		datas.add(new Song("Life In Color", "OneRepublic"));
		datas.add(new Song("Counting Stars", "OneRepublic"));
		datas.add(new Song("Love Runs Out", "OneRepublic"));
		datas.add(new Song("If I Lose Myself", "OneRepublic"));
		datas.add(new Song("Feel Again", "OneRepublic"));
		datas.add(new Song("What You Wanted", "OneRepublic"));
		datas.add(new Song("I Lived", "OneRepublic"));
		datas.add(new Song("Light It Up", "OneRepublic"));
		datas.add(new Song("Life In Color", "OneRepublic"));
		for(Object o : datas){
			completeSong.add((Song)o);
		}

		datas.add(new Section("More By OneRepublic", null));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
		datas.add(new Album("", "Native", "2014"));
		datas.add(new Album("", "Waking Up", "2009"));
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
			List<Song> searchResult = search(newText);
			for(Song s : searchResult){
				newAdapter.add(s);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Song> search(String query){
		List<Song> result = new ArrayList<>();
		for(Song s : completeSong){
			if(s.primary.toLowerCase().contains(query.toLowerCase()) || s.secondary.contains(query.toLowerCase())){
				result.add(s);
			}
		}
		return result;
	}
}
