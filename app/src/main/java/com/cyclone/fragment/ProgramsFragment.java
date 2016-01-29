package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Program;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramsFragment extends RecyclerFragment {

	private List<Program> completeProgram;

	public ProgramsFragment(){}

	public static ProgramsFragment newInstance(String json){
		ProgramsFragment fragment = new ProgramsFragment();
		fragment.json = json;
		fragment.completeProgram = new ArrayList<>();
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
		return 2;
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
	public boolean hasHeader() {
		return false;
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
		completeProgram = new ArrayList<>();
		datas.add(new Program("http://imgurl.com", "Inspiring Life", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Inspiring Morning", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Easy Busy", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Hit The Beat", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Inspiring Night", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Afternoon Cafe", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Soft Sensation", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Headline News", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Life Sports", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program("http://imgurl.com", "Wild Life", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		for(Object o : datas){
			completeProgram.add((Program)o);
		}
		return datas;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);

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
			List<Program> searchResult = search(newText);
			for(Program p : searchResult){
				newAdapter.add(p);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Program> search(String query){
		List<Program> result = new ArrayList<>();
		for(Program p : completeProgram){
			if(p.title.toLowerCase().contains(query.toLowerCase())){
				result.add(p);
			}
		}
		return result;
	}
}
