package com.cyclone.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.cyclone.R;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Person;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonListFragment extends Fragment {

	private RecyclerView recyclerView;
	private List<Person> datas;
	private UniversalAdapter adapter;
	private SwipeRefreshLayout swipeLayout;

	public PersonListFragment(){}

	public static PersonListFragment newInstance(){
		PersonListFragment fragment = new PersonListFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstaceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setEnabled(false);

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


		adapter = new UniversalAdapter(getActivity(), "");
		datas = parse("");
		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		recyclerView.setAdapter(adapter);

		animate(datas.get(0));

		return v;
	}

	private void animate(final Person person){
		final Handler handler = new Handler();
		final Person p = person;
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(p);
				datas.remove(p);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animate(datas.get(0));
				}
			}
		}, 200);
	}

	public List<Person> parse(String json){
		List<Person> persons = new ArrayList<>();
		persons.add(new Person(""+R.drawable.aaa_darto, "Imam Darto", "@imamdarto"));
		persons.add(new Person(""+R.drawable.aaa_dimas_danang, "Dimas Danang", "@dimasdanang"));
		persons.add(new Person(""+R.drawable.aaa_julio, "Julio", "@julio"));
		persons.add(new Person(""+R.drawable.aaa_kenny, "Kenny", "@kenny"));
		persons.add(new Person(""+R.drawable.aaa_cj, "CJ", "@cj"));
		persons.add(new Person(""+R.drawable.aaa_desta, "Desta", "@desta"));
		persons.add(new Person(""+R.drawable.kimmi_s, "Kimmi Smiles", "@kimmi"));
		persons.add(new Person(""+R.drawable.aaa_gina, "Gina", "@gina"));
		persons.add(new Person(""+R.drawable.aaa_nadia, "Nadya", "@nadya"));
		persons.add(new Person(""+R.drawable.aaa_eda, "Eda", "@eda"));
		persons.add(new Person(""+R.drawable.aaa_jeje, "Jeje", "@jeje"));
		persons.add(new Person(""+R.drawable.aaa_eda, "Eda", "@eda"));
		return persons;
	}
}