package de.ka.chappted.api;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * A repository.
 * Created by Thomas Hofmann on 21.12.17.
 */
public class Repository {

    private String mBaseUrl = "https://aqueous-thicket-54699.herokuapp.com/";

    private static Repository sRepository;

    private Client mNonAauthorizedClient;
    private Client mAuthenticatedClient;

    /**
     * Creates a new repository.
     */
    private Repository() {
        // hides the implicit constructor
    }

    /**
     * Retrieves a new instance of the repository.
     *
     * @return the repository instance
     */
    public static Repository get() {
        if (sRepository == null) {
            sRepository = new Repository();
        }
        return sRepository;
    }

    /**
     * Initializes the repository. Note that this step is not mandatory, but if called
     * it will recreate all services with the new defined initial data.
     *
     * @param baseUrl a new base url to use
     */
    public static void init(String baseUrl) {

        get().mAuthenticatedClient = null;
        get().mNonAauthorizedClient = null;

        get().mBaseUrl = baseUrl;
    }

    /**
     * Retrieves the non authorized client for making calls without authentication.
     *
     * @return the client
     */
    public Client getNonAuthorizedClient() {

        if (mNonAauthorizedClient == null) {
            mNonAauthorizedClient = ServiceGenerator.createNonAuthenticatedService(mBaseUrl, Client.class);
        }

        return mNonAauthorizedClient;
    }

    /**
     * Retreives the authorized client for making calls with authentication.
     *
     * @param context the base context
     * @return the client
     */
    public Client getAuthenticatedClient(@NonNull Context context) {
        if (mAuthenticatedClient == null) {
            mAuthenticatedClient = ServiceGenerator.createAuthenticatedService(mBaseUrl, Client.class, context);
        }

        return mAuthenticatedClient;
    }
}
