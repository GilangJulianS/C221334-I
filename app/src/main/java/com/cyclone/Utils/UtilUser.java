package com.cyclone.Utils;

import com.cyclone.loopback.UserClass;
import com.strongloop.android.loopback.AccessToken;

/**
 * Created by solusi247 on 17/12/15.
 */
public class UtilUser {
    public static UserClass.User currentUser;
    public static AccessToken currentToken;

    //id sementera sebeleum backend di benerin
    private String id = "567246f8e14b45fb245b8e9c";

    public String getId() {
        return id;
    }
}
