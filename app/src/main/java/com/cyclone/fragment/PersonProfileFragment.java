package com.cyclone.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.custom.Tools;
import com.cyclone.model.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonProfileFragment extends RecyclerFragment{

	public static final int MODE_OWN_PROFILE = 101;
	public static final int MODE_OTHERS_PROFILE = 102;
	private String transitionId;
	private int mode;

	public PersonProfileFragment(){}

	public static PersonProfileFragment newInstance(int mode, String transitionId, String json){
		PersonProfileFragment fragment = new PersonProfileFragment();
		fragment.json = json;
		fragment.mode = mode;
		fragment.transitionId = transitionId;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

	}

	@Override
	public int getColumnNumber() {
		return 1;
	}

	@Override
	public boolean isRefreshEnabled() {
		return true;
	}

	@Override
	public int getHeaderLayoutId() {
		return R.layout.part_header_person_profile;
	}

	@Override
	public void prepareHeader(View v) {
		setupHeader(v, "");
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Post(R.drawable.aaa_darto, "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				R.drawable.aaa_klub_funky, "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));
		datas.add(new Post(R.drawable.aaa_desta, "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				R.drawable.aaa_klub_funky, "Mix Max", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false));
		datas.add(new Post(R.drawable.aaa_dimas_danang, "<b>Dimas Danang</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", R.drawable.aaa_klub_cover_hey, "Cover - Hey", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST, false));
		return datas;
	}

	public void setupHeader(View header, String json){
		ViewGroup btnShowlist = (ViewGroup) header.findViewById(R.id.group_showlist);
		ViewGroup btnContent = (ViewGroup) header.findViewById(R.id.group_content);
		ViewGroup btnFollower = (ViewGroup) header.findViewById(R.id.group_follower);
		ViewGroup btnFollowing = (ViewGroup) header.findViewById(R.id.group_following);
		TextView txtShowlist = (TextView) header.findViewById(R.id.txt_showlist_count);
		TextView txtContent = (TextView) header.findViewById(R.id.txt_contents_count);
		TextView txtFollower = (TextView) header.findViewById(R.id.txt_followers_count);
		TextView txtFollowing = (TextView) header.findViewById(R.id.txt_following_count);
		Button btnAddShowList = (Button) header.findViewById(R.id.btn_add_showlist);
		Button btnUpload = (Button) header.findViewById(R.id.btn_upload);
		Button btnFollow = (Button) header.findViewById(R.id.btn_follow);
		ImageView imgUser = (ImageView) header.findViewById(R.id.img_user);

		setupBackground(header);

		if(mode == MODE_OWN_PROFILE){
			header.findViewById(R.id.btn_follow).setVisibility(View.GONE);
		}else if(mode == MODE_OTHERS_PROFILE){
			header.findViewById(R.id.group_btn_own_profile).setVisibility(View.GONE);
			final Button followButton = (Button) header.findViewById(R.id.btn_follow);
			followButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Drawable img = ContextCompat.getDrawable(getActivity(), R.drawable
							.ic_check_black_36dp);
					followButton.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
					followButton.setText("Followed");
					followButton.setEnabled(false);
				}
			});
		}

		btnShowlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_FEED);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Showlist");
				startActivity(i);
			}
		});
		btnContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_FEED);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Content");
				startActivity(i);
			}
		});
		btnFollower.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_PEOPLE);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Follower");
				startActivity(i);
			}
		});
		btnFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_PEOPLE);
				i.putExtra("activity", R.layout.activity_drawer_standard);
				i.putExtra("title", "Following");
				startActivity(i);
			}
		});

		btnAddShowList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setMessage("")
						.setTitle("Select showlist type")
						.setPositiveButton("Add a mix", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent i = new Intent(activity, DrawerActivity.class);
								i.putExtra("title", "Add a mix");
								i.putExtra("activity", R.layout.activity_drawer_standard);
								i.putExtra("layout", MasterActivity.LAYOUT_ADD_MIX);
								activity.startActivity(i);
							}
						})
						.setNegativeButton("Add a playlist", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
				builder.create().show();
			}
		});

		btnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Upload button pressed", Toast.LENGTH_SHORT).show();
			}
		});

		btnFollow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "Follow button pressed", Toast.LENGTH_SHORT).show();
			}
		});


//		txtContent.setText("");
//		txtShowlist.setText("");
//		txtFollower.setText("");
//		txtFollowing.setText("");

		imgUser.setImageResource(R.drawable.background_login);
		if(Build.VERSION.SDK_INT >= 21)
			imgUser.setTransitionName(transitionId);
	}


	public void setupBackground(View header){
		ImageView imgHeader = (ImageView) header.findViewById(R.id.img_header_background);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_login);
		bitmap = Tools.blur(bitmap, 0.5f, 10);
		bitmap = Tools.crop(bitmap);
		imgHeader.setImageBitmap(bitmap);
	}
}
