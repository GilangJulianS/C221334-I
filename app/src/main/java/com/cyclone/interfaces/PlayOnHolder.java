package com.cyclone.interfaces;

import com.cyclone.player.media.MediaWrapper;

import java.util.List;

/**
 * Created by solusi247 on 15/12/15.
 */
public interface PlayOnHolder {
    void onLoadedPlayOnHolder(List<MediaWrapper> media);
    void onLoadedPlayOnHolder(int position);
    void onLoadedPlayOnHolder(String category, int position);

}
