package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
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
	public ImageButton btnMenu;

	public ProgramHolder(View v, Activity activity, UniversalAdapter adapter){
		super(v, activity, adapter);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtTitle = (TextView) v.findViewById(R.id.txt_title);
		txtSchedule = (TextView) v.findViewById(R.id.txt_schedule);
		ratingBar = (RatingBar) v.findViewById(R.id.ratingbar);
		card = (ViewGroup) v.findViewById(R.id.card_program);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Program) object, activity);
	}

	public void bind(Program program, final Activity activity){
		Program p = program;
		final ImageView imageView = imgCover;
		imgCover.setImageResource(R.drawable.background_login);
		txtTitle.setText(p.title);
		txtSchedule.setText(p.schedule);
		ratingBar.setRating(p.rating);
		card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PROGRAM_PAGE);
				i.putExtra("title", "Hit the Beat");
				if (Build.VERSION.SDK_INT >= 16) {
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
							(activity, imageView, "program");
					activity.startActivity(i, options.toBundle());
				} else {
					activity.startActivity(i);
				}
			}
		});
		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(activity, btnMenu);
				menu.inflate(R.menu.popup_default);
//				menu.setOnMenuItemClickListener(new PopupMenuListener(activity));
				menu.show();
			}
		});
	}

}
