package com.cyclone.player;

import com.cyclone.DrawerActivity;
import com.cyclone.player.media.MediaWrapper;

import org.videolan.libvlc.util.MediaBrowser;

import java.util.ArrayList;

/**
 * Created by solusi247 on 11/12/15.
 */
public class AudioController {

    private static AudioController mInsane = null;

    private static PlaybackService playbackService;

    private MediaBrowser mMediaBrowser;
    private DrawerActivity mMainActivity;

    ArrayList<MediaWrapper> mTracksToAppend = new ArrayList<MediaWrapper>();

    public AudioController(){
        mInsane = this;
    }

    public AudioController getInsane(){
        if(mInsane == null)
            new AudioController();
        return mInsane;
    }

    public static void setPlabackService(PlaybackService pbs){
        playbackService = pbs;
    }

    public static PlaybackService getPlaybackService(){
        if(mInsane == null)
            new AudioController();
        return playbackService;
    }

}
