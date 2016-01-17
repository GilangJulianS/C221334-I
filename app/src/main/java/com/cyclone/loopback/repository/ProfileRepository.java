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

                    profile.setMale(result.getString("male"));
                    profile.setBirthday(result.getString("birthday"));
                    profile.setAbout(result.getString("about"));
                    profile.setUsername(result.getString("username"));
                    profile.setEmail(result.getString("email"));
                    profile.setEmailVerified(result.getString("emailVerified"));
                    profile.setVerificationToken(result.getString("verificationToken"));
                    profile.setStatus(result.getString("status"));
                    profile.setCreated(result.getString("created"));
                    profile.setLastUpdated(result.getString("lastUpdated"));
                    profile.setId(result.getString("id"));
                    profile.setProfile_picture(result.getString("profile_picture"));

                    UtilArrayData.CurrentProfile = profile;


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                profileList.add(profile);
                callback.onSuccess(profileList);
            }
        });
    }
}
