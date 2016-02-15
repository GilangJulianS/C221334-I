package com.cyclone.data;

import com.cyclone.MasterActivity;
import com.cyclone.Utils.TimeFormat;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.fragment.SubcategoryFragment;
import com.cyclone.loopback.model.Favorite;
import com.cyclone.loopback.model.Music;
import com.cyclone.loopback.model.Playlist;
import com.cyclone.loopback.model.PlaylistData;
import com.cyclone.loopback.model.RadioContent;
import com.cyclone.model.Categories;
import com.cyclone.model.Category;
import com.cyclone.model.Content;
import com.cyclone.model.Contents;
import com.cyclone.model.Section;
import com.cyclone.model.SubcategoryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solusi247 on 22/01/16.
 */
public class GetData {
    public static int DATA_HOME = 0x607d;
    public static int DATA_FEED = 0x607e;
    public static int DATA_USER_PROFILE = 0x607f;
    public static int DATA_USER_PROFILE_OTHER = 0x6080;
    public static int DATA_RADIO_PROFILE = 0x6081;
    public static int DATA_LIVE_STREAMING = 0x6082;
    public static int DATA_PLAYER = 0x6083;
    public static int DATA_SUB_CATEGORY = 0x6084;
    public static int DATA_CATEGORY = 0x6085;
    public static int DATA_PLAYLIST = 0x3c4e2;
    public static int DATA_FAVORITE = 0x3c4e3;

    private static GetData Instance = null;

    public static GetData getInstance(){
        if(Instance == null){
            Instance = new GetData();
        }
        return Instance;
    }

    public void getData(){

    }
    public List<Object> showData(int typedata){
        return showData(typedata,"");
    }

    public List<Object> showData(int typedata, int category) {
        return showData(typedata, "" + category);
    }

    public List<Object> showData(int typedata, String category){
        System.out.println("on get data - showdata");
        System.out.println("tipe : " + typedata);
        if(typedata == DATA_HOME){
            Contents contents;
            Categories categories;
            List<Object> datas = new ArrayList<>();
            List<Category> categoryList;
            List<Content> contentList;

            categoryList = new ArrayList<>();
            categoryList.add(new Category("Radio Content", UtilArrayData.CATEGORY_RADIO_CONTENT));
            categoryList.add(new Category("Music", UtilArrayData.CATEGORY_MUSIC));
            categoryList.add(new Category("Showlist", UtilArrayData.CATEGORY_SHOWLIST));
            categoryList.add(new Category("Uploaded", UtilArrayData.CATEGORY_UPLOAD));
            categories = new Categories(categoryList);
            datas.add(categories);

            if(UtilArrayData.latestContent.size() > 0){
                datas.add(new Section("Latest Content", UtilArrayData.CATEGORY_LatestContent, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    RadioContent radioContent = UtilArrayData.latestContent.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_LatestContent,
                            Content.NOT_FAVORITABLE, radioContent.getName(),
                            UtilArrayData.radioProfile.getName(),
                            TimeFormat.toDateHours(radioContent.getUpdatedAt()), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(), i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.news.size() > 0){
                datas.add(new Section("News", UtilArrayData.CATEGORY_NEWS, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    RadioContent radioContent = UtilArrayData.news.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_NEWS,
                            Content.NOT_FAVORITABLE, radioContent.getName(),
                            UtilArrayData.radioProfile.getName(),
                            TimeFormat.toDateHours(radioContent.getUpdatedAt()), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(), i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.info.size() > 0){
                datas.add(new Section("Info", UtilArrayData.CATEGORY_Info, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    RadioContent radioContent = UtilArrayData.info.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Info, Content.NOT_FAVORITABLE,
                            radioContent.getName(), UtilArrayData.radioProfile.getName(),
                            TimeFormat.toDateHours(radioContent.getUpdatedAt()), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(), i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.advertorial.size() > 0){
                datas.add(new Section("Advertorial", UtilArrayData.CATEGORY_Advertorial, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    try{
                        RadioContent radioContent = UtilArrayData.advertorial.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Advertorial,
                                Content.NOT_FAVORITABLE, radioContent.getName(),
                                UtilArrayData.radioProfile.getName(),
                                TimeFormat.toDateHours(radioContent.getUpdatedAt()), false, Content.TYPE_RADIO_CONTENT,
                                radioContent.getAudio(),i, radioContent.getId()));
                    }catch (Exception e){}

                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.pop.size() > 0){
                datas.add(new Section("Pop", UtilArrayData.CATEGORY_POP, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    try{
                        Music music = UtilArrayData.pop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_TRACKS, music.getAudio(), i, music.getId()));
                    }catch (Exception e){}
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.indoPop.size() > 0){
                datas.add(new Section("Pop Indo", UtilArrayData.CATEGORY_POP_INDO, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    try{
                        Music music = UtilArrayData.indoPop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP_INDO, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_TRACKS, music.getAudio(), i, music.getId()));
                    }catch (Exception e){}
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.hipHopRap.size() > 0){
                datas.add(new Section("Hip Hop/Rap", UtilArrayData.CATEGORY_HIP_HOP_RAP, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    try{
                        Music music = UtilArrayData.hipHopRap.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_HIP_HOP_RAP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_TRACKS, music.getAudio(), i, music.getId()));
                    }catch (Exception e){

                    }
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            return datas;
        }else if(typedata == DATA_SUB_CATEGORY){
            System.out.println("DATA SUB CATEGORY");
            System.out.println("category : "+category);
            List<Object> datas = new ArrayList<>();
            if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_LatestContent)){
                if(UtilArrayData.latestContent.size() > 0){
                    for(int i = 0; i < UtilArrayData.latestContent.size(); i++){
                        RadioContent radioContent = UtilArrayData.latestContent.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                        System.out.println("judul : " + radioContent.getName());
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_NEWS)){
                if(UtilArrayData.news.size() > 0){
                    for(int i = 0; i < UtilArrayData.news.size(); i++){
                        RadioContent radioContent = UtilArrayData.news.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Info)){
                if(UtilArrayData.info.size() > 0){
                    for(int i = 0; i < UtilArrayData.info.size(); i++){
                        RadioContent radioContent = UtilArrayData.info.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Variety)){
                if(UtilArrayData.variety.size() > 0){
                    for(int i = 0; i < UtilArrayData.variety.size(); i++){
                        RadioContent radioContent = UtilArrayData.variety.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Travel)){
                if(UtilArrayData.travel.size() > 0){
                    for(int i = 0; i < UtilArrayData.travel.size(); i++){
                        RadioContent radioContent = UtilArrayData.travel.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Advertorial)){
                if(UtilArrayData.advertorial.size() > 0){
                    for(int i = 0; i < UtilArrayData.advertorial.size(); i++){
                        RadioContent radioContent = UtilArrayData.advertorial.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_POP)){
                if(UtilArrayData.pop.size() > 0){
                    for(int i = 0; i < UtilArrayData.pop.size(); i++){
                        Music music = UtilArrayData.pop.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_POP_INDO)){
                if(UtilArrayData.indoPop.size() > 0){
                    for(int i = 0; i < UtilArrayData.indoPop.size(); i++){
                        Music music = UtilArrayData.indoPop.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_DANCE)){
                if(UtilArrayData.dance.size() > 0){
                    for(int i = 0; i < UtilArrayData.dance.size(); i++){
                        Music music = UtilArrayData.dance.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_HIP_HOP_RAP)){
                if(UtilArrayData.hipHopRap.size() > 0){
                    for(int i = 0; i < UtilArrayData.hipHopRap.size(); i++){
                        Music music = UtilArrayData.hipHopRap.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                    }
                }
            } else if (category.equalsIgnoreCase(Section.CATEGORY_FAFORITE)) {
                if (SubcategoryFragment.getInstance().getTypeFavorite() == Content.TYPE_TRACKS) {
                    if (UtilArrayData.favorites.size() > 0) {
                        List<Favorite> list = UtilArrayData.favorites;
                        for (int i = 0; i < list.size(); i++) {
                            Music music = list.get(i).getMusic();
                            datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                    music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                    music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                        }
                    }
                } else if (SubcategoryFragment.getInstance().getTypeFavorite() == Content.TYPE_RADIO_CONTENT) {
                    if (UtilArrayData.favorites.size() > 0) {
                        List<Favorite> list = UtilArrayData.favorites;
                        for (int i = 0; i < list.size(); i++) {
                            RadioContent radioContent = list.get(i).getRadioContent();
                            datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                    UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                    radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                        }
                    }
                } else if (SubcategoryFragment.getInstance().getTypeFavorite() == Content.TYPE_PLAYLIST) {
                    if (UtilArrayData.favorites.size() > 0) {
                        List<Favorite> list = UtilArrayData.favorites;
                        for (int i = 0; i < list.size(); i++) {
                            Playlist playlist = list.get(i).getPlaylist();
                            datas.add(new SubcategoryItem(playlist.getImage(), playlist.getName(),
                                    playlist.getCaption(), SubcategoryItem.TYPE_NORMAL, category,
                                    "", i, Content.TYPE_PLAYLIST, playlist.getId()));
                        }
                    }
                }

            }else{
                System.out.println("category not found...");
            }
            return datas;
        }else if(typedata == DATA_CATEGORY){
            if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_RADIO_CONTENT)){
                Contents contents;
                List<Object> datas = new ArrayList<>();
                List<Content> contentList;
                if(UtilArrayData.latestContent.size() > 0){
                    datas.add(new Section("Latest Content", UtilArrayData.CATEGORY_LatestContent, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.latestContent.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_LatestContent, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.news.size() > 0){
                    datas.add(new Section("News", UtilArrayData.CATEGORY_NEWS, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.news.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_NEWS, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.info.size() > 0){
                    datas.add(new Section("Info", UtilArrayData.CATEGORY_Info, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.info.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Info, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.variety.size() > 0){
                    datas.add(new Section("Variety", UtilArrayData.CATEGORY_Variety, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.variety.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Variety, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.travel.size() > 0){
                    datas.add(new Section("Travel", UtilArrayData.CATEGORY_Travel, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.travel.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Travel, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.advertorial.size() > 0){
                    datas.add(new Section("Advertorial", UtilArrayData.CATEGORY_Advertorial, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.advertorial.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Advertorial, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.radioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }
                return datas;
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_MUSIC)){
                Contents contents;
                List<Object> datas = new ArrayList<>();
                List<Content> contentList;

                if(UtilArrayData.pop.size() > 0){
                    datas.add(new Section("Pop", UtilArrayData.CATEGORY_POP, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.pop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.indoPop.size() > 0){
                    datas.add(new Section("Pop Indo", UtilArrayData.CATEGORY_POP_INDO, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.indoPop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP_INDO, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.dance.size() > 0){
                    datas.add(new Section("Dance", UtilArrayData.CATEGORY_DANCE, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.dance.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_DANCE, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.hipHopRap.size() > 0){
                    datas.add(new Section("Hip Hop/Rap", UtilArrayData.CATEGORY_HIP_HOP_RAP, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        try{
                            Music music = UtilArrayData.hipHopRap.get(i);
                            contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_HIP_HOP_RAP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                        }catch (Exception e){

                        }
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }
                return datas;
            }else{
                List<Object> datas = new ArrayList<>();
                return datas;
            }
        } else if (typedata == DATA_PLAYLIST) {
            System.out.println("sampai di get data playlist");
            List<Object> datas = new ArrayList<>();
            List<PlaylistData> list = UtilArrayData.playlistData;
            for (int i = 0; i < list.size(); i++) {
                PlaylistData playlistData = list.get(i);
                if (playlistData.getTypeContent() == PlaylistData.TYPE_RADIOCONTENT) {
                    com.cyclone.model.RadioContent radioContent = playlistData.getRadioContent();
                    datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                            UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, UtilArrayData.CATEGORY_PLAYLIST,
                            radioContent.getAudio(), i, Content.TYPE_RADIO_CONTENT, radioContent.getId()));
                }

                if (playlistData.getTypeContent() == PlaylistData.TYPE_MUSIC) {
                    com.cyclone.model.Music music = playlistData.getMusic();
                    datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                            UtilArrayData.radioProfile.getName(), SubcategoryItem.TYPE_NORMAL, UtilArrayData.CATEGORY_PLAYLIST,
                            music.getAudio(), i, Content.TYPE_TRACKS, music.getId()));
                }
            }

            return datas;
        } else if (typedata == DATA_FAVORITE) {
            List<Object> datas = new ArrayList<>();
            List<Content> contentList;
            List<Favorite> favoriteListMusic = new ArrayList<>();
            List<Favorite> favoriteListPlaylist = new ArrayList<>();
            List<Favorite> favoriteListRadiocontent = new ArrayList<>();
            Contents contents;

            if (UtilArrayData.favorites.size() > 0) {
                List<Favorite> list = UtilArrayData.favorites;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getType() == Favorite.TYPE_MUSIC)
                        favoriteListMusic.add(list.get(i));
                    else if (list.get(i).getType() == Favorite.TYPE_PLAYLIST)
                        favoriteListPlaylist.add(list.get(i));
                    else if (list.get(i).getType() == Favorite.TYPE_RADIOCONTENT)
                        favoriteListRadiocontent.add(list.get(i));
                }

                contentList = new ArrayList<>();
                datas.add(new Section("Music", Content.TYPE_TRACKS, Section.CATEGORY_FAFORITE, MasterActivity.FRAGMENT_SUBCATEGORY));
                for (int i = 0; i < favoriteListMusic.size(); i++) {
                    Favorite favorite = favoriteListMusic.get(i);
                    contentList.add(new Content(favorite.getMusic().getCoverArt(),
                            UtilArrayData.CATEGORY_MUSIC,
                            Content.NOT_FAVORITABLE,
                            favorite.getMusic().getName(),
                            favorite.getMusic().getArtist(),
                            favorite.getUpdatedAt(),
                            false,
                            Content.TYPE_TRACKS,
                            favorite.getMusic().getAudio(),
                            i,
                            favorite.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);

                contentList = new ArrayList<>();
                datas.add(new Section("Playlist", Content.TYPE_PLAYLIST, Section.CATEGORY_FAFORITE, MasterActivity.FRAGMENT_SUBCATEGORY));
                for (int i = 0; i < favoriteListPlaylist.size(); i++) {
                    Favorite favorite = favoriteListPlaylist.get(i);
                    contentList.add(new Content("" + favorite.getPlaylist().getImage(),
                            UtilArrayData.CATEGORY_PLAYLIST,
                            Content.NOT_FAVORITABLE,
                            favorite.getPlaylist().getName(),
                            favorite.getPlaylist().getCaption(),
                            favorite.getUpdatedAt(),
                            false,
                            Content.TYPE_PLAYLIST,
                            "",
                            i,
                            favorite.getPlaylist().getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);

                contentList = new ArrayList<>();
                datas.add(new Section("Radio Content", Content.TYPE_RADIO_CONTENT, Section.CATEGORY_FAFORITE, MasterActivity.FRAGMENT_SUBCATEGORY));
                for (int i = 0; i < favoriteListRadiocontent.size(); i++) {
                    Favorite favorite = favoriteListRadiocontent.get(i);
                    contentList.add(new Content(favorite.getRadioContent().getCoverArt(),
                            UtilArrayData.CATEGORY_RADIO_CONTENT,
                            Content.NOT_FAVORITABLE,
                            favorite.getRadioContent().getName(),
                            UtilArrayData.radioProfile.getName(),
                            favorite.getUpdatedAt(),
                            false,
                            Content.TYPE_RADIO_CONTENT,
                            favorite.getRadioContent().getAudio(),
                            i,
                            favorite.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }
            return datas;
        }
        return null;
    }


}
