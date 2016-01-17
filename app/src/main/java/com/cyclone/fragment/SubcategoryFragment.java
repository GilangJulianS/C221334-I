package com.cyclone.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.model.Content;
import com.cyclone.model.SubcategoryItem;
import com.cyclone.player.gui.MediaBrowserRecyclerFragment;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaWrapper;

import org.videolan.libvlc.Media;
import org.videolan.libvlc.util.MediaBrowser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gilang on 21/11/2015.
 */
public class SubcategoryFragment extends MediaBrowserRecyclerFragment implements MediaBrowser.EventListener, PlayOnHolder {
	public static SubcategoryFragment fragment;
	public SubcategoryFragment(){}

	public static SubcategoryFragment newInstance(String json){
		fragment = new SubcategoryFragment();
		fragment.json = json;
		return fragment;
	}

	public static SubcategoryFragment getInstance(){
		return fragment;
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
		if(json.equals(UtilArrayData.CATEGORY_NEWS)){
			for(int i =0; i < UtilArrayData.ContentNews.size(); i++){
				datas.add(new SubcategoryItem(UtilArrayData.ContentNews.get(i).imgUrl, UtilArrayData.ContentNews.get(i).txtPrimary, UtilArrayData.ContentNews.get(i).txtSecondary, UtilArrayData.ContentNews.get(i).music_url, UtilArrayData.ContentNews.get(i).tag, ""));
			}
			//datas.addAll(UtilArrayData.ContentNews);
		}
		else if(json.equals(UtilArrayData.CATEGORY_TALK)){
			for(int i =0; i < UtilArrayData.ContentTalk.size(); i++){
				datas.add(new SubcategoryItem(UtilArrayData.ContentTalk.get(i).imgUrl, UtilArrayData.ContentTalk.get(i).txtPrimary, UtilArrayData.ContentTalk.get(i).txtSecondary, UtilArrayData.ContentTalk.get(i).music_url, UtilArrayData.ContentTalk.get(i).tag, ""));
			}
		}
		else{
			datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson", ""));
			datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons", ""));
			datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta", ""));
			datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo", ""));
			datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake", ""));
			datas.add(new SubcategoryItem("", "Love never felt so good", "Michael Jackson", ""));
			datas.add(new SubcategoryItem("", "Demons", "Imagine Dragons", ""));
			datas.add(new SubcategoryItem("", "Vio Hotel Jakarta", "Hotel Jakarta", ""));
			datas.add(new SubcategoryItem("", "Tattoo", "Jason Derulo", ""));
			datas.add(new SubcategoryItem("", "Suit & Tie", "Justin Timberlake", ""));
		}

		return datas;
	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {
		if(mService != null){
			List<MediaWrapper> media = new ArrayList<MediaWrapper>();
			MediaDatabase mDB = MediaDatabase.getInstance();
			List<Content> content;
			Uri myUri ;
			MediaCustom MC;
			MediaWrapper mMedia;
			if(category.equals(UtilArrayData.CATEGORY_NEWS)){
				content = UtilArrayData.ContentNews;
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
			if(category.equals(UtilArrayData.CATEGORY_TALK)){
				content = UtilArrayData.ContentTalk;
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
			System.out.println("location : "+media.get(0).getLocation());
			mService.load(media, 0);
			mService.playIndex(position);
		}
		else{
			System.out.println("service null");
		}
	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {


	}

	@Override
	protected void display() {

	}

	@Override
	public void clear() {

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

	@Override
	public void onDataLoadedHome(Map<String, List> data) {
		System.out.println("JSON DITERIMA<<<<<<<<<<<<");
		System.out.println("jumlah MAP : " + data.size());
		showData();
	}
}
