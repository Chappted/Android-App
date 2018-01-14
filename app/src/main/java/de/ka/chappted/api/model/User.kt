package de.ka.chappted.api.model

import android.support.annotation.Keep

import com.google.gson.annotations.SerializedName

/**
 * Represents a user.
 *
 * Created by Thomas Hofmann on 22.12.17.
 */
@Keep
class User(@field:SerializedName("email")
           val email: String,
           @field:SerializedName("password")
           val password: String)
