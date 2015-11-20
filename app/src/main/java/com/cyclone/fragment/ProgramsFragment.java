package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramsFragment extends Fragment {

	private RecyclerView mRecyclerView;
	private RecyclerView.LayoutManager mLayoutManager;
	private UniversalAdapter adapter;
	private List<Program> programs;
	private SwipeRefreshLayout swipeLayout;

	public ProgramsFragment(){}

	public static ProgramsFragment newInstance(){
		ProgramsFragment fragment = new ProgramsFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		mLayoutManager = new GridLayoutManager(getActivity(), 2);
		mRecyclerView.setLayoutManager(mLayoutManager);

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		mRecyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		mRecyclerView.setAdapter(adapter);

		programs = parse("");

		animate(programs.get(0));

		((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Programs");

		return v;
	}

	public List<Program> parse(String json){
		List<Program> datas = new ArrayList<>();
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
		return datas;
	}

	private void animate(final Program program){
		final Handler handler = new Handler();
		final Program p = program;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(p);
				programs.remove(p);
				adapter.notifyItemInserted(adapter.datas.size()-1);
				if(!programs.isEmpty()){
					animate(programs.get(0));
				}
			}
		}, 50);
	}
}
