package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.model.Section;

/**
 * Created by gilang on 01/11/2015.
 */
public class SectionHolder extends UniversalHolder {

	public TextView txtTitle;
	public Button btnMoreTransparent;
	public Button btnMoreNormal;

	public SectionHolder(View v, Activity activity, UniversalAdapter adapter){
		super(v, activity, adapter);
		txtTitle = (TextView) v.findViewById(R.id.txt_section_title);
		btnMoreTransparent = (Button) v.findViewById(R.id.btn_more_transparent);
		btnMoreNormal = (Button) v.findViewById(R.id.btn_more_normal);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Section)object);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Section)object);
	}*/

	public void bind(final Section section){
		txtTitle.setText(section.name);
		if(section.targetFragment != -1) {
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity, DrawerActivity.class);
					i.putExtra("fragmentType", section.targetFragment);
					i.putExtra("title", section.name);
					i.putExtra("section_category", section.category);
					activity.startActivity(i);
				}
			};
			btnMoreTransparent.setOnClickListener(listener);
			btnMoreNormal.setOnClickListener(listener);
			if(section.sectionType == Section.TYPE_TRANSPARENT)
				btnMoreTransparent.setVisibility(View.VISIBLE);
			else if(section.sectionType == Section.TYPE_NORMAL)
				btnMoreNormal.setVisibility(View.VISIBLE);
		}
	}
}
