package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.user;
import com.strongloop.android.loopback.ModelRepository;

/**
 * Created by solusi247 on 22/12/15.
 */
public class userRepository extends ModelRepository<user> {
    public userRepository() {
        super("account",user.class);
    }
}
