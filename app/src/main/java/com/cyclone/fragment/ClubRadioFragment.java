package com.cyclone.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.model.Post;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 17/10/2015.
 */
public class ClubRadioFragment extends RecyclerFragment {

	private FloatingActionButton fabUpload, fabMix, fabPlaylist;

	public ClubRadioFragment(){}

	public static ClubRadioFragment newInstance(String json){
		ClubRadioFragment fragment = new ClubRadioFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		ViewGroup fabContainer = (ViewGroup) activity.findViewById(R.id.container_fab);
		LayoutInflater inflater = activity.getLayoutInflater();
		View fab = inflater.inflate(R.layout.fab_club_radio, fabContainer, false);

		fabUpload = (FloatingActionButton) fab.findViewById(R.id.fab_upload);
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
		});
		fabContainer.addView(fab);

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
		if(UtilArrayData.feedTimelines.size() > 0){

			for (int i = 0; i < UtilArrayData.feedTimelines.size(); i++){
				FeedTimeline feedTimeline = UtilArrayData.feedTimelines.get(i);
				System.out.println("name : " + feedTimeline.getTypePost().get("name"));
				/*datas.add(new Post("", "<b>" + feedTimeline.getOwner().get("username") + "</b> " + feedTimeline.getTypePost().get("caption") + " <b>" + feedTimeline.getType() + "</b>", feedTimeline.getUpdated_at(), feedTimeline.getType(),
						"", feedTimeline.getTypePost().get("name"), "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));*/
				datas.add(new Post("", "<b>"+feedTimeline.getOwner().get("username")+"</b> "+feedTimeline.getTypePost().get("caption")+" <b>"+feedTimeline.getType()+"</b>", feedTimeline.getCreated_at(), feedTimeline.getType(),
						"", feedTimeline.getTypePost().get("name"), "New playlist by me", "40 tracks", feedTimeline.getLikesCount(), feedTimeline.getCommentsCount(), Post.TYPE_POST, feedTimeline.isLiked(), feedTimeline.getOwner().get("id"),feedTimeline.getOwner().get("username"), feedTimeline.getId()));

			}
		}else{
			datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
					"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
					"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
					"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
					.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
					"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
					"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
					"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
					"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
					.TYPE_POST, false, "0", "Username", ""));
			datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
					"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false, "0", "Username", ""));
		}

		return datas;
	}
}
