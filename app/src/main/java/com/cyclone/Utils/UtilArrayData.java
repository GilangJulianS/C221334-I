package com.cyclone.Utils;

import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.Profile;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.model.radioProgram;
import com.cyclone.model.Content;
import com.cyclone.model.RunningProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solusi247 on 15/12/15.
 */
public class UtilArrayData {

    public static final String CATEGORY_NEWS = "news";
    public static final String CATEGORY_TALK = "talk";
    public static final String CATEGORY_HITS_LAYLIST = "hits";
    public static final String CATEGORY_TOP_MIX = "mix";
    public static final String CATEGORY_MOST_PLAYED = "popular_upload";
    public static final String CATEGORY_NEWLY_UPLOAD = "new_upload";
    public static final String CATEGORY_LIVE_STREAMING = "live_streaming";

    public static final String NAMA_RADIO= "K-Lite FM";

    public static List<Content> ContentNews = new ArrayList<>();
    public static List<Content> ContentTalk = new ArrayList<>();
    public static List<Object> ContentLiveStreaming = new ArrayList<>();
    public static List<FeedTimeline> feedTimelines = new ArrayList<>();
    public static List<radioProgram> radioPrograms = new ArrayList<>();
    public static List<comment> commentList = new ArrayList<>();
    public static RunningProgram program = null;
    public static Profile CurrentProfile = null;

    public static int curPosPlaying = 0;

    public static String TitleAdd;

}
