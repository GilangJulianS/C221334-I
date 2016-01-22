package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;

/**
 * Created by gilang on 23/11/2015.
 */
public class MixesHolder extends UniversalHolder {

	public ViewGroup container;

	public MixesHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		container = (ViewGroup) v.findViewById(R.id.content_container);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Mixes)object);
	}

	public void bind(Mixes mixes){
		int counter = 0;
		container.removeAllViews();
		for(Mix m : mixes.list){
			View v = activity.getLayoutInflater().inflate(R.layout.card_mix, container, false);
			ImageView imgCover = (ImageView) v.findViewById(R.id.img_cover);
			TextView txtTitle = (TextView) v.findViewById(R.id.txt_title);

			imgCover.setImageResource(R.drawable.wallpaper);
			txtTitle.setText(m.text);

			container.addView(v);

			counter++;
		}
		while(counter < 3){
			View v = activity.getLayoutInflater().inflate(R.layout.view_filler_horizontal_full, container, false);
			container.addView(v);
			counter++;
		}
	}

}
