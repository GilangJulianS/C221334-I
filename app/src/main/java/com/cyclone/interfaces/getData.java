package com.cyclone.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Created by solusi247 on 15/12/15.
 */
public interface getData {
    void onDataLoadedHome(Map<String, List> data);
    void onDataLoadedLiveStreaming(List<Object> data);
    void onDataLoadedHomeCancel();
}
