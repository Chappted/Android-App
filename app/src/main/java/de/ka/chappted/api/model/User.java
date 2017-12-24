package de.ka.chappted.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a user.
 *
 * Created by Thomas Hofmann on 22.12.17.
 */
public class User {

    @SerializedName("email")
    private String mEmail;

    @SerializedName("password")
    private String mPassword;


    public User(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }
}
