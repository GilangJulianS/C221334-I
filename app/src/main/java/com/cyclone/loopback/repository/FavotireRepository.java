package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.Favorite;
import com.cyclone.loopback.model.Music;
import com.cyclone.loopback.model.Playlist;
import com.cyclone.loopback.model.RadioContent;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 15/02/16.
 */
public class FavotireRepository extends ModelRepository<Favorite> {
    public static final String ACCOUNT_CLASS = "account";
    public static final String PLAYLIST_CLASS = "playlist";
    public static final String ALBUM_CLASS = "album";
    public static final String RADIOCONTENT_CLASS = "radio_content";
    public static final String MUSIC_CLASS = "music";
    public static final String TYPE_ALBUM = "album ";
    public static final String TYPE_ARTIST = "artist";
    public static final String TYPE_MIX = "mix";
    public static final String TYPE_MUSIC = "music";
    public static final String TYPE_PLAYLIST = "playlist";
    public static final String TYPE_RADIOCONTENT = "radio_content";
    private static FavotireRepository favotireRepository;
    private static String modelname = ACCOUNT_CLASS;

    public FavotireRepository() {
        super(favotireRepository.modelname);
    }

    public static FavotireRepository newInstance(String modelname) {
        favotireRepository = new FavotireRepository();
        favotireRepository.modelname = modelname;
        return favotireRepository;
    }


    @Override
    public RestContract createContract() {
        RestContract restContract = new RestContract();
        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/favorites/" + TYPE_MUSIC, "GET"), getClassName() + ".getMusic");
        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/favorites/" + TYPE_PLAYLIST, "GET"), getClassName() + ".getPlaylist");
        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/favorites/" + TYPE_RADIOCONTENT, "GET"), getClassName() + ".getRadicontent");
        restContract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/favorite/:id/", "POST"), getClassName() + ".addToFaforite");
        return restContract;
    }

    public void getMusic(String offset, String take, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("offset", offset);
        parm.put("take", take);
        invokeStaticMethod("getMusic", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                JSONObject objectResponse;
                JSONArray jsonArray;
                try {
                    objectResponse = new JSONObject(response);
                    jsonArray = new JSONArray("" + objectResponse.getJSONArray("message"));
                    Favorite favorite;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        favorite = new Favorite();
                        JSONObject jsonObject = new JSONObject("" + jsonArray.getJSONObject(i));
                        favorite.setId(jsonObject.getString("id"));
                        favorite.setAccountId(jsonObject.getString("accountId"));
                        favorite.setContentId(jsonObject.getString("contentId"));
                        favorite.setCreatedAt(jsonObject.getString("createdAt"));
                        favorite.setUpdatedAt(jsonObject.getString("updatedAt"));
                        favorite.setType(Favorite.TYPE_MUSIC);
                        JSONObject object = new JSONObject("" + jsonObject.getJSONObject(TYPE_MUSIC));
                        Music music = new Music();
                        music.setName(object.getString("name"));
                        music.setAudio(object.getString("audio"));
                        music.setCoverArt(object.getString("coverArt"));
                        music.setGenre(object.getString("genre"));
                        music.setAlbum(object.getString("album"));
                        try {
                            music.setLyric(object.getString("lyric"));
                        } catch (Exception e) {
                            music.setLyric("");
                        }

                        try {
                            music.setInfo(object.getString("info"));
                        } catch (Exception e) {
                            music.setInfo("");
                        }
                        music.setPrivate(object.getString("private"));
                        music.setPlayCount(object.getString("playCount"));
                        music.setFavoriteCount(object.getString("favoriteCount"));
                        music.setId(object.getString("id"));
                        music.setCreatedAt(object.getString("createdAt"));
                        music.setUpdatedAt(object.getString("updatedAt"));
                        music.setAccountId(object.getString("accountId"));
                        try {
                            music.setArtist(object.getString("artist"));
                        } catch (Exception e) {
                            music.setArtist("Anknown Artist");
                        }
                        favorite.setMusic(music);

                        UtilArrayData.favorites.add(favorite);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess("");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getPlaylist(String offset, String take, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("offset", offset);
        parm.put("take", take);
        invokeStaticMethod("getPlaylist", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                JSONObject objectResponse;
                JSONArray jsonArray;
                try {
                    objectResponse = new JSONObject(response);
                    jsonArray = new JSONArray("" + objectResponse.getJSONArray("message"));
                    Favorite favorite;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        favorite = new Favorite();
                        JSONObject jsonObject = new JSONObject("" + jsonArray.getJSONObject(i));
                        favorite.setId(jsonObject.getString("id"));
                        favorite.setAccountId(jsonObject.getString("accountId"));
                        favorite.setContentId(jsonObject.getString("contentId"));
                        favorite.setCreatedAt(jsonObject.getString("createdAt"));
                        favorite.setUpdatedAt(jsonObject.getString("updatedAt"));
                        favorite.setType(Favorite.TYPE_PLAYLIST);
                        JSONObject object = new JSONObject("" + jsonObject.getJSONObject(TYPE_PLAYLIST));
                        Playlist playlist = new Playlist();
                        playlist.setName(object.getString("name"));
                        playlist.setCaption(object.getString("caption"));
                        playlist.setPrivate(object.getString("private"));
                        playlist.setPlayCount(object.getString("playCount"));
                        playlist.setFavoriteCount(object.getString("favoriteCount"));
                        try {
                            playlist.setImage(object.getString("image"));
                        } catch (Exception e) {
                            playlist.setImage("");
                        }
                        playlist.setContentCount(object.getString("contentCount"));
                        playlist.setId(object.getString("id"));
                        playlist.setCreatedAt(object.getString("createdAt"));
                        playlist.setUpdatedAt(object.getString("updatedAt"));
                        playlist.setAccountId(object.getString("accountId"));
                        favorite.setPlaylist(playlist);

                        UtilArrayData.favorites.add(favorite);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callback.onSuccess("");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void getRadiocontent(String offset, String take, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("offset", offset);
        parm.put("take", take);
        invokeStaticMethod("getRadicontent", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                JSONObject objectResponse;
                JSONArray jsonArray;
                try {
                    objectResponse = new JSONObject(response);
                    jsonArray = new JSONArray("" + objectResponse.getJSONArray("message"));
                    Favorite favorite;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        favorite = new Favorite();
                        JSONObject jsonObject = new JSONObject("" + jsonArray.getJSONObject(i));
                        favorite.setId(jsonObject.getString("id"));
                        favorite.setAccountId(jsonObject.getString("accountId"));
                        favorite.setContentId(jsonObject.getString("contentId"));
                        favorite.setCreatedAt(jsonObject.getString("createdAt"));
                        favorite.setUpdatedAt(jsonObject.getString("updatedAt"));
                        favorite.setType(Favorite.TYPE_RADIOCONTENT);
                        JSONObject object = new JSONObject("" + jsonObject.getJSONObject("radioContent"));
                        RadioContent radioContent = new RadioContent();
                        radioContent.setName(object.getString("name"));
                        radioContent.setAudio(object.getString("audio"));
                        radioContent.setCoverArt(object.getString("coverArt"));
                        radioContent.setCategory(object.getString("category"));
                        radioContent.setInfo(object.getString("info"));
                        radioContent.setPrivate(Boolean.parseBoolean(object.getString("private")));
                        radioContent.setPlayCount(Integer.parseInt(object.getString("playCount")));
                        radioContent.setFavoriteCount(Integer.parseInt(object.getString("favoriteCount")));
                        radioContent.setId(object.getString("id"));
                        radioContent.setCreatedAt(object.getString("createdAt"));
                        radioContent.setUpdatedAt(object.getString("updatedAt"));
                        radioContent.setRadioId(object.getString("radioId"));
                        radioContent.setAccountId(object.getString("accountId"));
                        favorite.setRadioContent(radioContent);

                        UtilArrayData.favorites.add(favorite);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess("");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void addToFavorite(String id, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", id);
        invokeStaticMethod("addToFaforite", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("berhasil : " + response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("gagal : " + t);
            }
        });
    }
}
