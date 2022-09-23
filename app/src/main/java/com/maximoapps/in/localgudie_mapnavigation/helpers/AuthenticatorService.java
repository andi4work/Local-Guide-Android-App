package com.maximoapps.in.localgudie_mapnavigation.helpers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by ${ChandraMohanReddy} on 1/11/2017.
 */
public class AuthenticatorService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        CustomAuthenticator authenticator = new CustomAuthenticator(this);
        return authenticator.getIBinder();
    }
}