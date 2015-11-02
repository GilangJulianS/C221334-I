package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.model.Person;

/**
 * Created by gilang on 01/11/2015.
 */
public class PersonHolder extends UniversalHolder{

	public ImageView imgUser;
	public TextView txtName;
	public TextView txtUsername;
	public ViewGroup card;
	private int transitionId;
	public static int autoId = 0;

	public PersonHolder(View v) {
		super(v);
		imgUser = (ImageView)v.findViewById(R.id.img_user);
		txtName = (TextView) v.findViewById(R.id.txt_name);
		txtUsername = (TextView) v.findViewById(R.id.txt_username);
		card = (ViewGroup) v.findViewById(R.id.card_person);
		transitionId = autoId;
		autoId++;
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Person)object, activity);
	}

	public void bind(final Person person, final Activity activity){
		imgUser.setImageResource(R.drawable.background_login);
		txtName.setText(person.name);
		txtUsername.setText(person.username);
		final ImageView imageView = imgUser;
		card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, CollapseActivity.class);
				i.putExtra("layout", CollapseActivity.LAYOUT_PERSON_PROFILE);
				i.putExtra("title", person.name);
				i.putExtra("transition", "profile" + transitionId);
				if(Build.VERSION.SDK_INT >= 16) {
					ActivityOptionsCompat options = ActivityOptionsCompat
							.makeSceneTransitionAnimation(activity, imageView, "profile" + transitionId);
					activity.startActivity(i, options.toBundle());
				}else{
					activity.startActivity(i);
				}
			}
		});
	}
}
