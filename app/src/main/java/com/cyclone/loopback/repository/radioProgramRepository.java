package com.cyclone.loopback.repository;

import com.cyclone.Utils.UtilArrayData;
import com.cyclone.loopback.model.radioProgram;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.remoting.adapters.Adapter;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by solusi247 on 08/01/16.
 */
public class radioProgramRepository extends ModelRepository<radioProgram> {

    public radioProgramRepository() {
        super("radio");
    }

    @Override
    public RestContract createContract() {
        RestContract contract = super.createContract();

        contract.addItem(new RestContractItem("/" + getNameForRestUrl() + "/:id/programs", "GET"), getClassName() + ".get");

        return contract;
    }

    public void get(String radioId, final ListCallback<radioProgram> callback){
        createContract();
        Map<String, String> parm = new HashMap<>();
        parm.put("id", radioId);

        invokeStaticMethod("get", parm, new Adapter.Callback() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                List<radioProgram> list = new ArrayList<radioProgram>();

                try {
                    jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        radioProgram programRadio = new radioProgram();
                        programRadio.setId(jsonObject.getString("id"));
                        programRadio.setName(jsonObject.getString("name"));
                        programRadio.setDescription(jsonObject.getString("description"));
                        programRadio.setFinished_at(jsonObject.getString("finished_at"));
                        programRadio.setStarted_at(jsonObject.getString("started_at"));
                        programRadio.setLogo(jsonObject.getString("logo"));
                        programRadio.setRadioId(jsonObject.getString("radioId"));

                        list.add(programRadio);
                    }
                    System.out.println("ARRAY : " + jsonArray);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UtilArrayData.radioPrograms = list;
                callback.onSuccess(list);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("on error " + t);
                callback.onError(t);
            }
        });
    }
}
