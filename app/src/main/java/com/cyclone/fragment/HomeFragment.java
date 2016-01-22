package com.cyclone.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.data.GetData;
import com.cyclone.data.GoPlay;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.interfaces.onLoadMediaWrapper;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.interfaces.IgetCoverUrl;
import com.cyclone.player.media.MediaLibrary;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;

import org.videolan.libvlc.util.MediaBrowser;

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
	public HomeFragment(){}

	public static HomeFragment newInstance(String json){
		fragment = new HomeFragment();
		fragment.json = json;
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
					mService.load(GoPlay.getInstance().PlayStream());
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
		return GetData.getInstance().showData(GetData.DATA_HOME);
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
}
