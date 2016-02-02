package com.cyclone.custom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
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
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilUser;
import com.cyclone.fragment.AddPlaylistFragment;
import com.cyclone.fragment.CategoryFragment;
import com.cyclone.fragment.HomeFragment;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.loopback.repository.PlaylistAccountRepository;
import com.cyclone.loopback.repository.PlaylistRepository;
import com.cyclone.model.AddPlaylist;
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
	static AddPlaylist addPlaylist;

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
		for( Content c : contents.list){
			View v = activity.getLayoutInflater().inflate(R.layout.card_thumbnail, container, false);
			final ImageView imgCover = (ImageView) v.findViewById(R.id.img_cover);
			final ImageView imgHeart = (ImageView) v.findViewById(R.id.img_heart);
			TextView txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
			TextView txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
			TextView txtTertiary = (TextView) v.findViewById(R.id.txt_tertiary);
			final ImageButton btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
			final CardView card = (CardView) v.findViewById(R.id.card);

			final Content temp = c;
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
					if (temp.favoritableType == Content.FAVORITABLE) {
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
					}else{
						PlayOnHolder playOnHolder = null;
						if(DrawerActivity.getFragmentType() == DrawerActivity.FRAGMENT_HOME){
							playOnHolder = HomeFragment.getInsane();
						}else if(DrawerActivity.getFragmentType() == DrawerActivity.FRAGMENT_CATEGORY){
							playOnHolder = CategoryFragment.getInstance();
						}
						ServicePlayOnHolder servicePlayOnHolder = new ServicePlayOnHolder();
						servicePlayOnHolder.startPlayOnFragment(v.getContext(), playOnHolder, temp.tag, temp.position);

						System.out.println("Contens Holder c.tag : " + temp.tag);
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
					final RestAdapter restAdapter = new RestAdapter(activity, ServerUrl.API_URL);
					final PlaylistAccountRepository playlistAccountRepository = restAdapter.createRepository(PlaylistAccountRepository.class);
					playlistAccountRepository.get(UtilUser.currentUser.getId(), new Adapter.Callback() {
						@Override
						public void onSuccess(String response) {

						}

						@Override
						public void onError(Throwable t) {
							System.out.println("error : "+t);
						}
					});
					PopupMenu menu = new PopupMenu(activity, btnMenu);
					menu.inflate(temp.getMenuResId());
					menu.setOnMenuItemClickListener(new PopupMenuListener(activity, temp, btnMenu));
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

		while(counter < 3){
		  LayoutInflater inflater = activity.getLayoutInflater();
		  View v = inflater.inflate(R.layout.view_filler_horizontal_full, container, false);
		  container.addView(v);
		  counter++;
		}
	}

	public static Snackbar createSnackBar(final Activity activity){
		addPlaylist = AddPlaylistFragment.getAddPlaylist();
		Snackbar snackbar =
				Snackbar.make(((DrawerActivity) activity).coordinatorLayout, Data.getData().size()
						+ " items added", Snackbar.LENGTH_INDEFINITE)
						.setAction("Done", new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								if (DrawerActivity.getFragmentType() == DrawerActivity.FRAGMENT_ADD_PLAYLIST_FORM) {
									if (UtilUser.currentUser != null) {
										Map<String, String> parm = new HashMap<String, String>();
										//parm.put("id", UtilUser.currentUser.getId().toString());
										parm.put("name", addPlaylist.getTitle());
										parm.put("caption", addPlaylist.getCaption());
										parm.put("private", "false");
										parm.put("image", "");
										System.out.println(addPlaylist.getTitle() + " | " + addPlaylist.getCaption());
										final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
										PlaylistRepository playlistRepository = restAdapter.createRepository(PlaylistRepository.class);

										final ProgressDialog dialog = ProgressDialog.show(activity, "",
												"Loading...", true);
										dialog.show();

										playlistRepository.creat(parm, new Adapter.Callback() {
											@Override
											public void onSuccess(String response) {
												System.out.println("saved success : " + response);
												dialog.dismiss();
												activity.onBackPressed();
											}

											@Override
											public void onError(Throwable t) {
												//activity.onBackPressed();
												dialog.dismiss();
												Toast.makeText(activity, "Failed to creat Playlist", Toast.LENGTH_SHORT).show();
												System.out.println(t);
											}
										});
									}
								}
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