package com.cyclone.loopback.repository;

import com.cyclone.loopback.model.Upload;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.RestRepository;
import com.strongloop.android.loopback.callbacks.JsonArrayParser;
import com.strongloop.android.loopback.callbacks.JsonObjectParser;
import com.strongloop.android.loopback.callbacks.ListCallback;
import com.strongloop.android.loopback.callbacks.ObjectCallback;
import com.strongloop.android.remoting.adapters.RestContract;
import com.strongloop.android.remoting.adapters.RestContractItem;

/**
 * Created by solusi247 on 05/02/16.
 */
public class UploadRepository extends RestRepository<Upload> {
    private String getNameForRestUrl() {
        return "uploads";
    }

    public UploadRepository() {
        super("upload", Upload.class);
    }

    public RestContract createContract() {
        RestContract contract = super.createContract();

        String className = getClassName();

        final String basePath = "/" + getNameForRestUrl();
        contract.addItem(new RestContractItem(basePath, "POST"),
                className + ".create");

        contract.addItem(new RestContractItem(basePath, "GET"),
                className + ".getAll");

        contract.addItem(new RestContractItem(basePath + "/:name", "GET"),
                className + ".get");

        contract.addItem(new RestContractItem(basePath + "/:name", "DELETE"),
                className + ".prototype.remove");

        return contract;
    }

    public void create(String name, ObjectCallback<Upload> callback) {
        invokeStaticMethod("create", ImmutableMap.of("name", name),
                new JsonObjectParser<Upload>(this, callback));
    }

    public void get(String containerName, ObjectCallback<Upload> callback) {
        invokeStaticMethod("get", ImmutableMap.of("name", containerName),
                new JsonObjectParser<Upload>(this, callback));
    }

    public void getAll(ListCallback<Upload> callback) {
        invokeStaticMethod("getAll", null,
                new JsonArrayParser<Upload>(this, callback));
    }
}