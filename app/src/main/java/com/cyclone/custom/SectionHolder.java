package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.Section;

import org.w3c.dom.Text;

/**
 * Created by gilang on 01/11/2015.
 */
public class SectionHolder extends UniversalHolder {

	public TextView txtTitle;
	public Button btnMore;

	public SectionHolder(View v, Activity activity){
		super(v, activity);
		txtTitle = (TextView) v.findViewById(R.id.txt_section_title);
		btnMore = (Button) v.findViewById(R.id.btn_more);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Section)object);
	}

	public void bind(final Section section){
		txtTitle.setText(section.name);
		if(section.category != null) {
			btnMore.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(activity, DrawerActivity.class);
					i.putExtra("layout", MasterActivity.LAYOUT_SUBCATEGORY);
					i.putExtra("activity", R.layout.activity_drawer_standard);
					i.putExtra("title", section.name);
					activity.startActivity(i);
				}
			});
		}else{
			btnMore.setVisibility(View.INVISIBLE);
		}
	}
}
