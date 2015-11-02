package com.cyclone.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.model.Album;
import com.cyclone.model.Announcer;
import com.cyclone.model.Notification;
import com.cyclone.model.Person;
import com.cyclone.model.Post;
import com.cyclone.model.Program;
import com.cyclone.model.Playlist;
import com.cyclone.model.Section;
import com.cyclone.model.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 01/11/2015.
 */
public class UniversalAdapter extends Adapter<UniversalHolder> {

	public static final int TYPE_ANNOUNCER = 101;
	public static final int TYPE_CLUB_FEED = 102;
	public static final int TYPE_NOTIFICATION = 103;
	public static final int TYPE_PERSON = 104;
	public static final int TYPE_PLAYLIST = 105;
	public static final int TYPE_PROGRAM = 106;
	public static final int TYPE_SECTION = 107;
	public static final int TYPE_SONG = 108;
	public static final int TYPE_ALBUM = 109;

	public List<Object> datas;
	private Activity activity;
	private Context context;

	public UniversalAdapter(Activity activity, String json){
		datas = new ArrayList<>();
		this.activity = activity;
		this.context = (Context)activity;
	}

	public void add(Object o){
		datas.add(o);
	}

	public void add(Object o, int position){
		datas.add(position, o);
	}

	@Override
	public UniversalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
		return createViewHolder(v, viewType);
	}

	@Override
	public void onBindViewHolder(UniversalHolder holder, int position) {
		holder.bind(datas.get(position), activity, position);
	}

	@Override
	public int getItemCount() {
		return datas.size();
	}

	@Override
	public int getItemViewType(int position) {
		Object o = datas.get(position);
		if(o instanceof Announcer)return TYPE_ANNOUNCER;
		else if(o instanceof Post) return TYPE_CLUB_FEED;
		else if(o instanceof Notification) return TYPE_NOTIFICATION;
		else if(o instanceof Person) return TYPE_PERSON;
		else if(o instanceof Program) return TYPE_PROGRAM;
		else if(o instanceof Playlist) return TYPE_PLAYLIST;
		else if(o instanceof Section) return TYPE_SECTION;
		else if(o instanceof Song) return TYPE_SONG;
		else if(o instanceof Album) return TYPE_ALBUM;
		return -1;
	}

	public int getLayoutId(int viewType){
		switch (viewType){
			case TYPE_ANNOUNCER: return R.layout.card_announcer;
			case TYPE_CLUB_FEED: return R.layout.card_club_feed;
			case TYPE_NOTIFICATION: return R.layout.card_notification;
			case TYPE_PERSON: return R.layout.card_person;
			case TYPE_PROGRAM: return R.layout.card_program;
			case TYPE_PLAYLIST: return R.layout.card_playlist;
			case TYPE_SECTION: return R.layout.card_section;
			case TYPE_SONG: return R.layout.card_song;
			case TYPE_ALBUM: return R.layout.card_album;
			default: return -1;
		}
	}

	public UniversalHolder createViewHolder(View v, int viewType){
		UniversalHolder holder = null;
		switch (viewType){
			case TYPE_ANNOUNCER: holder = new AnnouncerHolder(v); break;
			case TYPE_CLUB_FEED: holder = new ClubFeedHolder(v); break;
			case TYPE_NOTIFICATION: holder = new NotificationHolder(v); break;
			case TYPE_PERSON: holder = new PersonHolder(v); break;
			case TYPE_PROGRAM: holder = new ProgramHolder(v); break;
			case TYPE_PLAYLIST: holder = new PlaylistHolder(v); break;
			case TYPE_SECTION: holder = new SectionHolder(v); break;
			case TYPE_SONG: holder = new SongHolder(v); break;
			case TYPE_ALBUM: holder = new AlbumHolder(v); break;
		}
		return holder;
	}
}
