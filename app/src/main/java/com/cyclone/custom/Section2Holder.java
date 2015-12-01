package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Section2;

import org.w3c.dom.Text;

/**
 * Created by gilang on 23/11/2015.
 */
public class Section2Holder extends UniversalHolder {

	public TextView txtTitle;
	public Button btnSeeAll;

	public Section2Holder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtTitle = (TextView) v.findViewById(R.id.txt_section_title);
		btnSeeAll = (Button) v.findViewById(R.id.btn_more);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Section2)object);
	}

	public void bind(final Section2 section){
		txtTitle.setText(section.name);
		btnSeeAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("title", section.name);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("layout", MasterActivity.LAYOUT_GRID_MIX);
				activity.startActivity(i);
			}
		});
	}
}
