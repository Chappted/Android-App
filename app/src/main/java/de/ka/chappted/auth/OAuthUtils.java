package de.ka.chappted.auth;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;

import de.ka.chappted.api.model.OAuthToken;
import de.ka.chappted.api.Repository;
import retrofit2.Response;

/**
 * A o auth utility.
 * <p>
 * Created by Thomas Hofmann on 22.12.17.
 */
public class OAuthUtils {

    private static OAuthUtils sUtils;

    private OAuthToken mOAuthToken = new OAuthToken();

    /**
     * Hides the implicit construcotr
     */
    private OAuthUtils() {
        // hides implicit constructor
    }

    /**
     * Retrieves the o auth utils.
     *
     * @return the utils instance
     */
    public static OAuthUtils get() {

        if (sUtils == null) {
            sUtils = new OAuthUtils();
        }

        return sUtils;
    }

    /**
     * Retrieves the o auth token. Will auto-load the current o auth token, if there is any and
     * update the o auth token of this utility. If there is no auth token, a login or register is
     * presented, if possible. Pass a activity as context for auto showing login / register.
     *
     * @param context the base context, may be an activity for showing login / register
     * @return the token
     */
    @Nullable
    public OAuthToken peek(Context context) {

        // on first load, we peek if there is a refresh token stored here
        if (mOAuthToken.getRefreshToken() == null) {

            // if not, we load it from the account
            updateOAuthForAccount(context);

            // if there is none, we have to register first
            if (mOAuthToken.getRefreshToken() == null && context instanceof Activity) {
                Authenticator.requestNewOAuth((Activity) context);
                return null;
            }
        }

        return mOAuthToken;
    }


    /**
     * Updates th o auth tokens for the current account.
     *
     * @param context the base context
     */
    public void updateOAuthForAccount(Context context) {

        OAuthToken token = Authenticator.retrieveOAuth(context);

        if (token != null) {
            mOAuthToken = token;
        }
    }

    /**
     * Fetches a new access token. This is based on a refresh token. If neither exists, all tokens
     * are invalidated and the user has to login or register.
     * This is a blocking request, which is not asynchronous and may <b>NOT</b> be called on the
     * main thread!
     *
     * @param context the base context
     * @return the access token if one could be fetched or null
     */
    @Nullable
    public String fetchNewAccessTokenBlocking(Context context) {

        try {
            Response<OAuthToken> response = Repository.get().getNonAuthorizedClient().getNewAccessToken(
                    mOAuthToken.getRefreshToken(),
                    mOAuthToken.getClientID(),
                    mOAuthToken.getClientSecret(),
                    "refresh_token").execute();

            if (response.body() != null) {

                String accessToken = response.body().getAccessToken();

                mOAuthToken.setAccessToken(accessToken);
                mOAuthToken.setExpiry(response.body().getExpiry());
                mOAuthToken.setScope(response.body().getScope());
                mOAuthToken.setTokenType(response.body().getTokenType());

                Authenticator.storeAccessToken(context, accessToken);
                return accessToken;
            } else {
                // 401 ? no refreshToken ? kick the o auth and start over again
                invalidateOAuth(context);
                return null;
            }
        } catch (Exception e) {
            // we have errors ? kick the o auth and start over again
            invalidateOAuth(context);
            return null;
        }
    }

    /**
     * Invalidates all o auth relevant tokens.
     *
     * @param context the base context
     */
    private void invalidateOAuth(Context context) {
        Authenticator.invalidateOAuth(context);
        mOAuthToken.setAccessToken(null);
        mOAuthToken.setRefreshToken(null);
    }

}
