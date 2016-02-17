package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.Profile;
import com.cyclone.loopback.model.comment;
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
 * Created by solusi247 on 07/01/16.
 */
public class CommentRepository extends ModelRepository<comment> {
    public CommentRepository() {
        super("feed", comment.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/comments/:id", "GET"),
                getClassName() + ".get");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/comments/:id", "POST"),
                getClassName() + ".post");

        return contract;
    }

    public void get(String idFeed, int offset, int take, final ListCallback<comment> callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        System.out.println("id feed : " + idFeed);
        parm.put("id", idFeed);
        parm.put("offset", "" + offset);
        parm.put("take", "" + take);

        final List<comment> listComment = new ArrayList<comment>();

        invokeStaticMethod("get", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);
                callback.onError(t);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);
                JSONArray result;
                comment cmt;

                try {
                    System.out.println("try");
                    result = new JSONArray(response);
                    for (int i = 0; i < result.length(); i++){
                        JSONObject ObjectResult;
                        JSONObject ObjectAccount;
                        ObjectResult = result.getJSONObject(i);
                        ObjectAccount = ObjectResult.getJSONObject("account");

                        cmt = new comment();

                        cmt.setId(ObjectResult.getString("id"));
                        cmt.setAccountId(ObjectResult.getString("accountId"));
                        cmt.setContent(ObjectResult.getString("content"));
                        cmt.setCreatedAt(ObjectResult.getString("createdAt"));
                        cmt.setFeedId(ObjectResult.getString("feedId"));
                        cmt.setUpdatedAt(ObjectResult.getString("updatedAt"));

                        Profile profile = new Profile();

                        profile.setMale(ObjectAccount.getString("male"));
                        try {
                            profile.setBirthday(ObjectAccount.getString("birthday"));
                        } catch (Exception e) {
                            profile.setBirthday(" ");
                        }
                        try {
                            profile.setAbout(ObjectAccount.getString("about"));
                        } catch (Exception e) {
                            profile.setAbout(" ");
                        }
                        profile.setUsername(ObjectAccount.getString("username"));
                        profile.setEmail(ObjectAccount.getString("email"));
                        try {
                            profile.setStatus(ObjectAccount.getString("status"));
                        } catch (Exception e) {
                            profile.setStatus(" ");
                        }
                        profile.setId(ObjectAccount.getString("id"));
                        try {
                            profile.setProfilePicture(ObjectAccount.getString("profilePicture"));
                        } catch (Exception e) {
                            profile.setProfilePicture(" ");
                        }
                        profile.setRadioOwner(ObjectAccount.getString("radioOwner"));

                        cmt.setProfile(profile);
                        /*cmt.setUserid(ObjectAccount.getString("id"));
                        cmt.setUsername(ObjectAccount.getString("username"));
                        cmt.setMale(ObjectAccount.getString("male"));
                        cmt.setEmail(ObjectAccount.getString("email"));*/

                        System.out.println("Comment Object : " + ObjectResult.getString("content"));
                        System.out.println("Account Object : "+ ObjectAccount.getString("username"));

                        listComment.add(cmt);
                    }

                    UtilArrayData.commentList = listComment;
                    callback.onSuccess(listComment);

                } catch (JSONException e) {
                    System.out.println("errorrrr");
                    e.printStackTrace();
                    callback.onSuccess(listComment);
                }

            }
        });

    }

    public void post(String idFeed, String comment, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        System.out.println("id feed : " + idFeed);
        parm.put("id", idFeed);
        parm.put("content",comment);
        invokeStaticMethod("post", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);
                callback.onError(t);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);
                callback.onSuccess(response);

            }
        });

    }
}
