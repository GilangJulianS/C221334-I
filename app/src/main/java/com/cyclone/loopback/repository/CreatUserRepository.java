package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.CreatUser;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by solusi247 on 17/12/15.
 */
public class CreatUserRepository extends ModelRepository<CreatUser> {
    public CreatUserRepository() {
        super("account", CreatUser.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() , "POST"),
                getClassName() + ".creat");

        return contract;
    }

    public void creat(String username, String email, String male, String password, final Adapter.Callback callback) {

        Map<String, String> parm = new HashMap<String , String>();
        parm.put("male", male);
        parm.put("username" , username);
        parm.put("email" , email);
        parm.put("password" , password);
        parm.put("radioOwner" , "true");

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
