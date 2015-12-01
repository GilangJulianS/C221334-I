package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;

/**
 * Created by gilang on 21/11/2015.
 */
public class ContentsHolder extends UniversalHolder {

	public ViewGroup container;

	public ContentsHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		container = (ViewGroup) v.findViewById(R.id.content_container);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Contents)object);
	}

	public void bind(Contents contents){
		container.removeAllViews();
		for(Content c : contents.list){
			View v = activity.getLayoutInflater().inflate(R.layout.card_thumbnail, container, false);
			ImageView imgCover = (ImageView) v.findViewById(R.id.img_cover);
			TextView txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
			TextView txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
			TextView txtTertiary = (TextView) v.findViewById(R.id.txt_tertiary);

			if(c.imgUrl == null)
				imgCover.setImageResource(c.imgResource);

			if(c.txtPrimary != null)
				txtPrimary.setText(c.txtPrimary);
			else
				txtPrimary.setVisibility(View.GONE);
			if(c.txtSecondary != null)
				txtSecondary.setText(c.txtSecondary);
			else
				txtSecondary.setVisibility(View.GONE);
			if(c.txtTertiary != null)
				txtTertiary.setText(c.txtTertiary);
			else
				txtTertiary.setVisibility(View.GONE);

			container.addView(v);
		}
	}
}
