package com.cyclone.loopback;

import android.support.v4.app.Fragment;

import com.cyclone.interfaces.getData;

import java.util.List;
import java.util.Map;

/**
 * Created by solusi247 on 15/12/15.
 */
public class GetJsonFragment extends Fragment implements getData {

    @Override
    public void onDataLoadedHome(Map<String, List> data) {

    }

    @Override
    public void onDataLoadedLiveStreaming(List<Object> data) {

    }

    @Override
    public void onDataLoadedHomeCancel() {

    }
}
