package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.radioProfile;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.JsonObjectParser;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 07/01/16.
 */
public class radioProfileRepository extends ModelRepository<radioProfile> {
    public radioProfileRepository() {
        super("radio", radioProfile.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id", "GET"),
                getClassName() + ".get");

        return contract;
    }

    public void get(String feedId, final ObjectCallback<radioProfile> callback) {
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", feedId);
        System.out.println("Redio Profile execute");
        invokeStaticMethod("get", parm, new JsonObjectParser<radioProfile>(this, new ObjectCallback<radioProfile>() {
            @Override
            public void onSuccess(radioProfile object) {
                UtilArrayData.radioProfile = object;
                callback.onSuccess(object);
            }

            @Override
            public void onError(Throwable t) {
                callback.onError(t);
            }
        }));

    }
}
