package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.AccountStats;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 15/02/16.
 */
public class AccountStatsRepository extends ModelRepository<AccountStats> {
    public AccountStatsRepository() {
        super("account", AccountStats.class);
    }

    public RestContract createContract() {
        RestContract contract = new RestContract();
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/stats", "GET"), getClassName() + ".get");
        return contract;
    }

    public void get(String id, final ObjectCallback<AccountStats> callback) {
        Map<String, String> parm = new HashMap<>();
        parm.put("id", id);

        invokeStaticMethod("get", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println(response);
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    AccountStats accountStats = new AccountStats();
                    JSONObject stats = new JSONObject(String.valueOf(jsonObject.getJSONObject("message")));
                    accountStats.setFollowers(stats.getString("followers"));
                    accountStats.setFollowings(stats.getString("followings"));
                    accountStats.setShowlists(stats.getString("showlists"));
                    accountStats.setUploads(stats.getString("uploads"));
                    callback.onSuccess(accountStats);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error : " + t);
            }
        });

    }
}
