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
public class FeedLikeRepository extends ModelRepository<LikeFeed> {

    public FeedLikeRepository() {
        super("feed", LikeFeed.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/likes/:id", "POST"), getClassName() + ".LIKE");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/likes/:id", "DELETE"), getClassName() + ".UNLIKE");

        return contract;
    }


    public void like(String FeedId) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", FeedId);

        invokeStaticMethod("LIKE", parm, new Adapter.Callback() {

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

class LikeFeed extends Model{

}
