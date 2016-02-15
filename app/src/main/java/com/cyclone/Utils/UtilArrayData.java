package com.cyclone.Utils;

import com.cyclone.loopback.model.Favorite;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.PlaylistAccount;
import com.cyclone.loopback.model.PlaylistData;
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
    public static final String CATEGORY_PLAYLIST = "cyclone.playlist";

    public static final String NAMA_RADIO= "K-Lite FM";

    public static List<RadioContent> allRadioContent = new ArrayList<>();
    public static List<RadioContent> latestContent = new ArrayList<>();
    public static List<RadioContent> news = new ArrayList<>();
    public static List<RadioContent> info = new ArrayList<>();
    public static List<RadioContent> variety = new ArrayList<>();
    public static List<RadioContent> travel = new ArrayList<>();
    public static List<RadioContent> advertorial = new ArrayList<>();

    public static List<Music> music = new ArrayList<>();
    public static List<Music> pop = new ArrayList<>();
    public static List<Music> indoPop = new ArrayList<>();
    public static List<Music> dance = new ArrayList<>();
    public static List<Music> hipHopRap = new ArrayList<>();

    public static List<Content> contentNews = new ArrayList<>();
    public static List<Content> contentTalk = new ArrayList<>();
    public static List<Object> contentLiveStreaming = new ArrayList<>();
    public static List<FeedTimeline> feedTimelines = new ArrayList<>();
    public static List<radioProgram> radioPrograms = new ArrayList<>();
    public static List<comment> commentList = new ArrayList<>();
    public static List<Object> requestList = new ArrayList<>();
    public static List<PlaylistAccount> playlistAccount = new ArrayList<>();
    public static List<PlaylistData> playlistData = new ArrayList<>();
    public static List<Favorite> favorites = new ArrayList<>();
    public static RunningProgram program = null;
    public static Profile currentProfile = null;
    public static radioProfile radioProfile = null;

    public static int curPosPlaying = 0;

    public static String TitleAdd;

}
