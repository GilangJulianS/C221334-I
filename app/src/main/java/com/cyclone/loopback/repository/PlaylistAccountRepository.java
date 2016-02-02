package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.PlaylistAccount;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 15/01/16.
 */
public class PlaylistAccountRepository extends ModelRepository<PlaylistAccount> {
    public PlaylistAccountRepository() {
        super("account");
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/playlists", "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String id, final Adapter.Callback callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id",id);

        invokeStaticMethod("get", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error");
                callback.onError(t);
            }

            @Override
            public void onSuccess(String response) {
                System.out.println("respon : " + response);
                PlaylistAccount playlistAccount;
                JSONArray jsonArray;

                try {
                    jsonArray = new JSONArray(response);
                    UtilArrayData.playlistAccount.clear();
                    for (int i = 0; i < jsonArray.length(); i++){
                        playlistAccount = new PlaylistAccount();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        playlistAccount.setName(jsonObject.getString("name"));
                        playlistAccount.setCaption(jsonObject.getString("caption"));
                        playlistAccount.setId(jsonObject.getString("id"));
                        playlistAccount.setCreatedAt(jsonObject.getString("createdAt"));
                        playlistAccount.setAccountId(jsonObject.getString("accountId"));
                        playlistAccount.setPrivate(jsonObject.getString("private"));

                        UtilArrayData.playlistAccount.add(playlistAccount);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onSuccess(response);
            }
        });
    }
}
