package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.FeedTimeline;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.ListCallback;
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
 * Created by solusi247 on 22/12/15.
 */
public class FeedTimelineRepository extends ModelRepository<FeedTimeline> {
    public FeedTimelineRepository() {
        super("feed", FeedTimeline.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/timeline", "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String offset, String take, final ListCallback<FeedTimeline> callback) {
        Map<String, String> parm = new HashMap<>();
        parm.put("offset", offset);
        parm.put("take", take);

        final List<FeedTimeline> listFeed = new ArrayList<>();


        invokeStaticMethod("get", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error : "+t);
                callback.onError(t);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);
                FeedTimeline feedTimeline;

                JSONObject ObjectRsultFirst;

               // JSONObject data;
                JSONArray data;
                JSONArray result;



                try {
                    data= new JSONArray(response);
                   // result = data.getJSONArray("result");
                    result = new JSONArray(response);

                    for (int i = 0; i < result.length(); i++){
                        JSONArray ArrayResult;
                        JSONObject ObjectResult;
                        JSONObject owner;
                        JSONObject type = null;
                        JSONObject statistics;
                       // Map<String, String> statisticsMap = new HashMap<String, String>();
                        Map<String, String> ownerMap = new HashMap<String, String>();
                        Map<String, String> typePost = new HashMap<String, String>();
                        feedTimeline = new FeedTimeline();
                        ObjectResult = result.getJSONObject(i);

//                        statistics = ObjectResult.getJSONObject("statistics");
                        owner = ObjectResult.getJSONObject("owner");
                        String tipe = ObjectResult.getString("type");

                      //  statisticsMap.put("likes", statistics.getString("likes"));
                      //  statisticsMap.put("comments", statistics.getString("comments"));

                        ownerMap.put("username", owner.getString("username"));
                        ownerMap.put("id", owner.getString("id"));
                        try {
                            ownerMap.put("profilePicture", owner.getString("profilePicture"));
                        } catch (Exception e) {
                            ownerMap.put("profilePicture", "");
                        }

                        if(tipe.equalsIgnoreCase("mix")){
                            type = ObjectResult.getJSONObject("mix");
                            typePost.put("name", type.getString("name"));
                            typePost.put("caption",type.getString("caption"));
                            typePost.put("id", type.getString("id"));
                            typePost.put("contentCount", type.getString("contentCount"));
                        }
                        else if(tipe.equalsIgnoreCase("playlist")){
                            type = ObjectResult.getJSONObject("playlist");
                            typePost.put("name", type.getString("name"));
                            typePost.put("caption",type.getString("caption"));
                            typePost.put("id", type.getString("id"));
                            typePost.put("contentCount", type.getString("contentCount"));
                        }
                        else if(tipe.equalsIgnoreCase("content")){
                            type = ObjectResult.getJSONObject("content");
                            typePost.put("name", type.getString("name"));
                           // typePost.put("caption",type.getString("caption"));
                            typePost.put("id", type.getString("id"));

                        }
                        feedTimeline.setType(ObjectResult.getString("type"));
                        feedTimeline.setId(ObjectResult.getString("id"));
                        feedTimeline.setCreated_at(ObjectResult.getString("createdAt"));
                        feedTimeline.setAccountId(ObjectResult.getString("accountId"));
                        feedTimeline.setFeedContentId(ObjectResult.getString("feedContentId"));
                        feedTimeline.setCommentsCount(ObjectResult.getInt("commentsCount"));
                        feedTimeline.setLikesCount(ObjectResult.getInt("likesCount"));
                        feedTimeline.setLiked(ObjectResult.getBoolean("liked"));

                       /* feedTimeline.setCreated_at(ObjectResult.getString("created_at"));
                        feedTimeline.setUpdated_at(ObjectResult.getString("updated_at"));
                        feedTimeline.setId(ObjectResult.getString("id"));
                        feedTimeline.setUserId(ObjectResult.getString("userId"));
                        feedTimeline.setStatistics(statisticsMap);*/
                        feedTimeline.setOwner(ownerMap);
                        feedTimeline.setTypePost(typePost);

                        listFeed.add(feedTimeline);

                           // UtilArrayData.feedTimelines.add(feedTimeline);


                        System.out.println("type name for looping result : "+feedTimeline.getTypePost().get("name"));

                    }
                    UtilArrayData.feedTimelines.clear();
                    UtilArrayData.feedTimelines = listFeed;
                    callback.onSuccess(listFeed);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
