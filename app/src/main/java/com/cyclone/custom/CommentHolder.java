package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Comment;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentHolder extends UniversalHolder{

	public ImageView imgUser;
	public TextView txtUsername;
	public TextView txtComment;
	public TextView txtTime;

	public CommentHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		txtUsername = (TextView) v.findViewById(R.id.txt_name);
		txtComment = (TextView) v.findViewById(R.id.txt_comment);
		txtTime = (TextView) v.findViewById(R.id.txt_time);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Comment)object);
	}

	public void bind(Comment comment){
		UrlImageViewHelper.setUrlDrawable(imgUser, comment.imgUrl, R.drawable.ic_person_black_48dp);
		txtUsername.setText(comment.username);
		txtComment.setText(comment.comment);
		txtTime.setText(comment.time);
	}
}
