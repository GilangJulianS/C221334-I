package com.cyclone.data;

import android.net.Uri;

import com.cyclone.Utils.ServerUrl;
import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.Music;
import com.cyclone.loopback.model.RadioContent;
import com.cyclone.player.media.MediaCustom;
import com.cyclone.player.media.MediaDatabase;
import com.cyclone.player.media.MediaWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by solusi247 on 22/01/16.
 */
public class GoPlay {
    public static int HOME_PLAY_ON_HOLDER = 0x607d;

    private static GoPlay Instance = null;

    public static GoPlay getInstance(){
        if(Instance == null){
            Instance = new GoPlay();
        }
        return Instance;
    }

    public MediaWrapper PlayStream(){
        MediaWrapper mMedia;
        MediaCustom MC;
        MediaDatabase mDB = MediaDatabase.getInstance();
        List<MediaWrapper> mw = new ArrayList<MediaWrapper>();
        MC = new MediaCustom();
        MC.setUri(Uri.parse(ServerUrl.ulr_stream));
        MC.setTitle("program.name");
        MC.setArtist(UtilArrayData.NAMA_RADIO);
        MC.setAlbum("program.description");
        MC.setAlbumArtist(UtilArrayData.NAMA_RADIO);
        MC.setArtworkURL("https://lh6.ggpht.com/cEwi4r2tcVC9neGWHxjt6ZLQ2TuAs_iPn3rL_YQAp4sZsit4dNHROrsH2Fk8gr94hlxw=w300");
        mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                MC.getDiscNumber(), MC.getLastModified());
        mDB.addMedia(mMedia);
        mw.add(mMedia);
        return mMedia;
    }

    public List<MediaWrapper> getMedia(int typemedia, String category){
        List<MediaWrapper> media = new ArrayList<MediaWrapper>();
        MediaDatabase mDB = MediaDatabase.getInstance();

        if(typemedia == HOME_PLAY_ON_HOLDER){
            List<RadioContent> content;
            List<Music> listMusic;
            Uri myUri ;
            MediaCustom MC;
            MediaWrapper mMedia;

            if(category.equals(UtilArrayData.CATEGORY_LatestContent)){
                content = UtilArrayData.LatestContent;
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }

            }else if(category.equals(UtilArrayData.CATEGORY_NEWS)){
                content = UtilArrayData.News;
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle("ke" + i + content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_Info)){
                content = UtilArrayData.Info;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Info);
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_Variety)){
                content = UtilArrayData.Variety;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Variety);
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_Travel)){
                content = UtilArrayData.Travel;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Travel);
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_Advertorial)){
                content = UtilArrayData.Advertorial;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Advertorial);
                for (int i = 0; i<content.size(); i++){
                    myUri = Uri.parse(content.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(content.get(i).getName());
                    MC.setArtist(UtilArrayData.rdioProfile.getName());
                    MC.setAlbum(content.get(i).getName());
                    MC.setAlbumArtist(content.get(i).getCategory());
                    MC.setArtworkURL(content.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_POP)){
                listMusic = UtilArrayData.Pop;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Advertorial);
                for (int i = 0; i<listMusic.size(); i++){
                    myUri = Uri.parse(listMusic.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(listMusic.get(i).getName());
                    MC.setArtist(listMusic.get(i).getArtist());
                    MC.setAlbum(listMusic.get(i).getAlbum());
                    MC.setAlbumArtist(listMusic.get(i).getAlbum());
                    MC.setArtworkURL(listMusic.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_POP_INDO)){
                listMusic = UtilArrayData.Indo_Pop;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Advertorial);
                for (int i = 0; i<listMusic.size(); i++){
                    myUri = Uri.parse(listMusic.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(listMusic.get(i).getName());
                    MC.setArtist(listMusic.get(i).getArtist());
                    MC.setAlbum(listMusic.get(i).getAlbum());
                    MC.setAlbumArtist(listMusic.get(i).getAlbum());
                    MC.setArtworkURL(listMusic.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_DANCE)){
                listMusic = UtilArrayData.Dance;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Advertorial);
                for (int i = 0; i<listMusic.size(); i++){
                    myUri = Uri.parse(listMusic.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(listMusic.get(i).getName());
                    MC.setArtist(listMusic.get(i).getArtist());
                    MC.setAlbum(listMusic.get(i).getAlbum());
                    MC.setAlbumArtist(listMusic.get(i).getAlbum());
                    MC.setArtworkURL(listMusic.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }else if(category.equals(UtilArrayData.CATEGORY_HIP_HOP_RAP)){
                listMusic = UtilArrayData.Hip_Hop_Rap;
                System.out.println(category +" : "+UtilArrayData.CATEGORY_Advertorial);
                for (int i = 0; i<listMusic.size(); i++){
                    myUri = Uri.parse(listMusic.get(i).getAudio());
                    MC = new MediaCustom();
                    MC.setUri(myUri);
                    MC.setTitle(listMusic.get(i).getName());
                    MC.setArtist(listMusic.get(i).getArtist());
                    MC.setAlbum(listMusic.get(i).getAlbum());
                    MC.setAlbumArtist(listMusic.get(i).getAlbum());
                    MC.setArtworkURL(listMusic.get(i).getCoverArt());
                    mMedia = new MediaWrapper(MC.getUri(), MC.getTime(), MC.getLength(), MC.getType(),
                            MC.getPicture(), MC.getTitle(), MC.getArtist(), MC.getGenre(), MC.getAlbum(), MC.getAlbumArtist(),
                            MC.getWidth(), MC.getHeight(), MC.getArtworkURL(), MC.getAudio(), MC.getSpu(), MC.getTrackNumber(),
                            MC.getDiscNumber(), MC.getLastModified());
                    mDB.addMedia(mMedia);
                    media.add(mMedia);
                }
            }
            else{
                System.out.println("no category");
            }
        }


        return media;
    }
}