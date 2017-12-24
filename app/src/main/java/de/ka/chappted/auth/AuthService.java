package de.ka.chappted.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * A service to authenticate users using the {@link Authenticator Authenticator} class.
 * <p>
 * Created by Thomas Hofmann on 08.12.17.
 */
public class AuthService extends Service {
    @Override
    public IBinder onBind(Intent intent) {

        Authenticator authenticator = new Authenticator(this);
        return authenticator.getIBinder();
    }

}

