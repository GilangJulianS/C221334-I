package com.cyclone.player.interfaces;

import org.videolan.libvlc.MediaList;

/**
 * Created by solusi247 on 18/11/15.
 */
public interface IMediaList {

    void loadMediaList(MediaList mediaPathList, int position, boolean noVideo);
}
