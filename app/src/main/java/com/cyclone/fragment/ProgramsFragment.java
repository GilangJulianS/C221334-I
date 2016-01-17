package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.radioProgram;
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

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();

		if(UtilArrayData.radioPrograms.size()>0){

			for(int i = 0; i < UtilArrayData.radioPrograms.size(); i++){
				radioProgram rp = UtilArrayData.radioPrograms.get(i);
						datas.add(new Program("http://imgurl.com", rp.getName(), "Sen - Jum, 04.00 - 05" +
						".00", 4, ""));
			}
		}
		else{
			datas.add(new Program("http://imgurl.com", "Inspiring Life", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Inspiring Morning", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Easy Busy", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Hit The Beat", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Inspiring Night", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Afternoon Cafe", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Soft Sensation", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Headline News", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Life Sports", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
			datas.add(new Program("http://imgurl.com", "Wild Life", "Sen - Jum, 04.00 - 05" +
					".00", 4, ""));
		}

		return datas;
	}
}
