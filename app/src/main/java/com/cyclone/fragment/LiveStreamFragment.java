package com.cyclone.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cyclone.DrawerActivity;
import com.cyclone.R;
import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.custom.OnOffsetChangedListener;
import com.cyclone.interfaces.PlayOnHolder;
import com.cyclone.model.Loading;
import com.cyclone.model.RunningProgram;
import com.cyclone.player.gui.PlaybackServiceRecyclerFragment;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaLibrary;
import com.cyclone.player.media.MediaWrapper;
import com.cyclone.player.media.MediaWrapperList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 06/11/2015.
 */
public class LiveStreamFragment extends PlaybackServiceRecyclerFragment implements PlayOnHolder {
	static LiveStreamFragment fragment;
	private Context mContext;
	private Activity mActivity;
	MediaLibrary mMediaLibrary;
	List<MediaWrapper> mAudioList;
	MediaWrapperList mwl;
	View mView;
	private boolean isLoading;
	private int maxData;
	private static final int pageSize = 5;


	public LiveStreamFragment(){}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMediaLibrary = MediaLibrary.getInstance();
		mContext = getContext();
		mActivity = getActivity();

		recuclerContext = mContext;

		mGetData = this;


		//getDataLive();
		//handler.postDelayed(runnable, 40000);
	}

	public Runnable runnable = new Runnable() {
		@Override
		public void run() {
			System.out.println("refresh");
			//getDataLive();
			//handler.postDelayed(runnable, 25000);
		}
	};

	Handler handler = new Handler();


	public static LiveStreamFragment newInstance(String json){
		fragment = new LiveStreamFragment();
		fragment.json = json;
		fragment.isLoading = false;
		fragment.maxData = pageSize;
		//playbackService = PlaybackService.getInstance();
		return fragment;
	}

	public static LiveStreamFragment getInsane(){
		return fragment;
	}

	@Override
	public List<Object> getDatas() {
		return parse(json);
	}

	@Override
	public void onCreateView(View v, ViewGroup parent, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		recyclerView.addOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				checkLoad();
			}
		});
	}

	private void checkLoad(){
//		System.out.println(((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() + " " + adapter.datas.size());
//		System.out.println(maxData + " " + UtilArrayData.contentLiveStreaming.size());
		if (((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition() == adapter.datas.size() - 1 && !isLoading && maxData < UtilArrayData.contentLiveStreaming.size()) {
			isLoading = true;
			adapter.datas.add(new Loading());
			adapter.notifyItemInserted(adapter.datas.size() - 1);
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					maxData += pageSize;
					showData();
					isLoading = false;
				}
			}, 3000);
		}
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
		try{
			adapter.datas.clear();
		} catch (Exception e){}

		if(UtilArrayData.contentLiveStreaming.size() > 0){
			datas.addAll(UtilArrayData.contentLiveStreaming);
		}

		while(datas.size() > maxData){
			datas.remove(datas.size() - 1);
		}
		/*else {
			List<Program> programs = new ArrayList<>();
			programs.add(new Program("", "The Dandees", "9am-12am", 5f));
			programs.add(new Program("", "Desta and Gina in The Morning", "7am-9am", 4.5f));
			programs.add(new Program("", "Popular Musics", "1pm-2pm", 4.2f));
			datas.add(new ProgramPager(programs, 1));
			datas.add(new RunningProgram("The Dandees", "The Most Wanted Guys In Town", ""));
			datas.add(new ProgramContent(ProgramContent.TYPE_MUSIC, "10:21", "Biru - Afgan"));
			datas.add(new ProgramContent(ProgramContent.TYPE_MUSIC, "10:18", "Stop Look Listen - " +
					"Patti Austin"));
			datas.add(new ProgramContent(ProgramContent.TYPE_COMMERCIAL, "10:12", "Indihouse Fiber " +
					"Spot"));
			datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station Prambors"));
			datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station 1"));
			datas.add(new ProgramContent(ProgramContent.TYPE_SOUND, "10:11", "ID Station Closing 1"));
		}*/

		return datas;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.streamplayer, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id){
			case R.id.btn_new_request:
				Intent i = new Intent(getContext(), DrawerActivity.class);
				i.putExtra("fragmentType", DrawerActivity.FRAGMENT_REQUEST);
				i.putExtra("title", "Request");
				getActivity().startActivity(i);
		}
		return super.onOptionsItemSelected(item);
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
	public void onDataLoadedLiveStreaming(List<Object> data) {
		System.out.println("jumlah Data : " + data.size());
		if(data.size() > 0){
			showData();
		}
		else{
			System.out.println("nol dalam Data");
			if(progressBar != null)
				progressBar.setVisibility(View.GONE);
			swipeLayout.setEnabled(true);
			swipeLayout.setRefreshing(false);
		}

		if(mService != null ){
			if(mService.isPlaying() && mService.getCurrentMediaLocation() == ServerUrl.ulr_stream){
				RunningProgram program = UtilArrayData.program;
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
				mService.getMediaList().getMedia(mService.getCurrentMediaPosition()).updateMeta(mMedia);
				mService.updateMetadataOnPlay();
			}

		}
	}

	@Override
	public void onLoadedPlayOnHolder(List<MediaWrapper> media) {

	}

	@Override
	public void onLoadedPlayOnHolder(int position) {

	}

	@Override
	public void onLoadedPlayOnHolder(String category, int position) {
		if(mService != null) {
			mAudioList = MediaLibrary.getInstance().getAudioItems();
			Object object = UtilArrayData.contentLiveStreaming;
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

