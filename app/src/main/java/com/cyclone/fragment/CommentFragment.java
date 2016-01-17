package com.cyclone.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.comment;
import com.cyclone.model.Comment;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 23/11/2015.
 */
public class CommentFragment extends RecyclerFragment {

	private FloatingActionButton fabNewComment, fabMix, fabPlaylist;

	public CommentFragment(){}

	public static CommentFragment newInstance(String feedId){
		CommentFragment fragment = new CommentFragment();
		fragment.DataId = feedId;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		/*ViewGroup fabContainer = (ViewGroup) activity.findViewById(R.id.container_fab);
		LayoutInflater inflater = activity.getLayoutInflater();
		View fab = inflater.inflate(R.layout.fab_new_comment, fabContainer, false);

		fabNewComment = (FloatingActionButton)fab.findViewById(R.id.fab_new_comment);
		fabNewComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
		*//*fabUpload = (FloatingActionButton) fab.findViewById(R.id.fab_upload);
		fabUpload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "fab upload clicked", Toast.LENGTH_SHORT).show();
			}
		});
		fabMix = (FloatingActionButton) fab.findViewById(R.id.fab_mix);
		fabMix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "fab mix clicked", Toast.LENGTH_SHORT).show();
			}
		});
		fabPlaylist = (FloatingActionButton) fab.findViewById(R.id.fab_playlist);
		fabPlaylist.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(activity, "fab playlist clicked", Toast.LENGTH_SHORT).show();
			}
		});*//*
		fabContainer.addView(fab);*/

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
		return 0;
	}

	@Override
	public void prepareHeader(View v) {

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

		if(UtilArrayData.commentList.size()>0){
			List<comment> cmtL = UtilArrayData.commentList;
			for(int i = 0; i < cmtL.size(); i++){
				comment cmt = cmtL.get(i);
				datas.add(new Comment("", cmt.getUsername(), cmt.getContent(), cmt.getUpdatedAt()));
			}

		}
		else{

		}

		/*datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));
		datas.add(new Comment("", "Imam Darto", "Lagunya top top markotob", "18:30"));
		datas.add(new Comment("", "Dimas Danang", "Tambahin juga dong lagu2nya gw", "1w"));*/
		return datas;
	}

}
