package com.cyclone.loopback.repository;

import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 22/12/15.
 */
public class FollowRepository extends ModelRepository<Follow> {

    public FollowRepository() {
        super("account", Follow.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/following/:id", "PUT"), getClassName() + ".FOLLOW");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/following/:id", "DELETE"), getClassName() + ".UNFOLLOW");

        return contract;
    }


    public void follow(String AccountId) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", AccountId);

        invokeStaticMethod("FOLLOW", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);

            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);

            }
        });
    }

    public void unlike(String FeedId) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", FeedId);

        invokeStaticMethod("UNLIKE", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);

            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);

            }
        });
    }

}

class Follow extends Model{

}

