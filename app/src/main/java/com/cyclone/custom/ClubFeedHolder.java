package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.fragment.PersonProfileFragment;
import com.cyclone.model.Post;

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
	public ImageButton btnLike;
	public ImageButton btnShare;
	public ImageButton btnComment;
	public ViewGroup container;
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
		btnLike = (ImageButton) v.findViewById(R.id.btn_like);
		btnShare = (ImageButton) v.findViewById(R.id.btn_share);
		btnComment = (ImageButton) v.findViewById(R.id.btn_comment);
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
		if(p.imgUrl == null)
			imgUser.setImageResource(p.imgInt);
		if(Build.VERSION.SDK_INT >= 21)
			imgUser.setTransitionName("profile" + transitionId);
		txtHeaderName.setText(Html.fromHtml(p.headerName));
		txtHeaderInfo.setText(p.timestamp + " • " + p.playlistType);

		if(p.postImgUrl == null)
			imgPost.setImageResource(p.postImgInt);
		txtPostTitle.setText(p.postTitle);
		txtPostContent.setText(p.postContent);
		txtPostInfo.setText(p.postInfo);
		txtLikesInfo.setText(p.likesCount + " likes • " + p.commentCount + " comments");
		btnLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!p.isLiked) {
					btnLike.setColorFilter(Color.parseColor("#E91E63"));
					p.isLiked = true;
				}else{
					btnLike.setColorFilter(null);
					p.isLiked = false;
				}
			}
		});
		btnShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		btnComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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
				i.putExtra("layout", DrawerActivity.LAYOUT_PERSON_PROFILE);
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
						i.putExtra("activity", R.layout.activity_drawer);
						i.putExtra("layout", MasterActivity.LAYOUT_MIX);
						i.putExtra("title", "Mix max");
						activity.startActivity(i);
					}
				});
			}
		}
	}
}
