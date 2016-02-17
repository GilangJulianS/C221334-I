package com.cyclone.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.custom.UniversalAdapter;
import com.cyclone.data.GetData;
import com.cyclone.data.GoPlay;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.interfaces.onLoadMediaWrapper;
import com.cyclone.model.Content;
import com.cyclone.model.Section;
import com.cyclone.model.SubcategoryItem;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.media.MediaLibrary;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;

import org.videolan.libvlc.util.MediaBrowser;

import java.util.ArrayList;
import java.util.List;

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
	private List<Content> completeList;
	public HomeFragment(){}

	public static HomeFragment newInstance(String json){
		fragment = new HomeFragment();
		fragment.json = json;
		fragment.completeList = new ArrayList<>();
		fragment.playOnHolder = fragment;
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		mMediaLibrary = MediaLibrary.getInstance();
		mContext = getContext();
		mActivity = getActivity();
		setHasOptionsMenu(true);
	}

	@Override
	public void onResume() {
		super.onResume();
		DrawerActivity.setFragmentType(MasterActivity.FRAGMENT_HOME);
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
				if (mService != null) {
					mService.load(GoPlay.getInstance().PlayStream(ServerUrl.ulr_stream));
					mService.playIndex(0);
				}
			}
		});

		btnMix.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			//Toast.makeText(activity, "Mix for you pressed", Toast.LENGTH_SHORT).show();

			}
		});
	}

	public List<Object> parse(String json){
		//ambil data dari class GetData
		List<Object> datas = GetData.getInstance().showData(GetData.DATA_HOME);
		return datas;
	}

	public static HomeFragment getInsane(){
		return fragment;
	}

	@Override
	public void OnCoverLoaded(Bitmap bitmap) {

	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {

	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {;
		if(mService != null){
			mService.load(GoPlay.getInstance().getMedia(GoPlay.HOME_PLAY_ON_HOLDER, category), 0);
			mService.playIndex(position);
		} else{
			System.out.println("service null");
		}
	}

	@Override
	public void OnLoadComplite(List<MediaWrapper> mediaWrapperList) {

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu);

		SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				processQuery(newText);
				return true;
			}
		});

		super.onCreateOptionsMenu(menu, inflater);
	}

	public void processQuery(String query){
		UniversalAdapter newAdapter;
		if(query.equals("")){
			recyclerView.setAdapter(adapter);
		}else{
			newAdapter = new UniversalAdapter(activity, playOnHolder);
			List<Content> contentList;
			List<Content> searchResult = search(query);
			String currentType = "";
			while(searchResult.size() > 0){
				currentType = searchResult.get(0).tag;
				newAdapter.add(new Section(currentType, currentType, Section.TYPE_NONE, MasterActivity.FRAGMENT_GRID_MIX));
				newAdapter.add(new SubcategoryItem("", searchResult.get(0).txtPrimary, searchResult.get(0).txtSecondary));
				searchResult.remove(0);
				for(int i=0; i<searchResult.size(); i++){
					if(searchResult.get(i).tag.equals(currentType)){
						newAdapter.add(new SubcategoryItem("", searchResult.get(i).txtPrimary, searchResult.get(i).txtSecondary));
						searchResult.remove(i);
						i--;
					}
				}
			}
			recyclerView.setAdapter(newAdapter);
		}
	}

	public List<Content> search(String query) {
		List<Content> result = new ArrayList<>();
		for (Content c : completeList) {
			if (c.txtPrimary != null) {
				if (c.txtPrimary.toLowerCase().contains(query.toLowerCase()))
					result.add(c);
			}
			if (c.txtSecondary != null) {
				if (c.txtSecondary.toLowerCase().contains(query.toLowerCase()))
					result.add(c);
			}
			if (c.txtTertiary != null) {
				if (c.txtTertiary.toLowerCase().contains(query.toLowerCase()))
					result.add(c);
			}
		}
		return result;
	}
}
