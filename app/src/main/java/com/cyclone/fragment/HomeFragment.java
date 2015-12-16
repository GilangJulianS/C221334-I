package com.cyclone.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.interfaces.test;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;
import com.cyclone.player.gui.MediaBrowserRecyclerFragment;
import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.media.MediaLibrary;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;
import com.cyclone.service.ServiceGetData;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.util.MediaBrowser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 20/11/2015.
 */
public class HomeFragment extends MediaBrowserRecyclerFragment implements MediaBrowser.EventListener , IgetCoverUrl, test, PlayOnHolder {

	MediaLibrary mMediaLibrary;
	private MediaBrowser mMediaBrowser;
	private ProgressDialog pDialog;
	private Context mContext;
	static HomeFragment fragment;
	List<MediaWrapper> mAudioList;
	MediaWrapperList mwl;


	public HomeFragment(){}

	public static HomeFragment newInstance(String json){
		fragment = new HomeFragment();
		fragment.json = json;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMediaLibrary = MediaLibrary.getInstance();
		mContext = getContext();

		recuclerContext = mContext;
		mGetData = this;
	}

	@Override
	protected void display() {

	}

	@Override
	public void clear() {

	}

	@Override
	public List<Object> getDatas() {

		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {

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
		return R.layout.part_header_home;
	}

	@Override
	public void prepareHeader(View v) {
		bindHeader(v, json);
	}

	@Override
	public int getSlidingLayoutId() {
		return 0;
	}

	@Override
	public void prepareSlidingMenu(View v) {

	}

	public void bindHeader(View v, String json){
		Button btnPlay = (Button) v.findViewById(R.id.btn_play);
		Button btnMix = (Button) v.findViewById(R.id.btn_mix);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(activity, "Play radio pressed", Toast.LENGTH_SHORT).show();
				if(mService != null){
					mAudioList = MediaLibrary.getInstance().getAudioItems();
					if(mService != null)
						mService.load(mAudioList,0);
					else
						System.out.println("service null");
				}

			}
		});

		btnMix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(activity, "Mix for you pressed", Toast.LENGTH_SHORT).show();
				ServiceGetData serviceGetData = new ServiceGetData();
				serviceGetData.startActionGetTestDataForPlay(mContext, fragment);
			}
		});


	}

	public List<Object> parse(String json){
		Contents contents;
		Categories categories;
		List<Object> datas = new ArrayList<>();
		List<Category> categoryList;
		List<Content> contentList;

		categoryList = new ArrayList<>();
		categoryList.add(new Category("Radio Content", "Radio Content"));
		categoryList.add(new Category("Music", "Music"));
		categoryList.add(new Category("Showlist", "Showlist"));
		categoryList.add(new Category("Uploaded", "Uploaded"));
		categories = new Categories(categoryList);
		datas.add(categories);

		datas.add(new Section("Latest News", "news", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		if (UtilArrayData.ContentNews.size()>0){
			contentList.addAll(UtilArrayData.ContentNews);
		}
		else{
			contentList.add(new Content("", "Latest News", "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
			contentList.add(new Content("", "Latest News", "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
			contentList.add(new Content("", "Latest News", "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		}
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", "talk", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Talk", "Talkshow GOWASDSA", "Prambors FM Jakarta", null));
		contentList.add(new Content("", "Talk", "Hampir 30 film", "Prambors FM Jakarta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("New Release", "release", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "New Release", "Love never feel", "Michael Jackson", null));
		contentList.add(new Content("", "New Release", "Demons", "Imagine Dragons", null));
		contentList.add(new Content("", "New Release", "Smells like te", "Nirvana", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Recommended Music", "recommended", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Recommended Music", "Its My Life", "Bon Jovi", null));
		contentList.add(new Content("", "Recommended Music", "Don't Look Back", "Oasis", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Hits Playlist", "hits", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Hits Playlist", "Morning Sunshine", "Dimas Danang", null));
		contentList.add(new Content("", "Hits Playlist", "Rock Yeah", "Imam Darto", null));
		contentList.add(new Content("", "Hits Playlist", "HipHopYo!", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", "mix", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Top mix", "Mix max", "Nycta Gina", null));
		contentList.add(new Content("", "Top mix", "DUbldbldb", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", "popular_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Most Played Upload", "Cover Love", "Dimas Danang", null));
		contentList.add(new Content("", "Most Played Upload", "Cover Demons", "Imam Darto", null));
		contentList.add(new Content("", "Most Played Upload", "Cover Smells Like", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", "new_upload", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", "Newly Uploaded", "Cover Its-me", "Nycta Gina", null));
		contentList.add(new Content("", "Newly Uploaded", "Cover Don't Look Back", "Julio", null));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}

	ArrayList<MediaWrapper> mTracksToAppend = new ArrayList<MediaWrapper>();
	@Override
	public void onMediaAdded(int index, Media media) {
		mTracksToAppend.add(new MediaWrapper(media));
	}

	@Override
	public void onMediaRemoved(int index, Media media) {

	}

	@Override
	public void onBrowseEnd() {
		if (mService != null)
			mService.append(mTracksToAppend);
	}



	private void updateLists() {
		/*mAudioList = MediaLibrary.getInstance().getAudioItems();
		if (mAudioList.isEmpty()){
			//updateEmptyView(mViewPager.getCurrentItem());
			//mSwipeRefreshLayout.setRefreshing(false);
			//mTabLayout.setVisibility(View.GONE);
			//focusHelper(true, R.id.artists_list);
		} else {
			//mTabLayout.setVisibility(View.VISIBLE);
			//mHandler.sendEmptyMessageDelayed(MSG_LOADING, 300);

			final ArrayList<Runnable> tasks = new ArrayList<Runnable>(Arrays.asList(updateArtists,
					updateAlbums, updateSongs, updateGenres, updatePlaylists));

			//process the visible list first
			tasks.add(0, tasks.remove(mViewPager.getCurrentItem()));
			tasks.add(new Runnable() {
				@Override
				public void run() {
					if (!mAdaptersToNotify.isEmpty())
						display();
				}
			});
			VLCApplication.runBackground(new Runnable() {
				@Override
				public void run() {
					for (Runnable task : tasks)
						task.run();
				}
			});
		}*/
	}




	public void goPlay(){
		if(mService != null) {
			mService.load(mAudioList, 0);

			Intent intent = new Intent(mContext, DrawerActivity.class);
			intent.putExtra("title", "Player");
			intent.putExtra("fragmentType", MasterActivity.FRAGMENT_PLAYER);
			intent.putExtra("menuId", 8);
			startActivity(intent);
			//finish();
		}
	}


	@Override
	public void OnLoadComplite(List<MediaWrapper> mediaWrapperList) {
		System.out.println("bisa terima dari callback");

		if(mService != null) {
			mService.stop();
			mService.load(mediaWrapperList, 0);
			mService.playIndex(0);
		}
	}

	@Override
	public void OnCoverLoaded(Bitmap bitmap) {

		//System.out.println("bitmap ter load <<<<<<<<<<<<<<<<<<<<<<<<");
		//mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).setPicture(bitmap);
	}

	@Override
	public void onDataLoadedHome(List<List> data) {
		System.out.println("JSON DITERIMA<<<<<<<<<<<<");
		//List<Content> cnt = new ArrayList<>();
		UtilArrayData.ContentNews.clear();
		UtilArrayData.ContentNews = data.get(0);

		/*public Content(String imgUrl, String tag, int targetType, @Nullable String txtPrimary, @Nullable String txtSecondary, @Nullable String txtTertiary){
			super(txtPrimary, txtSecondary);
			this.imgUrl = imgUrl;
			this.txtTertiary = txtTertiary;
			this.targetType = targetType;
			this.tag = tag;
			isFavorited = false;
	*/

		swipeLayout.setRefreshing(false);
		adapter.datas.clear();
		adapter.notifyDataSetChanged();
		datas = getDatas();
		animate(datas.get(0));


	}

	@Override
	public void onLoadedPlayOnHolder(MediaWrapper media) {

		System.out.println("play");
		System.out.println("MEDIA SAMPAI <<<<<<<<<<<<<<<<<");

		if(mService != null){
			System.out.println("location : "+media.getLocation());
			mService.load(media);
		}
		else{
			System.out.println("service null");
		}
	}

	public static HomeFragment getInsane(){
		return fragment;
	}
}
