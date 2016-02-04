package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.PlaylistData;
import com.cyclone.model.Music;
import com.cyclone.model.RadioContent;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by solusi247 on 04/02/16.
 */
public class PlaylistDataRepository extends ModelRepository<PlaylistData> {
    public PlaylistDataRepository() {
        super("playlist");
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/contents", "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String id, String offset, String take, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("offset", offset);
        parm.put("take", take);
        parm.put("id", id);
        invokeStaticMethod("get", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("success : " + response);
                List<PlaylistData> listPlaylist = new ArrayList<PlaylistData>();
                try {
                    System.out.println("success 2 : " + response);
                    JSONArray jsonArray = new JSONArray(response);
                    UtilArrayData.playlistData.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonContent;
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
                        PlaylistData playlistData = new PlaylistData();
                        playlistData.setId("" + jsonObject.get("id"));
                        playlistData.setContentId("" + jsonObject.get("contentId"));
                        playlistData.setPlaylistId("" + jsonObject.get("playlistId"));
                        System.out.println("0000000001");
                        try {
                            System.out.println("trying radio content");
                            jsonContent = new JSONObject(String.valueOf(jsonObject.getJSONObject("radioContent")));
                            playlistData.setTypeContent(PlaylistData.TYPE_RADIOCONTENT);

                            RadioContent radioContent = new RadioContent();
                            radioContent.setName("" + jsonContent.get("name"));
                            radioContent.setAudio("" + jsonContent.get("audio"));
                            radioContent.setCoverArt("" + jsonContent.get("coverArt"));
                            radioContent.setCategory("" + jsonContent.get("category"));
                            radioContent.setInfo("" + jsonContent.get("info"));
                            radioContent.setPrivate("" + jsonContent.get("private"));
                            radioContent.setPlayCount("" + jsonContent.get("playCount"));
                            radioContent.setFavoriteCount("" + jsonContent.get("favoriteCount"));
                            radioContent.setId("" + jsonContent.get("id"));
                            radioContent.setCreatedAt("" + jsonContent.get("createdAt"));
                            radioContent.setUpdatedAt("" + jsonContent.get("updatedAt"));
                            radioContent.setRadioId("" + jsonContent.get("radioId"));
                            radioContent.setAccountId("" + jsonContent.get("accountId"));

                            playlistData.setRadioContent(radioContent);
                            System.out.println("success radio content");
                        } catch (Exception e) {
                            System.out.println("gagal raio content");
                            try {
                                System.out.println("trying music");
                                jsonContent = new JSONObject(String.valueOf(jsonObject.getJSONObject("music")));
                                playlistData.setTypeContent(PlaylistData.TYPE_MUSIC);

                                Music music = new Music();
                                music.setName("" + jsonContent.get("name"));
                                music.setAudio("" + jsonContent.get("audio"));
                                music.setCoverArt("" + jsonContent.get("coverArt"));
                                music.setGenre("" + jsonContent.get("genre"));
                                music.setAlbum("" + jsonContent.get("album"));
                                music.setLyric("" + jsonContent.get("lyric"));
                                music.setPrivate("" + jsonContent.get("private"));
                                music.setPlayCount("" + jsonContent.get("playCount"));
                                music.setFavoriteCount("" + jsonContent.get("favoriteCount"));
                                music.setId("" + jsonContent.get("id"));
                                music.setCreatedAt("" + jsonContent.get("createdAt"));
                                music.setUpdatedAt("" + jsonContent.get("updatedAt"));
                                music.setAccountId("" + jsonContent.get("accountId"));
                                music.setArtist("" + jsonContent.get("artist"));

                                playlistData.setMusic(music);
                                System.out.println("success music");

                            } catch (Exception ex) {
                                System.out.println("faill music and radio content : " + ex);
                            }
                        }
                        System.out.println("looping ke : " + i);
                        listPlaylist.add(playlistData);
                        UtilArrayData.playlistData.add(playlistData);
                        System.out.println("isinya : " + listPlaylist.get(i).getTypeContent());
                        if (listPlaylist.get(i).getTypeContent() == PlaylistData.TYPE_RADIOCONTENT)
                            System.out.println("isinya radio content : " + listPlaylist.get(i).getRadioContent().getAudio());
                        if (listPlaylist.get(i).getTypeContent() == PlaylistData.TYPE_MUSIC)
                            System.out.println("isinya radio content : " + listPlaylist.get(i).getMusic().getAudio());

                    }
                    System.out.println("respone send");
                    // UtilArrayData.playlistData = listPlaylist;
                    callback.onSuccess("");
                    System.out.println("responde sended");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Json exception : " + e);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("eror yang mana siii : " + t);
                callback.onError(t);
            }
        });
    }
}
