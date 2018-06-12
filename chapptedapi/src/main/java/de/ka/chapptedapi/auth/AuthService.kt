package de.ka.chapptedapi.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * A service to authenticate users using the [Authenticator] class.
 *
 * Created by Thomas Hofmann on 08.12.17.
 */
class AuthService : Service() {
    override fun onBind(intent: Intent): IBinder? {

        val authenticator = Authenticator(this)
        return authenticator.iBinder
    }

}

