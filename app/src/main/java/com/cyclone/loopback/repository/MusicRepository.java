package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.Music;
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
 * Created by solusi247 on 22/01/16.
 */
public class MusicRepository extends ModelRepository<Music> {
    public MusicRepository() {
        super("music");
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl(), "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        invokeStaticMethod("get", parm, new Adapter.Callback() {

            @Override
            public void onSuccess(String response) {
                System.out.println("response : " +response);
                Music music;
                JSONArray jsonArray;
                try {
                    jsonArray = new JSONArray(response);
                    UtilArrayData.music.clear();
                    UtilArrayData.pop.clear();
                    UtilArrayData.indoPop.clear();
                    UtilArrayData.dance.clear();
                    UtilArrayData.hipHopRap.clear();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
                        music = new Music();
                        music.setName(jsonObject.getString("name"));
                        music.setAudio(jsonObject.getString("audio"));
                        music.setCoverArt(jsonObject.getString("coverArt"));
                        music.setGenre(jsonObject.getString("genre"));
                        music.setAlbum(jsonObject.getString("album"));
                        music.setLyric(jsonObject.getString("lyric"));
                        try{
                            music.setInfo(jsonObject.getString("info"));
                        } catch (Exception e){
                            music.setInfo("");
                        }
                        music.setPrivate(jsonObject.getString("private"));
                        music.setPlayCount(jsonObject.getString("playCount"));
                        music.setFavoriteCount(jsonObject.getString("favoriteCount"));
                        music.setId(jsonObject.getString("id"));
                        music.setCreatedAt(jsonObject.getString("createdAt"));
                        music.setUpdatedAt(jsonObject.getString("updatedAt"));
                        music.setAccountId(jsonObject.getString("accountId"));
                        try{
                            music.setArtist(jsonObject.getString("artist"));
                        }catch (Exception e){
                            music.setArtist("Anknown Artist");
                        }

                        UtilArrayData.music.add(music);
                        if(music.getGenre().equalsIgnoreCase("Pop")) UtilArrayData.pop.add(music);
                        else if(music.getGenre().equalsIgnoreCase("Indo Pop")) UtilArrayData.indoPop.add(music);
                        else if(music.getGenre().equalsIgnoreCase("Dance")) UtilArrayData.dance.add(music);
                        else if(music.getGenre().equalsIgnoreCase("Hip Hop/Rap")) UtilArrayData.hipHopRap.add(music);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error : "+t);
                callback.onError(t);
            }
        });

    }
}
