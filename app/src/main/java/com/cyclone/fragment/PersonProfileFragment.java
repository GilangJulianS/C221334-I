package com.cyclone.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.CollapseActivity;
import com.cyclone.R;
import com.cyclone.StandardActivity;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.custom.Tools;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.model.Post;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by gilang on 18/10/2015.
 */
public class PersonProfileFragment extends Fragment implements OnOffsetChangedListener {

	public static final int MODE_OWN_PROFILE = 101;
	public static final int MODE_OTHERS_PROFILE = 102;
	private CollapseActivity activity;
	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeLayout;
	private UniversalAdapter adapter;
	private List<Object> datas;
	private String transitionId;
	private int mode;
	private boolean swipeEnabled = true;

	public PersonProfileFragment(){}

	public static PersonProfileFragment newInstance(int mode, String transitionId){
		PersonProfileFragment fragment = new PersonProfileFragment();
		fragment.mode = mode;
		fragment.transitionId = transitionId;
		return fragment;
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.fragment_recycler, parent, false);

		swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh_layout);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeLayout.setRefreshing(false);
						adapter.datas.clear();
						adapter.notifyDataSetChanged();
						datas = parse("");
						animate(datas.get(0));
					}
				}, 5000);
			}
		});

		recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		SlideInUpAnimator slideAnimator = new SlideInUpAnimator(new
				DecelerateInterpolator());
		slideAnimator.setAddDuration(500);
		slideAnimator.setMoveDuration(500);
		recyclerView.setItemAnimator(slideAnimator);

		adapter = new UniversalAdapter(getActivity(), "");
		recyclerView.setAdapter(adapter);

		datas = parse("");
		animate(datas.get(0));

		return v;
	}

	public List<Object> parse(String json){
		List<Object> datas = new ArrayList<>();
		datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST));
		return datas;
	}

	@Override
	public void onAttach(Context context){
		super.onAttach(context);
		AppCompatActivity activity;
		if(context instanceof AppCompatActivity) {
			activity = (AppCompatActivity)context;
			ViewGroup parallaxHeader = (ViewGroup) activity.findViewById(R.id
					.parallax_header);
			LayoutInflater inflater = activity.getLayoutInflater();
			View header = inflater.inflate(R.layout.part_header_person_profile, parallaxHeader,
					false);
			setupHeader(header, "");
			parallaxHeader.removeAllViews();
			parallaxHeader.addView(header);
		}
		if(context instanceof CollapseActivity){
			this.activity = (CollapseActivity) context;
		}
	}

	public void setupHeader(View header, String json){
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
		TextView txtShowlist = (TextView) header.findViewById(R.id.txt_showlist_count);
		TextView txtContent = (TextView) header.findViewById(R.id.txt_contents_count);
		TextView txtFollower = (TextView) header.findViewById(R.id.txt_followers_count);
		TextView txtFollowing = (TextView) header.findViewById(R.id.txt_following_count);
		txtShowlist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_FEED);
				i.putExtra("title", "Showlist");
				startActivity(i);
			}
		});
		txtContent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_FEED);
				i.putExtra("title", "Content");
				startActivity(i);
			}
		});
		txtFollower.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_PEOPLE);
				i.putExtra("title", "Follower");
				startActivity(i);
			}
		});
		txtFollowing.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), StandardActivity.class);
				i.putExtra("layout", StandardActivity.LAYOUT_PEOPLE);
				i.putExtra("title", "Following");
				startActivity(i);
			}
		});
		ImageView imgUser = (ImageView) header.findViewById(R.id.img_user);
		imgUser.setImageResource(R.drawable.background_login);
		if(Build.VERSION.SDK_INT >= 21)
			imgUser.setTransitionName(transitionId);
	}

	public void setupBackground(View header){
		ImageView imgHeader = (ImageView) header.findViewById(R.id.img_header_background);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background_login);
		bitmap = Tools.blur(bitmap, 0.5f, 10);
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int x = width / 8;
		int y = height / 8;
		int newWidth = width * 6 / 8;
		int newHeight = height * 6 / 8;
		bitmap = Bitmap.createBitmap(bitmap, x, y, newWidth, newHeight);
		imgHeader.setImageBitmap(bitmap);
	}

	private void animate(final Object o){
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.add(o);
				datas.remove(o);
				adapter.notifyItemInserted(adapter.datas.size() - 1);
				if (!datas.isEmpty()) {
					animate(datas.get(0));
				}
			}
		}, 200);
	}

	@Override
	public void onChanged(float percent) {
		System.out.println(percent + " " + swipeEnabled);
		if(percent == 0 && !swipeEnabled){
			swipeEnabled = true;
			swipeLayout.setEnabled(true);
		}else if(percent != 0 && swipeEnabled){
			swipeEnabled = false;
			swipeLayout.setEnabled(false);
		}
	}
}
