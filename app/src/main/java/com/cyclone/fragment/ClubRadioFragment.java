package com.cyclone.fragment;

import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.model.Post;
import com.cyclone.model.Song;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 17/10/2015.
 */
public class ClubRadioFragment extends RecyclerFragment {

	private FloatingActionButton fabUpload, fabMix, fabPlaylist;
	private List<Post> completePost;

	public ClubRadioFragment(){}

	public static ClubRadioFragment newInstance(String json){
		ClubRadioFragment fragment = new ClubRadioFragment();
		fragment.json = json;
		fragment.completePost = new ArrayList<>();
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
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
		completePost = new ArrayList<>();

		if(UtilArrayData.feedTimelines.size() > 0){

			for (int i = 0; i < UtilArrayData.feedTimelines.size(); i++){
				FeedTimeline feedTimeline = UtilArrayData.feedTimelines.get(i);
				System.out.println("name : " + feedTimeline.getTypePost().get("name"));
				/*datas.add(new Post("", "<b>" + feedTimeline.getOwner().get("username") + "</b> " + feedTimeline.getTypePost().get("caption") + " <b>" + feedTimeline.getType() + "</b>", feedTimeline.getUpdated_at(), feedTimeline.getType(),
						"", feedTimeline.getTypePost().get("name"), "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));*/
				datas.add(new Post("", "<b>"+feedTimeline.getOwner().get("username")+"</b> "+feedTimeline.getTypePost().get("caption")+" <b>"+feedTimeline.getType()+"</b>", feedTimeline.getCreated_at(), feedTimeline.getType(),
						"", feedTimeline.getTypePost().get("name"), "New playlist by me", "40 tracks", feedTimeline.getLikesCount(), feedTimeline.getCommentsCount(), Post.TYPE_POST, feedTimeline.isLiked(), feedTimeline.getOwner().get("id"),feedTimeline.getOwner().get("username"), feedTimeline.getId()));

			}
		}
		for(Object o : datas){
			completePost.add((Post)o);
		}
		/*datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false));
		datas.add(new Post("", "<b>Imam Darto</b> created new <b>Mix</b>", "1 Hour ago", "Mix",
				"", "Funky Sunshine", "New playlist by me", "40 tracks", 52, 20, Post.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> liked a Playlist", "2 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> created new <b>Playlist</b>", "4 Hour ago",
				"Playlist", "", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post
				.TYPE_POST, false));
		datas.add(new Post("", "<b>Desta</b> shared a <b>Playlist</b>", "4 Hour ago", "Playlist",
				"", "Pop 2015", "2015 top hits", "20 tracks", 1024, 56, Post.TYPE_POST, false));*/
		return datas;
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
}
