package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
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
 * Created by solusi247 on 17/01/16.
 */
public class RadioContentRepository extends ModelRepository<RadioContent> {
    public RadioContentRepository() {
        super("radio_content");
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
                System.out.println("Success :"+response);
                JSONArray jsonArray;
                RadioContent radioContent;
                try {
                    jsonArray = new JSONArray(response);
                    UtilArrayData.latestContent.clear();
                    UtilArrayData.news.clear();
                    UtilArrayData.info.clear();
                    UtilArrayData.variety.clear();
                    UtilArrayData.travel.clear();
                    UtilArrayData.advertorial.clear();

                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
                        radioContent = new RadioContent();
                        radioContent.setName(jsonObject.getString("name"));
                        radioContent.setAudio(jsonObject.getString("audio"));
                        radioContent.setCoverArt(jsonObject.getString("coverArt"));
                        radioContent.setCategory(jsonObject.getString("category"));
                        radioContent.setInfo(jsonObject.getString("info"));
                        radioContent.setPrivate(Boolean.parseBoolean(jsonObject.getString("private")));
                        radioContent.setPlayCount(Integer.parseInt(jsonObject.getString("playCount")));
                        radioContent.setFavoriteCount(Integer.parseInt(jsonObject.getString("favoriteCount")));
                        radioContent.setId(jsonObject.getString("id"));
                        radioContent.setCreatedAt(jsonObject.getString("createdAt"));
                        radioContent.setUpdatedAt(jsonObject.getString("updatedAt"));
                        radioContent.setRadioId(jsonObject.getString("radioId"));
                        radioContent.setAccountId(jsonObject.getString("accountId"));

                        UtilArrayData.allRadioContent.add(radioContent);
                        if(radioContent.getCategory().equalsIgnoreCase("Latest Content")) UtilArrayData.latestContent.add(radioContent);
                        else if(radioContent.getCategory().equalsIgnoreCase("News")) UtilArrayData.news.add(radioContent);
                        else if(radioContent.getCategory().equalsIgnoreCase("Info")) UtilArrayData.info.add(radioContent);
                        else if(radioContent.getCategory().equalsIgnoreCase("Variety")) UtilArrayData.variety.add(radioContent);
                        else if(radioContent.getCategory().equalsIgnoreCase("Travel")) UtilArrayData.travel.add(radioContent);
                        else if(radioContent.getCategory().equalsIgnoreCase("Advertorial")) UtilArrayData.advertorial.add(radioContent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error :"+ t);
                callback.onError(t);
            }
        });
    }
}
