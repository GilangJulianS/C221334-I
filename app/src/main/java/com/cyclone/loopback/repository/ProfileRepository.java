package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.FeedTimeline;
import com.cyclone.loopback.model.Profile;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by solusi247 on 22/12/15.
 */
public class ProfileRepository extends ModelRepository<Profile> {
    public ProfileRepository() {
        super("account", Profile.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id", "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String idProfile, final ListCallback<Profile> callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", idProfile);

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
                JSONObject result;
                Profile profile = new Profile();
                List<Profile> profileList = new ArrayList<Profile>();

                try {
                    result = new JSONObject(response);
                    try{}catch (Exception e){}
                    try{profile.setMale(result.getString("male"));}catch (Exception e){profile.setMale("");}
                    try{profile.setBirthday(result.getString("birthday"));}catch (Exception e){profile.setBirthday("");}
                    try{profile.setAbout(result.getString("about"));}catch (Exception e){profile.setAbout("");}
                    try{profile.setUsername(result.getString("username"));}catch (Exception e){profile.setUsername("");}
                    try{profile.setEmail(result.getString("email"));}catch (Exception e){profile.setEmail("");}
                    try{profile.setEmailVerified(result.getString("emailVerified"));}catch (Exception e){profile.setEmailVerified("");}
                    try{profile.setVerificationToken(result.getString("verificationToken"));}catch (Exception e){profile.setVerificationToken("");}
                    try{profile.setStatus(result.getString("status"));}catch (Exception e){profile.setStatus("");}
                    try{profile.setCreated(result.getString("created"));}catch (Exception e){profile.setCreated("");}
                    try{profile.setLastUpdated(result.getString("lastUpdated"));}catch (Exception e){profile.setLastUpdated("");}
                    try{profile.setId(result.getString("id"));}catch (Exception e){profile.setId("");}
                    try{profile.setProfile_picture(result.getString("profile_picture"));}catch (Exception e){profile.setProfile_picture("");}

                    UtilArrayData.currentProfile = profile;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                profileList.add(profile);
                callback.onSuccess(profileList);
            }
        });
    }
}
