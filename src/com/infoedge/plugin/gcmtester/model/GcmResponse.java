package com.infoedge.plugin.gcmtester.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gagandeep on 30/3/16.
 */
public class GcmResponse {

    @SerializedName("multicast_id")
    public String multicastId;

    @SerializedName("success")
    public int success;

    @SerializedName("failure")
    public int failure;

    @SerializedName("results")
    public List<GcmResultPerId> resultPerIds;

}
