package com.infoedge.plugin.gcmtester.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gagandeep on 30/3/16.
 */
public class GcmResultPerId {

    @SerializedName("message_id")
    public String messageId;

    @SerializedName("error")
    public String errorMessage;
}
