package com.cyclone.custom;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyclone.R;
import com.cyclone.model.Comment;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentHolder extends UniversalHolder{

	public ImageView imgUser;
	public TextView txtUsername;
	public TextView txtComment;

	public CommentHolder(View v, Activity activity, UniversalAdapter adapter) {
		super(v, activity, adapter);
		imgUser = (ImageView) v.findViewById(R.id.img_user);
		txtUsername = (TextView) v.findViewById(R.id.txt_name);
		txtComment = (TextView) v.findViewById(R.id.txt_comment);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Comment)object);
	}

	/*@Override
	public void bind(Object object, Activity activity, int position) {
		bind((Comment)object);
	}*/

	public void bind(Comment comment){
		imgUser.setImageResource(R.drawable.background_login);
		txtUsername.setText(comment.username);
		txtComment.setText(comment.comment);
	}
}
