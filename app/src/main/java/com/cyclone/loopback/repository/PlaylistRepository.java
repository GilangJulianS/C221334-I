package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.Playlist;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.Map;

/**
 * Created by solusi247 on 22/12/15.
 */
public class PlaylistRepository extends ModelRepository<Playlist> {
    public PlaylistRepository() {
        super("Playlist", Playlist.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() , "POST"),
                getClassName() + ".creat");

        return contract;
    }

    public void creat(Map<String, String> parm, final Adapter.Callback callback) {
        createContract();
        invokeStaticMethod("creat", parm, new Adapter.Callback() {

            @Override
            public void onError(Throwable t) {
                System.out.println("error");
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
