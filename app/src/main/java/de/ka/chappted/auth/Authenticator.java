package de.ka.chappted.auth;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import de.ka.chappted.R;
import de.ka.chappted.api.model.OAuthToken;
import de.ka.chappted.auth.login.LoginActivity;
import timber.log.Timber;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.accounts.AccountManager.get;

/**
 * A authenticator. Uses OAuth2 for authenticating the user.
 * <p>
 * Created by Thomas Hofmann on 08.12.17.
 *
 * @see OAuthUtils
 */
public class Authenticator extends AbstractAccountAuthenticator {

    // Authentication process:
    // We use the OAuth2 'authentication code flow' where we store a refreshToken as the authToken.
    // additionally an accessToken is stored as userdata for access of all resources.
    // The refreshToken then is only needed to gain access to a new accessToken if it expires.
    // An expired/revoked refreshToken leads to a new registration/login to get a new one.

    public static final String AUTH_TOKEN_TYPE = "Access";
    public static final String KEY_USERDATA_ACCESS_TOKEN = "access_token";

    private final Context mContext;

    /**
     * Creates a new authenticator.
     *
     * @param context the base context
     */
    Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType,
                             String authTokenType,
                             String[] requiredFeatures,
                             Bundle options) throws NetworkErrorException {


        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account,
                               String authTokenType,
                               final Bundle options) throws NetworkErrorException {

        Bundle result = new Bundle();

        // case 1: authTokenType is invalid
        if (authTokenType != null && !authTokenType.equals(AUTH_TOKEN_TYPE)) {
            result.putString(AccountManager.KEY_ERROR_MESSAGE, mContext.getString(R.string.account_error_tokentype));
            return result;
        }

        AccountManager accountManager = AccountManager.get(mContext);

        // if account is available
        if (account != null) {

            String accessToken = accountManager.getUserData(account, KEY_USERDATA_ACCESS_TOKEN);
            String refreshToken = accountManager.peekAuthToken(account, authTokenType);
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);

            // case 2: accessToken is available
            if (accessToken != null) {
                return result;
            }

            // case 3: access token is not available but refresh token is, fetch a new accesstoken!
            if (refreshToken != null) {
                OAuthUtils.get().fetchNewAccessTokenBlocking(mContext);
                return result;
            }

        }

        // case 4: account does not exist, ask for login
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (authTokenType.equals(AUTH_TOKEN_TYPE)) {
            return mContext.getString(R.string.account_token_label);
        } else {
            return authTokenType;
        }
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features)
            throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options)
            throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options)
            throws NetworkErrorException {
        return null;
    }

    /**
     * Stores the access token fro this account.
     *
     * @param context     the base context
     * @param accessToken the access token to store
     */
    static void storeAccessToken(Context context, String accessToken) {
        Account account = Authenticator.getAccount(context);

        if (account != null) {
            get(context).setUserData(account, KEY_USERDATA_ACCESS_TOKEN, accessToken);
        }
    }

    /**
     * Fires a request for o auth. Will lead to a registeration screen, if not authenticated
     * already. Else, might lead to a new access token. Will <b>NOT</b> notify on any
     * success / failure as this is intended to be called if a new registration / login is
     * wanted.
     *
     * @param activity a activity to start the login / registration process
     */
    static void requestNewOAuth(final Activity activity) {

        get(activity).getAuthTokenByFeatures(
                activity.getString(R.string.account_type),
                AUTH_TOKEN_TYPE,
                null,
                activity,
                null,
                null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Timber.d("OAuth requested.");
                    }
                }
                , null);
    }

    /**
     * Retrieves the o auth token from the account. If there is no account, null is returned.
     *
     * @param context the base context
     * @return the token or null, if there is no token
     */
    @Nullable
    static OAuthToken retrieveOAuth(Context context) {

        OAuthToken token = new OAuthToken();

        Account account = getAccount(context);

        if (account == null) {
            return null;
        }

        token.setRefreshToken(get(context).peekAuthToken(account, AUTH_TOKEN_TYPE));
        token.setAccessToken(get(context).getUserData(account, KEY_USERDATA_ACCESS_TOKEN));

        return token;

    }

    /**
     * If there is an account, invalidates the refresh token which also consumes the current accessToken.
     * This makes a new login/register for o auth mandatory.
     *
     * @param context the base context
     */
    static void invalidateOAuth(Context context) {
        AccountManager accountManager = get(context);

        Account account = getAccount(context);

        if (account == null) {
            return;
        }

        String token = accountManager.peekAuthToken(account, AUTH_TOKEN_TYPE);

        accountManager.setUserData(account, KEY_USERDATA_ACCESS_TOKEN, null);
        accountManager.invalidateAuthToken(AUTH_TOKEN_TYPE, token);
    }


    /**
     * Retrieves the current account, if any.
     *
     * @param context the base context
     * @return the account or null if there is none
     */
    @Nullable
    private static Account getAccount(Context context) {
        AccountManager accountManager = get(context);

        Account[] availableAccounts = accountManager.getAccountsByType(context.getString(R.string.account_type));

        if (availableAccounts.length > 0) {
            return availableAccounts[0];
        }
        return null;


    }
}
