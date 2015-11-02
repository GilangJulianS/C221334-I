package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Section;

import org.w3c.dom.Text;

/**
 * Created by gilang on 01/11/2015.
 */
public class SectionHolder extends UniversalHolder {

	public TextView txtTitle;

	public SectionHolder(View v){
		super(v);
		txtTitle = (TextView) v.findViewById(R.id.txt_section_title);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Section)object);
	}

	public void bind(Section section){
		txtTitle.setText(section.name);
	}
}
