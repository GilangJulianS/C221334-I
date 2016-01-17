package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.fragment.LiveStreamFragment;
import com.cyclone.model.RunningProgram;
import com.cyclone.service.ServicePlayOnHolder;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by gilang on 07/11/2015.
 */
public class RunningProgramHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnPlay;
	public ImageView cover;

	public RunningProgramHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
		cover = (ImageView) v. findViewById(R.id.cover);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((RunningProgram)object, activity);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((RunningProgram)object, activity);
	}*/

	public void bind(RunningProgram program, final Activity activity){
		String name, des;
		if(UtilArrayData.program != null){
			name = UtilArrayData.program.name;
			des = UtilArrayData.program.description;
		}
		else {
			name = program.name;
			des = program.description;
		}
		txtPrimary.setText(name);
		txtSecondary.setText(des);
		UrlImageViewHelper.setUrlDrawable(cover,program.cover_url, R.drawable.radio_icon);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ServicePlayOnHolder servicePlayOnHolder = new ServicePlayOnHolder();
				servicePlayOnHolder.startPlayOnFragment(v.getContext(), LiveStreamFragment.getInsane(), UtilArrayData.CATEGORY_LIVE_STREAMING, 0);
			}
		});
	}
}
