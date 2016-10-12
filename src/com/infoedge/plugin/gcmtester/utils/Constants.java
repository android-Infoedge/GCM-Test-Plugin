package com.infoedge.plugin.gcmtester.utils;

import okhttp3.MediaType;

/**
 * Created by gagandeep on 29/3/16.
 */
public final class Constants {

    private Constants() {
    }

    public static final String API_URL = "https://android.googleapis.com/gcm/send";

    public static final String HEADER_AUTH_KEY_VALUE_PREFIX = "key= ";
    public static final String HEADER_AUTH_KEY = "Authorization";

    public static final String HEADER_CONTENT_TYPE_KEY_JSON = "Content-Type";
    public static final String HEADER_CONTENT_TYPE_VALUE = "application/json";

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=utf-8");

    public static final String PREF_KEY_API_KEY = "gcm_pref_key_api_key";
    public static final String PREF_KEY_REG_IDS = "gcm_pref_key_reg_ids";
    public static final String PREF_KEY_JSON_MESSAGE = "gcm_pref_key_json_message";

    public static final String ERROR_MESSAGE_API_KEY_EMPTY = "Please enter valid API key";
    public static final String ERROR_MESSAGE_REG_IDS_EMPTY = "Please enter valid Registration id(s)";
    public static final String ERROR_MESSAGE_JSON_MESSAGE_EMPTY = "Please enter message json message";
    public static final String ERROR_MESSAGE_JSON_MESSAGE_INVALID = "Please enter message in valid json format";
    public static final String ERROR_MESSAGE_GCM_FAILED = "Some problem occured while sending gcm message";
    public static final String ERROR_MESSAGE_GCM_RESPONSE_RECEIVED = "Some problem occured from server side, Please check json format";
}
