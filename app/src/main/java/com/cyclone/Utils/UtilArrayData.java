package com.cyclone.Utils;

import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.Profile;
import com.cyclone.loopback.model.RadioContent;
import com.cyclone.loopback.model.comment;
import com.cyclone.loopback.model.radioProfile;
import com.cyclone.loopback.model.radioProgram;
import com.cyclone.model.Content;
import com.cyclone.model.RunningProgram;
import com.cyclone.loopback.model.Music;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solusi247 on 15/12/15.
 */
public class UtilArrayData {

    public static final String CATEGORY_NEWS = "cyclone.news";
    public static final String CATEGORY_TALK = "cyclone.talk";
    public static final String CATEGORY_LatestContent = "cyclone.latestContent";
    public static final String CATEGORY_Info = "cyclone.info";
    public static final String CATEGORY_Variety = "cyclone.variety";
    public static final String CATEGORY_Travel = "cyclone.travel";
    public static final String CATEGORY_Advertorial = "cyclone.cyclone.advertorial";
    public static final String CATEGORY_HITS_LAYLIST = "cyclone.hits";
    public static final String CATEGORY_TOP_MIX = "cyclone.mix";
    public static final String CATEGORY_MOST_PLAYED = "cyclone.popular_upload";
    public static final String CATEGORY_NEWLY_UPLOAD = "cyclone.new_upload";
    public static final String CATEGORY_LIVE_STREAMING = "cyclone.live_streaming";
    public static final String CATEGORY_POP = "cyclone.pop";
    public static final String CATEGORY_POP_INDO = "cyclone.pop_indo";
    public static final String CATEGORY_DANCE = "cyclone.dance";
    public static final String CATEGORY_HIP_HOP_RAP = "cyclone.hiphoprap";
    public static final String CATEGORY_MUSIC = "cyclone.music";
    public static final String CATEGORY_RADIO_CONTENT = "cyclone.radio_content";
    public static final String CATEGORY_SHOWLIST = "cyclone.showlist";
    public static final String CATEGORY_UPLOAD = "cyclone.upload";

    public static final String NAMA_RADIO= "K-Lite FM";

    public static List<RadioContent> AllRadioContent = new ArrayList<>();
    public static List<RadioContent> LatestContent = new ArrayList<>();
    public static List<RadioContent> News = new ArrayList<>();
    public static List<RadioContent> Info = new ArrayList<>();
    public static List<RadioContent> Variety = new ArrayList<>();
    public static List<RadioContent> Travel = new ArrayList<>();
    public static List<RadioContent> Advertorial = new ArrayList<>();

    public static List<Music> Music = new ArrayList<>();
    public static List<Music> Pop = new ArrayList<>();
    public static List<Music> Indo_Pop = new ArrayList<>();
    public static List<Music> Dance = new ArrayList<>();
    public static List<Music> Hip_Hop_Rap = new ArrayList<>();

    public static List<Content> ContentNews = new ArrayList<>();
    public static List<Content> ContentTalk = new ArrayList<>();
    public static List<Object> ContentLiveStreaming = new ArrayList<>();
    public static List<FeedTimeline> feedTimelines = new ArrayList<>();
    public static List<radioProgram> radioPrograms = new ArrayList<>();
    public static List<comment> commentList = new ArrayList<>();
    public static RunningProgram program = null;
    public static Profile CurrentProfile = null;
    public static radioProfile rdioProfile = null;

    public static int curPosPlaying = 0;

    public static String TitleAdd;

}
