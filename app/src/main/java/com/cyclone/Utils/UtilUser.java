package com.cyclone.Utils;

import android.content.Context;

import com.cyclone.loopback.UserClass;
import com.cyclone.loopback.model.user;
import com.cyclone.loopback.repository.userRepository;
import com.strongloop.android.loopback.AccessToken;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.loopback.callbacks.ObjectCallback;

/**
 * Created by solusi247 on 17/12/15.
 */
public class UtilUser {
    public static UserClass.User currentUser;
    public static AccessToken currentToken;
    private static String img = " ";

    private static void setImg(String i) {
        img = i;
    }

    public static String getProfilePicture(Context context) {

        RestAdapter restAdapter = new RestAdapter(context, ServerUrl.API_URL);
        userRepository userRepo = restAdapter.createRepository(userRepository.class);
        userRepo.findById(UtilUser.currentUser.getId(), new ObjectCallback<user>() {
            @Override
            public void onSuccess(user object) {
                setImg(object.getProfilePicture());
            }

            @Override
            public void onError(Throwable t) {
                setImg(" ");
            }
        });

        return img;
    }

    //id sementera sebeleum backend di benerin
   /* private static String id = "567246f8e14b45fb245b8e9c";

    public String getId() {
        return id;
    }

    public static void setId(String ids){
        id = ids;
    }*/
}
