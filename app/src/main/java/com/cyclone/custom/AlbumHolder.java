package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Album;
import com.cyclone.model.Content;

/**
 * Created by gilang on 01/11/2015.
 */
public class AlbumHolder extends UniversalHolder {

	public ImageView imgCover;
	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnMenu;

	public AlbumHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgCover = (ImageView) v.findViewById(R.id.img_cover);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Album) object);
	}

	public void bind(final Album album){
		imgCover.setImageResource(R.drawable.wallpaper);
		txtPrimary.setText(album.primary);
		txtSecondary.setText(album.secondary);
		final Content c = new Content(album.imgUrl, "album",Content.FAVORITABLE, album.primary, album.secondary, null, false, Content.TYPE_ALBUM, "", 0, "");
		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(activity, btnMenu);
				menu.inflate(R.menu.popup_default);
				menu.setOnMenuItemClickListener(new PopupMenuListener(activity, c, btnMenu));
				menu.show();
			}
		});
	}
}
