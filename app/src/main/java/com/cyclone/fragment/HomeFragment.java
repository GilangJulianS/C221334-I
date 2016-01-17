package com.cyclone.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.interfaces.onLoadMediaWrapper;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.RunningProgram;
import com.cyclone.model.Section;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaLibrary;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;
import com.cyclone.service.ServiceGetData;

import org.videolan.libvlc.util.MediaBrowser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gilang on 20/11/2015.
 */
public class HomeFragment extends PlaybackServiceRecyclerFragment implements IgetCoverUrl, onLoadMediaWrapper, PlayOnHolder {

	MediaLibrary mMediaLibrary;
	private MediaBrowser mMediaBrowser;
	private ProgressDialog pDialog;
	private Context mContext;
	private Activity mActivity;
	static HomeFragment fragment;
	List<MediaWrapper> mAudioList;
	MediaWrapperList mwl;
	View mView;
	//private PlaybackService mService;


	public HomeFragment(){}

	public static HomeFragment newInstance(String json){

		fragment = new HomeFragment();
		fragment.json = json;
		//playbackService = PlaybackService.getInstance();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mMediaLibrary = MediaLibrary.getInstance();
		mContext = getContext();
		mActivity = getActivity();

		recuclerContext = mContext;
		mGetData = this;
	}



	@Override
	public List<Object> getDatas() {

		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		mView = v;
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
				if (mService != null) {

					if(mService != null && UtilArrayData.ContentLiveStreaming != null) {
						mAudioList = MediaLibrary.getInstance().getAudioItems();
						Object object = UtilArrayData.ContentLiveStreaming;
						RunningProgram program = UtilArrayData.program;
						if (mService != null) {
							MediaWrapper mMedia;
							MediaCustom MC;
							MediaDatabase mDB = MediaDatabase.getInstance();
							List<MediaWrapper> mw = new ArrayList<MediaWrapper>();
							MC = new MediaCustom();

							MC.setUri(Uri.parse(ServerUrl.ulr_stream));

							MC.setTitle(program.name);
							MC.setArtist(UtilArrayData.NAMA_RADIO);
							MC.setAlbum(program.description);
							MC.setAlbumArtist(UtilArrayData.NAMA_RADIO);
							MC.setArtworkURL("https://lh6.ggpht.com/cEwi4r2tcVC9neGWHxjt6ZLQ2TuAs_iPn3rL_YQAp4sZsit4dNHROrsH2Fk8gr94hlxw=w300");

							mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
									MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
									MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
									MC.getDiscNumber(), MC.getLastModified());

							mDB.addMedia(mMedia);

							mw.add(mMedia);

							mService.load(mMedia);
							mService.playIndex(0);
						}
					}
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

		//datas.clear();


		categoryList = new ArrayList<>();
		categoryList.add(new Category("Radio Content", "Radio Content"));
		categoryList.add(new Category("Music", "Music"));
		categoryList.add(new Category("Showlist", "Showlist"));
		categoryList.add(new Category("Uploaded", "Uploaded"));
		categories = new Categories(categoryList);
		datas.add(categories);

		datas.add(new Section("Latest News", "news", MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		if (UtilArrayData.ContentNews.size() > 0){

			for(int i = 0; i< 2; i++){
				contentList.add(UtilArrayData.ContentNews.get(i));
			}

			//contentList.addAll(UtilArrayData.ContentNews);
		}
		/*else{
			contentList.add(new Content("", "Latest News", "Dua Aturan Pemerintah", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
			contentList.add(new Content("", "Latest News", "Hampir 30 film", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
			//contentList.add(new Content("", "Latest News", "Melawan Asap", "Prambors FM Jakarta", "17 Sept 2015 - 10:05"));
		}*/

		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Talk", UtilArrayData.CATEGORY_TALK, MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		if(UtilArrayData.ContentTalk.size()>0){

			for(int i = 0; i< 2; i++){
				contentList.add(UtilArrayData.ContentTalk.get(i));
			}
			//contentList.addAll(UtilArrayData.ContentTalk);

		}
		contents = new Contents(contentList);
		datas.add(contents);


		datas.add(new Section("Hits Playlist", UtilArrayData.CATEGORY_HITS_LAYLIST, MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", UtilArrayData.CATEGORY_HITS_LAYLIST, "Morning Sunshine", "Dimas Danang", null, ""));
		contentList.add(new Content("", UtilArrayData.CATEGORY_HITS_LAYLIST, "Rock Yeah", "Imam Darto", null, ""));
		//contentList.add(new Content("", "Hits Playlist", "HipHopYo!", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Top mix", UtilArrayData.CATEGORY_TOP_MIX, MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", UtilArrayData.CATEGORY_TOP_MIX, "Mix max", "Nycta Gina", null, ""));
		contentList.add(new Content("", UtilArrayData.CATEGORY_TOP_MIX, "DUbldbldb", "Julio", null, ""));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Most Played Upload", UtilArrayData.CATEGORY_MOST_PLAYED, MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", UtilArrayData.CATEGORY_MOST_PLAYED, "Cover Love", "Dimas Danang", null, ""));
		contentList.add(new Content("", UtilArrayData.CATEGORY_MOST_PLAYED, "Cover Demons", "Imam Darto", null, ""));
		//contentList.add(new Content("", "Most Played Upload", "Cover Smells Like", "Desta", null));
		contents = new Contents(contentList);
		datas.add(contents);

		datas.add(new Section("Newly Uploaded", UtilArrayData.CATEGORY_NEWLY_UPLOAD, MasterActivity.FRAGMENT_SUBCATEGORY));
		contentList = new ArrayList<>();
		contentList.add(new Content("", UtilArrayData.CATEGORY_NEWLY_UPLOAD, "Cover Its-me", "Nycta Gina", null, ""));
		contentList.add(new Content("", UtilArrayData.CATEGORY_NEWLY_UPLOAD, "Cover Don't Look Back", "Julio", null, ""));
		contents = new Contents(contentList);
		datas.add(contents);

		return datas;
	}

	private void updateLists() {

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
	public void onDataLoadedHome(Map<String, List> data) {
		System.out.println("JSON DITERIMA<<<<<<<<<<<<");
		//List<Content> cnt = new ArrayList<>();
		System.out.println("jumlah MAP : " + data.size());
		//DrawerActivity.refresh();
		showData();
		/*if(data.size() > 0){
			*//*if(data.get("news").size() > 0){
				UtilArrayData.ContentNews.clear();
				UtilArrayData.ContentNews = data.get("news");
			}

			if(data.get("talk").size() > 0){
				UtilArrayData.ContentTalk.clear();
				UtilArrayData.ContentTalk = data.get("talk");
			}*//*

			if(progressBar != null)
				progressBar.setVisibility(View.GONE);

			*//*adapter.datas.clear();
			adapter.notifyDataSetChanged();
			datas.clear();
			System.out.println("Size get data : "+getDatas().size());
			datas = getDatas();
			cnt = 0;
			animate(datas.get(0));*//*

			showData();

		*//*	Intent intent = new Intent(activity, DrawerActivity.class);
			intent.putExtra("parent", true);
			intent.putExtra("fragmentType", MasterActivity.FRAGMENT_HOME);
			intent.putExtra("menuId", 0);
			//DrawerActivity.getmContext().startActivity(intent);
		//	DrawerActivity.getmActivity().finish();
			activity.startActivity(intent);
			activity.finish();*//*

		}
		else{
			System.out.println("nol dalam MAP");
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);
			swipeLayout.setEnabled(true);
			swipeLayout.setRefreshing(false);
			//animate(datas.get(0));
		}*/

	}

	@Override
	public void onDataLoadedHomeCancel() {
		Snackbar.make(getView(), "Connection failed. Check your internet connection.", Snackbar.LENGTH_SHORT).show();
		swipeLayout.setEnabled(true);
		swipeLayout.setRefreshing(false);
		if(progressBar != null)
			progressBar.setVisibility(View.GONE);
	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

		/*System.out.println("play");
		System.out.println("MEDIA SAMPAI <<<<<<<<<<<<<<<<<");

		if(mService != null){
			System.out.println("location : "+media.get(0).getLocation());
			mService.load(media, 0);
			mService.playIndex(0);
		}
		else{
			System.out.println("service null Dari drawerActivity");
		}*/

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {

	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {
		System.out.println("onLoadedPlayOnHolder");
		System.out.println("category :" + category);
		if(mService != null){
			List<MediaWrapper> media = new ArrayList<MediaWrapper>();
			MediaDatabase mDB = MediaDatabase.getInstance();
			List<Content> content;
			Uri myUri ;
			MediaCustom MC;
			MediaWrapper mMedia;

			if(category.equals(UtilArrayData.CATEGORY_NEWS)){
				content = UtilArrayData.ContentNews;
				System.out.println(category +" : "+UtilArrayData.CATEGORY_NEWS);
				for (int i = 0; i<content.size(); i++){
					myUri = Uri.parse(content.get(i).music_url);
					MC = new MediaCustom();
					MC.setUri(myUri);
					MC.setTitle(content.get(i).txtPrimary);
					MC.setArtist(content.get(i).txtSecondary);
					MC.setAlbum(content.get(i).txtPrimary);
					MC.setAlbumArtist(content.get(i).txtSecondary);
					MC.setArtworkURL(content.get(i).imgUrl);
					mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
							MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
							MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
							MC.getDiscNumber(), MC.getLastModified());
					mDB.addMedia(mMedia);
					media.add(mMedia);
				}
			}
			else if(category.equals(UtilArrayData.CATEGORY_TALK)){
				content = UtilArrayData.ContentTalk;
				System.out.println(category +" : "+UtilArrayData.CATEGORY_TALK);
				for (int i = 0; i<content.size(); i++){
					myUri = Uri.parse(content.get(i).music_url);
					MC = new MediaCustom();
					MC.setUri(myUri);
					MC.setTitle(content.get(i).txtPrimary);
					MC.setArtist(content.get(i).txtSecondary);
					MC.setAlbum(content.get(i).txtPrimary);
					MC.setAlbumArtist(content.get(i).txtSecondary);
					MC.setArtworkURL(content.get(i).imgUrl);
					mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
							MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
							MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
							MC.getDiscNumber(), MC.getLastModified());
					mDB.addMedia(mMedia);
					media.add(mMedia);
				}
			}
			else{
				System.out.println("no category");
			}
			System.out.println("location : "+media.get(0).getLocation());
			System.out.println("posis : "+position);
			mService.load(media, 0);
			mService.playIndex(position);
		}
		else{
			System.out.println("service null");
		}
	}

	public static HomeFragment getInsane(){
		return fragment;
	}


/*	@Override
	public void onConnected(PlaybackService service) {
		mService = service;
	}

	@Override
	public void onDisconnected() {
		mService = null;
	}*/
}
