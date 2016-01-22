package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Program;

/**
 * Created by gilang on 07/11/2015.
 */
public class PagerFragment extends Fragment {

	private ImageView image;
	private RatingBar ratingBar;
	private TextView txtSchedule;
	private TextView txtProgramPosition;
	private int id;
	private Program program;

	public PagerFragment(){}

	public static PagerFragment newInstance(Program program, int id){
		PagerFragment fragment = new PagerFragment();
		fragment.program = program;
		fragment.id = id;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_pager_item, parent, false);

		image = (ImageView) v.findViewById(R.id.img);
		ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
		txtSchedule = (TextView) v.findViewById(R.id.txt_schedule);
		txtProgramPosition = (TextView) v.findViewById(R.id.txt_program_position);

		image.setImageResource(R.drawable.wallpaper);
		ratingBar.setRating(program.rating);
		txtSchedule.setText(program.schedule);

		switch (id){
			case 0: txtProgramPosition.setText("Previous Show"); break;
			case 1: txtProgramPosition.setText("Now Showing"); break;
			case 2: txtProgramPosition.setText("Next Show"); break;
		}

		return v;
	}
}
