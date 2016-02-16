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
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.custom.Tools;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.loopback.model.AccountStats;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.model.Post;
import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

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
	private List<Post> completePost;
	private static PersonProfileFragment fragment;

	TextView txtShowlist;
	TextView txtContent;
	TextView txtFollower;
	TextView txtFollowing;
	TextView txtUsername;
	TextView txtAbout;
	ImageView imgUser;
	View header;
	ImageView imgHeader;

	public PersonProfileFragment(){}

	public static PersonProfileFragment newInstance(int mode, String transitionId, String json){
		fragment = new PersonProfileFragment();
		fragment.json = json;
		fragment.mode = mode;
		fragment.DataId = json;
		fragment.transitionId = transitionId;
		fragment.completePost = new ArrayList<>();
		return fragment;
	}

	public static PersonProfileFragment getInstance() {
		if (fragment == null) fragment = new PersonProfileFragment();
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
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

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		completePost = new ArrayList<>();
		if (UtilArrayData.feedTimelines.size() > 0) {

			for (int i = 0; i < UtilArrayData.feedTimelines.size(); i++) {
				FeedTimeline feedTimeline = UtilArrayData.feedTimelines.get(i);
				System.out.println("name : " + feedTimeline.getTypePost().get("name"));
				/*datas.add(new Post("", "<b>" + feedTimeline.getOwner().get("username") + "</b> " + feedTimeline.getTypePost().get("caption") + " <b>" + feedTimeline.getType() + "</b>", feedTimeline.getUpdated_at(), feedTimeline.getType(),
						"", feedTimeline.getTypePost().get("name"), "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));*/
				datas.add(new Post(feedTimeline.getOwner().get("profilePicture"),
						"<b>" + feedTimeline.getOwner().get("username") + "</b>",
						feedTimeline.getCreated_at(),
						feedTimeline.getType(),
						feedTimeline.getTypePost().get("image"),
						feedTimeline.getTypePost().get("name"),
						feedTimeline.getTypePost().get("caption"),
						/*feedTimeline.getTypePost().get("contentCount")*/ "20 tracks",
						feedTimeline.getLikesCount(),
						feedTimeline.getCommentsCount(),
						Post.TYPE_POST,
						feedTimeline.isLiked(),
						feedTimeline.getOwner().get("id"),
						feedTimeline.getOwner().get("username"),
						feedTimeline.getId(),
						feedTimeline.getTypePost().get("id")));

			}
		}
		for(Object o : datas){
			completePost.add((Post)o);
		}
		return datas;
	}

	public void setupHeader(final View header, String json) {
		fragment.header = header;
		ViewGroup btnShowlist = (ViewGroup) header.findViewById(R.id.group_showlist);
		ViewGroup btnContent = (ViewGroup) header.findViewById(R.id.group_content);
		ViewGroup btnFollower = (ViewGroup) header.findViewById(R.id.group_follower);
		ViewGroup btnFollowing = (ViewGroup) header.findViewById(R.id.group_following);
		txtShowlist = (TextView) header.findViewById(R.id.txt_showlist_count);
		txtContent = (TextView) header.findViewById(R.id.txt_contents_count);
		txtFollower = (TextView) header.findViewById(R.id.txt_followers_count);
		txtFollowing = (TextView) header.findViewById(R.id.txt_following_count);
		txtUsername = (TextView) header.findViewById(R.id.txt_username);
		txtAbout = (TextView) header.findViewById(R.id.txt_about);
		Button btnAddShowList = (Button) header.findViewById(R.id.btn_add_showlist);
		Button btnUpload = (Button) header.findViewById(R.id.btn_upload);
		Button btnFollow = (Button) header.findViewById(R.id.btn_follow);
		imgUser = (ImageView) header.findViewById(R.id.img_user);
		imgHeader = (ImageView) header.findViewById(R.id.img_header_background);

		if (UtilArrayData.currentProfile != null) {
			UrlImageViewHelper.setUrlDrawable(imgUser, UtilArrayData.currentProfile.getProfilePicture(), R.drawable.background_login, new UrlImageViewCallback() {
				@Override
				public void onLoaded(ImageView imageView, Bitmap bitmap, String s, boolean b) {
					setupBackground(bitmap);
				}
			});

			txtUsername.setText(UtilArrayData.currentProfile.getUsername());
			txtAbout.setText(UtilArrayData.currentProfile.getAbout());
		}
		setupBackground(BitmapFactory.decodeResource(getResources(), R.drawable.radio_icon));
		if(mode == MODE_OWN_PROFILE){
			header.findViewById(R.id.btn_follow).setVisibility(View.GONE);
		}else if(mode == MODE_OTHERS_PROFILE){
			header.findViewById(R.id.group_btn_own_profile).setVisibility(View.GONE);
			header.findViewById(R.id.group_btn_others_profile).setVisibility(View.VISIBLE);
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
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_FEED);
				i.putExtra("title", "Showlist");
				startActivity(i);
			}
		});
		btnContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_FEED);
				i.putExtra("title", "Content");
				startActivity(i);
			}
		});
		btnFollower.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PEOPLE);
				i.putExtra("title", "Follower");
				startActivity(i);
			}
		});
		btnFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PEOPLE);
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
								i.putExtra("title", "Create New Mix");
								i.putExtra("fragmentType", MasterActivity.FRAGMENT_ADD_MIX_FORM);
								activity.startActivity(i);
							}
						})
						.setNegativeButton("Add a playlist", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent i = new Intent(activity, DrawerActivity.class);
								i.putExtra("title", "Create New Playlist");
								i.putExtra("fragmentType", MasterActivity.FRAGMENT_ADD_PLAYLIST_FORM);
								activity.startActivity(i);
							}
						});
				builder.create().show();
			}
		});

		btnUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", MasterActivity.FRAGMENT_UPLOAD);
				i.putExtra("title", "Upload New Content");
				startActivity(i);
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


	public void setupBackground(Bitmap bitmap) {
		Bitmap img = bitmap;
		try {
			img = Tools.blur(img, 0.5f, 10);
		} catch (Exception e) {
			System.out.println("error image blur : " + e);
		}
		try {
			img = Tools.crop(img);
		} catch (Exception e) {
			System.out.println("error image crop : " + e);
		}
		try {
			imgHeader.setImageBitmap(img);
		} catch (Exception e) {
			System.out.println("error image set : " + e);
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			UniversalAdapter newAdapter;

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText, newAdapter);
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String newText, UniversalAdapter newAdapter){
		if(!newText.equals("")) {
			newAdapter = new UniversalAdapter(activity);
			List<Post> searchResult = search(newText);
			for(Post p : searchResult){
				newAdapter.add(p);
			}
			recyclerView.setAdapter(newAdapter);
		}else{
			adapter.notifyDataSetChanged();
			recyclerView.setAdapter(adapter);
		}
	}

	public List<Post> search(String query){
		List<Post> result = new ArrayList<>();
		for(Post p : completePost){
			if(p.postTitle.toLowerCase().contains(query.toLowerCase()) || p.postContent.contains(query.toLowerCase())){
				result.add(p);
			}
		}
		return result;
	}

	public void setProfile() {
		System.out.println("set profile");
		if (UtilArrayData.currentProfile != null) {
			UrlImageViewHelper.setUrlDrawable(imgUser, UtilArrayData.currentProfile.getProfilePicture(), R.drawable.background_login, new UrlImageViewCallback() {
				@Override
				public void onLoaded(ImageView imageView, Bitmap bitmap, String s, boolean b) {
					setupBackground(bitmap);
				}
			});
			txtUsername.setText(UtilArrayData.currentProfile.getUsername());
			txtAbout.setText(UtilArrayData.currentProfile.getAbout());
		}

	}

	public void setStats(AccountStats accountStats) {
		if (accountStats != null) {
			txtShowlist.setText(accountStats.getShowlists());
			txtContent.setText(accountStats.getUploads());
			txtFollower.setText(accountStats.getFollowers());
			;
			txtFollowing.setText(accountStats.getFollowings());
		}

	}
}
