package com.cyclone.custom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cyclone.DrawerActivity;
import com.cyclone.MasterActivity;
import com.cyclone.R;
import com.cyclone.model.RunningProgram;
import com.cyclone.player.MediaDatabase;
import com.cyclone.player.audio.AudioServiceController;
import com.cyclone.player.audio.costumMedia;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilang on 07/11/2015.
 */
public class RunningProgramHolder extends UniversalHolder {

	public TextView txtPrimary;
	public TextView txtSecondary;
	public ImageButton btnPlay;

	private AudioServiceController mAudioController;
	private LibVLC mLibVLC;

	public RunningProgramHolder(View v, Activity activity) {
		super(v, activity);
		txtPrimary = (TextView) v.findViewById(R.id.txt_primary);
		txtSecondary = (TextView) v.findViewById(R.id.txt_secondary);
		btnPlay = (ImageButton) v.findViewById(R.id.btn_play);
	}

	@Override
	public void bind(Object object, Activity activity, int position) {
		bind((RunningProgram)object, activity);
	}

	public void bind(RunningProgram program, final Activity activity){
		txtPrimary.setText(program.name);
		txtSecondary.setText(program.description);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent i = new Intent(activity, DrawerActivity.class);
				i.putExtra("layout", DrawerActivity.LAYOUT_PLAYER);
				i.putExtra("activity", R.layout.activity_drawer);
				activity.startActivity(i);

				mAudioController = AudioServiceController.getInstance();

				List<Media> alMedia = new ArrayList<Media>();

				Media m;

				MediaDatabase mDB = MediaDatabase.getInstance();

				costumMedia cm = new costumMedia();
				cm.setLocation(MasterActivity.ulr_stream);
				cm.setTitle("Judul Acara");
				cm.setArtist("K-Lite");
				cm.setAlbum("Album");
				cm.setArtist("albumArtist");
				cm.setArtworkURL("https://pbs.twimg.com/profile_images/1643172883/Logo_K-Lite_FM.PNG");

				m = new Media(cm.getLocation(), cm.getTime(), cm.getLength(), cm.getType(),
						cm.getPicture(), cm.getTitle(), cm.getArtist(), cm.getGenre(), cm.getAlbum(), cm.getAlbumArtist(),
						cm.getWidth(), cm.getHeight(), cm.getArtworkURL(), cm.getAudio(), cm.getSpu(), cm.getTrackNumber());

				mDB.addMedia(m);
				alMedia.add(m);
				mAudioController.loadMedia(alMedia);
				List<String> mediaLocation = new ArrayList<String>();
				mediaLocation.add(alMedia.get(0).getLocation());
				mAudioController.load(mediaLocation, 0);


			}
		});
	}
}
