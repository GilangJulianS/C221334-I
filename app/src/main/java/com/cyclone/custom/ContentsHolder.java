package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Data;
import com.cyclone.model.MasterModel;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.service.ServicePlayOnHolder;

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
		bind((Contents) object);
	}


	public void bind(Contents contents){
		int counter = 0;
		container.removeAllViews();
		for(final Content c : contents.list){
			View v = activity.getLayoutInflater().inflate(R.layout.card_thumbnail, container, false);
			final ImageView imgCover = (ImageView) v.findViewById(R.id.img_cover);
			final ImageView imgHeart = (ImageView) v.findViewById(R.id.img_heart);
			TextView txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
			TextView txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
			TextView txtTertiary = (TextView) v.findViewById(R.id.txt_tertiary);
			final ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
			final CardView card = (CardView) v.findViewById(R.id.card);

			final Content temp = c;
			imgCover.setImageResource(R.drawable.wallpaper);
			imgCover.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(temp.targetType == Content.TYPE_FAVORITABLE){
						temp.isFavorited = !temp.isFavorited;
						if(temp.isFavorited) {
							imgHeart.setVisibility(View.VISIBLE);
							Data.add((MasterModel)temp);
							createSnackBar(activity).show();
						}
						else {
							imgHeart.setVisibility(View.GONE);
							Data.remove((MasterModel)temp);
							createSnackBar(activity).show();
						}
					}else{
						Toast.makeText(activity, "Card clicked", Toast.LENGTH_SHORT).show();

						MediaWrapper mMedia = new MediaWrapper(Uri.parse(c.music_url));

						ServicePlayOnHolder servicePlayOnHolder = new ServicePlayOnHolder();
						servicePlayOnHolder.startPlayOnHome(v.getContext(), mMedia, HomeFragment.getInsane());
						/*ServiceGetData serviceGetData = new ServiceGetData();
						serviceGetData.startPlayOnHome(v.getContext(), mMedia, HomeFragment.getInsane());*/
					}
				}
			});
			if(temp.isFavorited) {
				imgHeart.setVisibility(View.VISIBLE);
			}else{
				imgHeart.setVisibility(View.GONE);
			}

			btnMenu.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					PopupMenu menu = new PopupMenu(activity, btnMenu);
					menu.inflate(R.menu.popup_default);
					menu.setOnMenuItemClickListener(new PopupMenuListener(activity));
					menu.show();
				}
			});

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


			counter++;
		}

//		while(counter < 3){
//			LayoutInflater inflater = activity.getLayoutInflater();
//			View v = inflater.inflate(R.layout.view_filler_horizontal_full, container, false);
//			container.addView(v);
//			counter++;
//		}
	}

	public static Snackbar createSnackBar(final Activity activity){
		Snackbar snackbar =
				Snackbar.make(((DrawerActivity) activity).coordinatorLayout, Data.getData().size()
						+ " items added", Snackbar.LENGTH_INDEFINITE)
						.setAction("Done", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								activity.onBackPressed();
							}
						});
		View snack = snackbar.getView();
		TextView txtSnack = (TextView) snack.findViewById(android.support.design.R.id.snackbar_text);
		txtSnack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_TRACK_LIST);
				i.putExtra("title", "Track List");
				activity.startActivity(i);
			}
		});
		return snackbar;
	}

}
