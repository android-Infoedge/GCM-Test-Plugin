package com.infoedge.plugin.gcmtester.network;

import com.google.gson.Gson;
import com.infoedge.plugin.gcmtester.model.GcmRequest;
import com.infoedge.plugin.gcmtester.utils.Constants;
import com.infoedge.plugin.gcmtester.model.GcmResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by gagandeep on 29/3/16.
 */
public final class NetworkHandler {

    private NetworkHandler() {}

    public interface NetworkResponseListener {
        void onSuccess(GcmResponse gcmResponse);
        void onFailure(String failureMessage);
    }

    public static void initGcmRequest(final String apiKey, final GcmRequest gcmRequest, final NetworkResponseListener networkResponseListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendGcmRequest(apiKey,gcmRequest,networkResponseListener);
            }
        }).start();
    }

    public static void sendGcmRequest(String apiKey, GcmRequest gcmRequest,NetworkResponseListener networkResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();

        String json = new Gson().toJson(gcmRequest);
        json = json.replace(String.valueOf(Float.MAX_VALUE),gcmRequest.message);
        Request request = new Request.Builder()
                .url(Constants.API_URL)
                .header(Constants.HEADER_AUTH_KEY,Constants.HEADER_AUTH_KEY_VALUE_PREFIX+apiKey)
                .header(Constants.HEADER_CONTENT_TYPE_KEY_JSON,Constants.HEADER_CONTENT_TYPE_VALUE)
                .post(RequestBody.create(Constants.MEDIA_TYPE_MARKDOWN,json))
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseString = new String(response.body().bytes());
                GcmResponse gcmResponse = new Gson().fromJson(responseString,GcmResponse.class);
                if(networkResponseListener != null) {
                    networkResponseListener.onSuccess(gcmResponse);
                }
                return;
            } else {
                if(networkResponseListener != null) {
                    networkResponseListener.onFailure(Constants.ERROR_MESSAGE_GCM_RESPONSE_RECEIVED);
                }
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(networkResponseListener != null) {
            networkResponseListener.onFailure(Constants.ERROR_MESSAGE_GCM_FAILED);
        }
    }
}
