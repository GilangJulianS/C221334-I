package com.cyclone.data;

import com.cyclone.MasterActivity;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.Music;
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

    public List<Object> showData(int typedata, String category){
        System.out.println("on get data - showdata");
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

            if(UtilArrayData.LatestContent.size() > 0){
                datas.add(new Section("Latest Content", UtilArrayData.CATEGORY_LatestContent, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    RadioContent radioContent = UtilArrayData.LatestContent.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_LatestContent, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.News.size() > 0){
                datas.add(new Section("News", UtilArrayData.CATEGORY_NEWS, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    RadioContent radioContent = UtilArrayData.News.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_NEWS, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.Info.size() > 0){
                datas.add(new Section("Info", UtilArrayData.CATEGORY_Info, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    RadioContent radioContent = UtilArrayData.Info.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Info, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.Advertorial.size() > 0){
                datas.add(new Section("Advertorial", UtilArrayData.CATEGORY_Advertorial, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    RadioContent radioContent = UtilArrayData.Advertorial.get(i);
                    contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Advertorial, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.Pop.size() > 0){
                datas.add(new Section("Pop", UtilArrayData.CATEGORY_POP, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    Music music = UtilArrayData.Pop.get(i);
                    contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.Indo_Pop.size() > 0){
                datas.add(new Section("Pop Indo", UtilArrayData.CATEGORY_POP_INDO, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    Music music = UtilArrayData.Indo_Pop.get(i);
                    contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP_INDO, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                }
                contents = new Contents(contentList);
                datas.add(contents);
            }

            if(UtilArrayData.Hip_Hop_Rap.size() > 0){
                datas.add(new Section("Hip Hop/Rap", UtilArrayData.CATEGORY_HIP_HOP_RAP, MasterActivity.FRAGMENT_SUBCATEGORY));
                contentList = new ArrayList<>();
                for(int i = 0; i < 2; i++){
                    try{
                        Music music = UtilArrayData.Hip_Hop_Rap.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_HIP_HOP_RAP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
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
                if(UtilArrayData.LatestContent.size() > 0){
                    for(int i = 0; i < UtilArrayData.LatestContent.size(); i++){
                        RadioContent radioContent = UtilArrayData.LatestContent.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                        System.out.println("judul : " + radioContent.getName());
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_NEWS)){
                if(UtilArrayData.News.size() > 0){
                    for(int i = 0; i < UtilArrayData.News.size(); i++){
                        RadioContent radioContent = UtilArrayData.News.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Info)){
                if(UtilArrayData.Info.size() > 0){
                    for(int i = 0; i < UtilArrayData.Info.size(); i++){
                        RadioContent radioContent = UtilArrayData.Info.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Variety)){
                if(UtilArrayData.Variety.size() > 0){
                    for(int i = 0; i < UtilArrayData.Variety.size(); i++){
                        RadioContent radioContent = UtilArrayData.Variety.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Travel)){
                if(UtilArrayData.Travel.size() > 0){
                    for(int i = 0; i < UtilArrayData.Travel.size(); i++){
                        RadioContent radioContent = UtilArrayData.Travel.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_Advertorial)){
                if(UtilArrayData.Advertorial.size() > 0){
                    for(int i = 0; i < UtilArrayData.Advertorial.size(); i++){
                        RadioContent radioContent = UtilArrayData.Advertorial.get(i);
                        datas.add(new SubcategoryItem(radioContent.getCoverArt(), radioContent.getName(),
                                UtilArrayData.rdioProfile.getName(), SubcategoryItem.TYPE_NORMAL, category,
                                radioContent.getAudio(), i,radioContent.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_POP)){
                if(UtilArrayData.Pop.size() > 0){
                    for(int i = 0; i < UtilArrayData.Pop.size(); i++){
                        Music music = UtilArrayData.Pop.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i,music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_POP_INDO)){
                if(UtilArrayData.Indo_Pop.size() > 0){
                    for(int i = 0; i < UtilArrayData.Indo_Pop.size(); i++){
                        Music music = UtilArrayData.Indo_Pop.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i,music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_DANCE)){
                if(UtilArrayData.Dance.size() > 0){
                    for(int i = 0; i < UtilArrayData.Dance.size(); i++){
                        Music music = UtilArrayData.Dance.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i,music.getId()));
                    }
                }
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_HIP_HOP_RAP)){
                if(UtilArrayData.Hip_Hop_Rap.size() > 0){
                    for(int i = 0; i < UtilArrayData.Hip_Hop_Rap.size(); i++){
                        Music music = UtilArrayData.Hip_Hop_Rap.get(i);
                        datas.add(new SubcategoryItem(music.getCoverArt(), music.getName(),
                                music.getArtist(), SubcategoryItem.TYPE_NORMAL, category,
                                music.getAudio(), i,music.getId()));
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
                if(UtilArrayData.LatestContent.size() > 0){
                    datas.add(new Section("Latest Content", UtilArrayData.CATEGORY_LatestContent, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.LatestContent.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_LatestContent, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.News.size() > 0){
                    datas.add(new Section("News", UtilArrayData.CATEGORY_NEWS, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.News.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_NEWS, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Info.size() > 0){
                    datas.add(new Section("Info", UtilArrayData.CATEGORY_Info, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.Info.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Info, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Variety.size() > 0){
                    datas.add(new Section("Variety", UtilArrayData.CATEGORY_Variety, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.Variety.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Variety, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Travel.size() > 0){
                    datas.add(new Section("Travel", UtilArrayData.CATEGORY_Travel, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.Travel.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Travel, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Advertorial.size() > 0){
                    datas.add(new Section("Advertorial", UtilArrayData.CATEGORY_Advertorial, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        RadioContent radioContent = UtilArrayData.Advertorial.get(i);
                        contentList.add(new Content(radioContent.getCoverArt(), UtilArrayData.CATEGORY_Advertorial, Content.NOT_FAVORITABLE, radioContent.getName(), UtilArrayData.rdioProfile.getName(), radioContent.getUpdatedAt(), false, Content.TYPE_RADIO_CONTENT, radioContent.getAudio(),i, radioContent.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }
                return datas;
            }else if(category.equalsIgnoreCase(UtilArrayData.CATEGORY_MUSIC)){
                Contents contents;
                List<Object> datas = new ArrayList<>();
                List<Content> contentList;

                if(UtilArrayData.Pop.size() > 0){
                    datas.add(new Section("Pop", UtilArrayData.CATEGORY_POP, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.Pop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Indo_Pop.size() > 0){
                    datas.add(new Section("Pop Indo", UtilArrayData.CATEGORY_POP_INDO, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.Indo_Pop.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_POP_INDO, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Dance.size() > 0){
                    datas.add(new Section("Dance", UtilArrayData.CATEGORY_DANCE, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        Music music = UtilArrayData.Dance.get(i);
                        contentList.add(new Content(music.getCoverArt(), UtilArrayData.CATEGORY_DANCE, Content.NOT_FAVORITABLE, music.getName(), music.getArtist(), music.getAlbum(), false, Content.TYPE_RADIO_CONTENT, music.getAudio(),i, music.getId()));
                    }
                    contents = new Contents(contentList);
                    datas.add(contents);
                }

                if(UtilArrayData.Hip_Hop_Rap.size() > 0){
                    datas.add(new Section("Hip Hop/Rap", UtilArrayData.CATEGORY_HIP_HOP_RAP, MasterActivity.FRAGMENT_SUBCATEGORY));
                    contentList = new ArrayList<>();
                    for(int i = 0; i < 2; i++){
                        try{
                            Music music = UtilArrayData.Hip_Hop_Rap.get(i);
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
        }
        return null;
    }


}