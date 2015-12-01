package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.model.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 10/10/2015.
 */
public class ProgramsFragment extends RecyclerFragment {

	public ProgramsFragment(){}

	public static ProgramsFragment newInstance(String json){
		ProgramsFragment fragment = new ProgramsFragment();
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

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Program(R.drawable.aa_desta_gina, "Desta and Gina In The Morning", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_dj_show, "DJ Show", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_the_dandless, "The Dandees ", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_light_night_hot, "Late Night Hot", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_jeli, "Jeli", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_40_gallery, "Top 40 Show", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_asia_pop_40, "Asia Pop 40", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		datas.add(new Program(R.drawable.aa_twix_mis, "Twix Mix", "Sen - Jum, 04.00 - 05" +
				".00", 4));
		return datas;
	}
}
