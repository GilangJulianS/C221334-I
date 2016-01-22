package com.cyclone.custom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.R;
import com.cyclone.model.Album;
import com.cyclone.model.Announcer;
import com.cyclone.model.Categories;
import com.cyclone.model.Comment;
import com.cyclone.model.Contents;
import com.cyclone.model.Mix;
import com.cyclone.model.Mixes;
import com.cyclone.model.Notification;
import com.cyclone.model.Person;
import com.cyclone.model.Post;
import com.cyclone.model.Program;
import com.cyclone.model.Queue;
import com.cyclone.model.ProgramContent;
import com.cyclone.model.ProgramPager;
import com.cyclone.model.Request;
import com.cyclone.model.RunningProgram;
import com.cyclone.model.Section;
import com.cyclone.model.Song;
import com.cyclone.model.SubcategoryItem;

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
	public static final int TYPE_RUNNING_PROGRAM = 110;
	public static final int TYPE_PROGRAM_CONTENT = 111;
	public static final int TYPE_PROGRAM_PAGER = 112;
	public static final int TYPE_REQUEST = 113;
	public static final int TYPE_CATEGORIES = 114;
	public static final int TYPE_CONTENTS = 115;
	public static final int TYPE_SUBCATEGORY_ITEM = 116;
	public static final int TYPE_MIXES = 117;
	public static final int TYPE_MIX = 118;
	public static final int TYPE_COMMENT = 119;

	public List<Object> datas;
	private SparseBooleanArray selectedItems;
	private Activity activity;

	public UniversalAdapter(Activity activity){
		datas = new ArrayList<>();
		this.activity = activity;
		selectedItems = new SparseBooleanArray();
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
		holder.setSelected(isSelected(position));
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
		else if(o instanceof Queue) return TYPE_PLAYLIST;
		else if(o instanceof Section) return TYPE_SECTION;
		else if(o instanceof Song) return TYPE_SONG;
		else if(o instanceof Album) return TYPE_ALBUM;
		else if(o instanceof RunningProgram) return TYPE_RUNNING_PROGRAM;
		else if(o instanceof ProgramContent) return TYPE_PROGRAM_CONTENT;
		else if(o instanceof ProgramPager) return TYPE_PROGRAM_PAGER;
		else if(o instanceof Request) return TYPE_REQUEST;
		else if(o instanceof Categories) return  TYPE_CATEGORIES;
		else if(o instanceof Contents) return TYPE_CONTENTS;
		else if(o instanceof SubcategoryItem) return TYPE_SUBCATEGORY_ITEM;
		else if(o instanceof Mixes) return TYPE_MIXES;
		else if(o instanceof Mix) return TYPE_MIX;
		else if(o instanceof Comment) return TYPE_COMMENT;
		return -1;
	}

	public int getLayoutId(int viewType){
		switch (viewType){
			case TYPE_ANNOUNCER: return R.layout.card_announcer;
			case TYPE_CLUB_FEED: return R.layout.card_club_feed;
			case TYPE_NOTIFICATION: return R.layout.card_notification;
			case TYPE_PERSON: return R.layout.card_person;
			case TYPE_PROGRAM: return R.layout.card_program;
			case TYPE_PLAYLIST: return R.layout.card_queue;
			case TYPE_SECTION: return R.layout.card_section;
			case TYPE_SONG: return R.layout.card_song;
			case TYPE_ALBUM: return R.layout.card_album;
			case TYPE_RUNNING_PROGRAM: return R.layout.card_running_program;
			case TYPE_PROGRAM_CONTENT: return R.layout.card_program_content;
			case TYPE_PROGRAM_PAGER: return R.layout.card_pager;
			case TYPE_REQUEST: return R.layout.card_request;
			case TYPE_CATEGORIES: return R.layout.card_categories;
			case TYPE_CONTENTS: return R.layout.card_contents;
			case TYPE_SUBCATEGORY_ITEM: return R.layout.card_subcategory_item;
			case TYPE_MIXES: return R.layout.card_contents;
			case TYPE_MIX: return R.layout.card_mix;
			case TYPE_COMMENT: return R.layout.card_comment;
			default: return -1;
		}
	}

	public UniversalHolder createViewHolder(View v, int viewType){
		UniversalHolder holder = null;
		switch (viewType){
			case TYPE_ANNOUNCER: holder = new AnnouncerHolder(v, activity, this); break;
			case TYPE_CLUB_FEED: holder = new ClubFeedHolder(v, activity, this); break;
			case TYPE_NOTIFICATION: holder = new NotificationHolder(v, activity, this); break;
			case TYPE_PERSON: holder = new PersonHolder(v, activity, this); break;
			case TYPE_PROGRAM: holder = new ProgramHolder(v, activity, this); break;
			case TYPE_PLAYLIST: holder = new QueueHolder(v, activity, this); break;
			case TYPE_SECTION: holder = new SectionHolder(v, activity, this); break;
			case TYPE_SONG: holder = new SongHolder(v, activity, this); break;
			case TYPE_ALBUM: holder = new AlbumHolder(v, activity, this); break;
			case TYPE_RUNNING_PROGRAM: holder = new RunningProgramHolder(v, activity, this); break;
			case TYPE_PROGRAM_CONTENT: holder = new ProgramContentHolder(v, activity, this); break;
			case TYPE_PROGRAM_PAGER: holder = new ProgramPagerHolder(v, activity, this); break;
			case TYPE_REQUEST: holder = new RequestHolder(v, activity, this); break;
			case TYPE_CATEGORIES: holder = new CategoriesHolder(v, activity, this); break;
			case TYPE_CONTENTS: holder = new ContentsHolder(v, activity, this); break;
			case TYPE_SUBCATEGORY_ITEM: holder = new SubcategoryHolder(v, activity, this); break;
			case TYPE_MIXES: holder = new MixesHolder(v, activity, this); break;
			case TYPE_MIX: holder = new MixHolder(v, activity, this); break;
			case TYPE_COMMENT: holder = new CommentHolder(v, activity, this); break;
		}
		return holder;
	}

	public boolean isSelected(int position){
		return getSelectedItems().contains(position);
	}

	public List<Integer> getSelectedItems(){
		List<Integer> items = new ArrayList<>(selectedItems.size());
		for (int i = 0; i < selectedItems.size(); ++i) {
			items.add(selectedItems.keyAt(i));
		}
		return items;
	}

	public void toggleSelect(int position){
		if(selectedItems.get(position, false)){
			selectedItems.delete(position);
		}else{
			selectedItems.put(position, true);
		}
		notifyItemChanged(position);
	}

	public void clearSelection() {
		List<Integer> selection = getSelectedItems();
		selectedItems.clear();
		for (Integer i : selection) {
			notifyItemChanged(i);
		}
	}

	public int getSelectedItemCount() {
		return selectedItems.size();
	}
}
