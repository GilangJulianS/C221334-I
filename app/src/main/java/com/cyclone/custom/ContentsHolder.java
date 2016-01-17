package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.Utils.UtilUser;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.loopback.repository.PlaylistRepository;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Data;
import com.cyclone.model.MasterModel;
import com.cyclone.service.ServicePlayOnHolder;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gilang on 21/11/2015.
 */
public class ContentsHolder extends UniversalHolder {

	public ViewGroup container;
	Bitmap mCover;

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
			//imgCover.setImageResource(R.drawable.wallpaper);
			//UrlImageViewHelper.setUrlDrawable(imgCover,c.imgUrl,R.drawable.radio_icon);
			UrlImageViewHelper.setUrlDrawable(imgCover, c.imgUrl, R.drawable.radio_icon, new UrlImageViewCallback() {
				@Override
				public void onLoaded(ImageView imageView, Bitmap bitmap, String s, boolean b) {
					imgCover.setImageBitmap(bitmap);
					mCover = bitmap;
				}
			});
			imgCover.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (temp.targetType == Content.TYPE_FAVORITABLE) {
						temp.isFavorited = !temp.isFavorited;
						if (temp.isFavorited) {
							imgHeart.setVisibility(View.VISIBLE);
							Data.add((MasterModel) temp);
							createSnackBar(activity).show();
						} else {
							imgHeart.setVisibility(View.GONE);
							Data.remove((MasterModel) temp);
							createSnackBar(activity).show();
						}
					} else {

						ServicePlayOnHolder servicePlayOnHolder = new ServicePlayOnHolder();
						servicePlayOnHolder.startPlayOnFragment(v.getContext(), HomeFragment.getInsane(), c.tag, c.position);

						System.out.println("Contens Holder c.tag : "+ c.tag);
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
					//final Content c = new Content("","","","","", "");
					menu.setOnMenuItemClickListener(new PopupMenuListener(activity, c, btnMenu, c.id));
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
		System.out.println("snackbar here ................");

		Snackbar snackbar =
				Snackbar.make(((DrawerActivity) activity).coordinatorLayout, Data.getData().size()
						+ " items added", Snackbar.LENGTH_INDEFINITE)
						.setAction("Done", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								System.out.println("KLIKED : " + DrawerActivity.getFragmentType());
								//Toast.makeText(activity.getBaseContext(),"KLIKED : "+ DrawerActivity.getFragmentType(), Toast.LENGTH_LONG).show();
								if(DrawerActivity.getFragmentType() == DrawerActivity.FRAGMENT_ADD_PLAYLIST_FORM){
									if(UtilUser.currentUser != null) {
										Map<String, String> parm = new HashMap<String, String>();
										//parm.put("id", UtilUser.currentUser.getId().toString());
										parm.put("name", UtilArrayData.TitleAdd);
										parm.put("caption", "created new");
										parm.put("private", "false");
										final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
										PlaylistRepository playlistRepository = restAdapter.createRepository(PlaylistRepository.class);

										playlistRepository.createContract();
										playlistRepository.creat(parm, new Adapter.Callback() {
											@Override
											public void onSuccess(String response) {
												System.out.println("saved success : " + response);
												activity.onBackPressed();
											}

											@Override
											public void onError(Throwable t) {
												activity.onBackPressed();
												System.out.println(t);
											}
										});
									}
/*
									final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
									final contenRepository contenRepo = restAdapter.createRepository(contenRepository.class);
									if(UtilUser.currentUser != null){
										Map<String, String> parm = new HashMap<>();
										parm.put("type", "playlist");
										parm.put("name", AddPlaylistFragment.title);
										parm.put("id", UtilUser.currentUser.getId().toString());
										parm.put("audio", "");
										parm.put("lyric", "");
										parm.put("info", "");
										parm.put("private", "false");

										contenRepo.createContract();
										contenRepo.creat(parm, new Adapter.Callback() {
											@Override
											public void onSuccess(String response) {
												System.out.println("saved success : "+response);
											}

											@Override
											public void onError(Throwable t) {
												System.out.println(t);
											}
										});
										System.out.println(UtilUser.currentUser.getId().toString());
										System.out.println("title : " + UtilArrayData.TitleAdd);

									}
									else{
										System.out.println("user is null");
									}*/

								}
								//activity.onBackPressed();
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
