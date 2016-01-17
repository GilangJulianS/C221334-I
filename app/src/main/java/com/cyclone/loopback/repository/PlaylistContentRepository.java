package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.PlaylistContent;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 15/01/16.
 */
public class PlaylistContentRepository extends ModelRepository<PlaylistContent> {
    public PlaylistContentRepository() {
        super("playlist");
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id", "GET"),
                getClassName() + ".get");
        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/radio_content/:conten_id", "POST"),
                getClassName() + ".postRadioContent");
        return super.createContract();
    }

    public void PostRadioContent (String id, String contentId, final Adapter.Callback callback){
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", id);
        parm.put("conten_id", contentId);
        System.out.println("id : "+id +" | contentID : "+contentId);
        invokeStaticMethod("postRadioContent", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                System.out.println("post content playlist : " + response);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("error content playlist : " + t);
            }
        });
    }
}
