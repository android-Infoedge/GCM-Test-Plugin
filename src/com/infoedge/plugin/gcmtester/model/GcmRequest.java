package com.infoedge.plugin.gcmtester.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gagandeep on 29/3/16.
 */
public final class GcmRequest {

    @SerializedName("registration_ids")
    public List<String> registrationIds;

    @SerializedName("data")
    public float jsonData = Float.MAX_VALUE;

    public transient String message;

}
