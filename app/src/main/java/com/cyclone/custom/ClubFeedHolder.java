package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
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
import com.cyclone.fragment.CommentFragment;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.loopback.repository.FeedLikeRepository;
import com.cyclone.model.Post;
import com.strongloop.android.loopback.RestAdapter;

/**
 * Created by gilang on 01/11/2015.
 */
public class ClubFeedHolder extends UniversalHolder{

	public ImageView imgUser;
	public TextView txtHeaderName;
	public TextView txtHeaderInfo;
	public ImageView imgPost;
	public TextView txtPostTitle;
	public TextView txtPostContent;
	public TextView txtPostInfo;
	public TextView txtLikesInfo;
	public TextView txtCommentInfo;
	public ImageButton btnLike;
	public ImageButton btnShare;
	public ImageButton btnComment;
	public ViewGroup container;
	public ImageButton btnMenu;
	private Activity activity;
	private int transitionId;
	public static int autoId = 0;

	public ClubFeedHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		txtHeaderName = (TextView) v.findViewById(R.id.txt_header_name);
		txtHeaderInfo = (TextView) v.findViewById(R.id.txt_header_info);
		imgPost = (ImageView) v.findViewById(R.id.img_post);
		txtPostTitle = (TextView) v.findViewById(R.id.txt_post_title);
		txtPostContent = (TextView) v.findViewById(R.id.txt_post_content);
		txtPostInfo = (TextView) v.findViewById(R.id.txt_post_info);
		txtLikesInfo = (TextView) v.findViewById(R.id.txt_likes_info);
		txtCommentInfo = (TextView) v.findViewById(R.id.txt_comment_info);
		btnLike = (ImageButton) v.findViewById(R.id.btn_like);
		btnShare = (ImageButton) v.findViewById(R.id.btn_share);
		btnComment = (ImageButton) v.findViewById(R.id.btn_comment);
		btnMenu = (ImageButton) v.findViewById(R.id.btn_menu);
		if(v instanceof ViewGroup)
			container = (ViewGroup) v;
		transitionId = autoId;
		autoId++;
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		this.activity = activity;
		bind((Post)object, activity);
	}

	public void bind(Post post, final Activity activity){
		final Post p = post;
		imgUser.setImageResource(R.drawable.background_login);
		if(Build.VERSION.SDK_INT >= 21)
			imgUser.setTransitionName("profile" + transitionId);
		txtHeaderName.setText(Html.fromHtml(p.headerName));
		txtHeaderInfo.setText(p.timestamp + " • " + p.playlistType);
		imgPost.setImageResource(R.drawable.background_login);
		txtPostTitle.setText(p.postTitle);
		txtPostContent.setText(p.postContent);
		txtPostInfo.setText(p.postInfo);
		txtLikesInfo.setText(p.likesCount + " likes • ");
		txtCommentInfo.setText(" " + p.commentCount + " comments");
		if(p.isLiked)
			btnLike.setColorFilter(Color.parseColor("#E91E63"));
		else
			btnLike.setColorFilter(null);

		txtLikesInfo.setClickable(true);
		txtLikesInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Snackbar.make(v, "Like Clicked", Snackbar.LENGTH_SHORT).show();
			}
		});

		txtCommentInfo.setClickable(true);
		txtCommentInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DrawerActivity.class);
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_COMMENT);
				intent.putExtra("mode", CommentFragment.MODE_READ_WRITE);
				intent.putExtra("title", "Comments");
				intent.putExtra("contentId", p.FeedId);
				activity.startActivity(intent);
			}
		});
		btnMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PopupMenu menu = new PopupMenu(activity, btnMenu);
				menu.inflate(R.menu.popup_default);
				menu.setOnMenuItemClickListener(new PopupMenuListener(activity, p, PopupMenuListener.TYPE_CLUB_FEED, btnMenu));
				menu.show();
			}
		});

		btnLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final RestAdapter restAdapter = new RestAdapter(activity.getBaseContext(), ServerUrl.API_URL);
				final FeedLikeRepository feedLikeRepository= restAdapter.createRepository(FeedLikeRepository.class);
				if (!p.isLiked) {
					btnLike.setColorFilter(Color.parseColor("#E91E63"));
					p.isLiked = true;
					p.likesCount++;
					txtLikesInfo.setText(p.likesCount + " likes • ");
					System.out.println("on holder id : " + p.FeedId);
					feedLikeRepository.like(p.FeedId);
				} else {
					btnLike.setColorFilter(null);
					p.isLiked = false;
					p.likesCount--;
					txtLikesInfo.setText(p.likesCount + " likes • ");
					System.out.println("on holder id : " + p.FeedId);
					feedLikeRepository.unlike(p.FeedId);
				}
			}
		});
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("image/jpg");
				Uri path = Uri.parse("android.resource://com.cyclone/" + R.drawable.header);
				i.putExtra(Intent.EXTRA_STREAM, Uri.parse("" + path));
				i.putExtra(Intent.EXTRA_TEXT, p.postContent);
				activity.startActivity(Intent.createChooser(i, "Share via"));
			}
		});
		btnComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(activity, DrawerActivity.class);
				intent.putExtra("fragmentType", MasterActivity.FRAGMENT_COMMENT);
				intent.putExtra("mode", CommentFragment.MODE_READ_WRITE);
				intent.putExtra("title", "Comments");
				intent.putExtra("contentId", p.FeedId);
				activity.startActivity(intent);
			}
		});
		final ImageView imageView = imgUser;
		if(p.type == Post.TYPE_NEWS) {
			imgUser.setVisibility(View.GONE);
			txtHeaderInfo.setText(p.timestamp);
			txtPostInfo.setVisibility(View.GONE);
		}else if(p.type == Post.TYPE_POST){
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_PERSON_PROFILE);
				i.putExtra("mode", PersonProfileFragment.MODE_OTHERS_PROFILE);
				i.putExtra("transition", "profile" + transitionId);

				if(Build.VERSION.SDK_INT >= 16) {
					ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
							(activity, imageView, "profile" + transitionId);
					activity.startActivity(i, options.toBundle());
				}else{
					activity.startActivity(i);
				}
				}
			};

			imgUser.setOnClickListener(listener);
			txtHeaderName.setOnClickListener(listener);
			if(p.playlistType.equals("Mix")){
				container.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(activity, DrawerActivity.class);
						i.putExtra("fragmentType", MasterActivity.FRAGMENT_MIX);
						i.putExtra("title", "Mix max");
						activity.startActivity(i);
					}
				});
			}else if(p.playlistType.equals("Playlist")){
				container.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(activity, DrawerActivity.class);
						i.putExtra("fragmentType", MasterActivity.FRAGMENT_PLAYLIST);
						i.putExtra("title", p.postTitle);
						i.putExtra("id", p.TypeId);
						activity.startActivity(i);
					}
				});
			}
		}
	}
}
