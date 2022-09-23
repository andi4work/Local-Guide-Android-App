package com.maximoapps.in.localgudie_mapnavigation.app;

import androidx.multidex.MultiDexApplication;

import com.maximoapps.in.localgudie_mapnavigation.helpers.ConnectivityReceiver;
import com.maximoapps.in.localgudie_mapnavigation.retrofit.services.RestClient;

/**
 * Created by ${ChandraMohanReddy} on 1/21/2017.
 */

public class MainApplication extends MultiDexApplication {
    private static MainApplication mInstance;

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}

