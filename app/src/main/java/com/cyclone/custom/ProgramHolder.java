package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.model.Program;

/**
 * Created by gilang on 01/11/2015.
 */
public class ProgramHolder extends UniversalHolder{

	public ImageView imgCover;
	public TextView txtTitle;
	public TextView txtSchedule;
	public RatingBar ratingBar;
	public ViewGroup card;



	public ProgramHolder(View v){
		super(v);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		txtSchedule = (TextView) v.findViewById(R.id.txt_schedule);
		ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
		card = (ViewGroup) v.findViewById(R.id.card_program);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Program) object, activity);
	}

	public void bind(Program program, final Activity activity){
		Program p = program;

		imgCover.setImageResource(Integer.valueOf(p.imgUrl));
		//final ImageView imageView = imgCover;

		txtTitle.setText(p.title);
		txtSchedule.setText(p.schedule);
		ratingBar.setRating(p.rating);
		card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			Intent i = new Intent(activity, CollapseActivity.class);
			i.putExtra("layout", CollapseActivity.LAYOUT_PROGRAM_PAGE);
			if(Build.VERSION.SDK_INT >= 16) {
				ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
						(activity, imgCover, "program");
				activity.startActivity(i, options.toBundle());
			}else{
				activity.startActivity(i);
			}
			}
		});
	}

}
