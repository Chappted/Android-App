package de.ka.chappted.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;

import de.ka.chappted.R;
import de.ka.chappted.auth.AuthService;

/**
 * Offers sync utils.
 * <p>
 * Created by Thomas Hofmann on 08.12.17.
 */

public class AuthUtil {

    private static final String PREF_SETUP_COMPLETE = "setup_complete";

    /**
     * Create an entry for this application in the system account list, if it isn't already there.
     *
     * @param context Context
     */
    public static void CreateSyncAccount(Context context) {
        boolean newAccount = false;
        boolean setupComplete = PreferenceManager
                .getDefaultSharedPreferences(context).getBoolean(PREF_SETUP_COMPLETE, false);

        String accountType = context.getString(R.string.account_type);

        // Create account, if it's missing. (Either first run, or user has deleted account.)
        Account account = AuthService.GetAccount(accountType);
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (accountManager != null
                && accountManager.addAccountExplicitly(account, null, null)) {
            newAccount = true;
        }

        // Schedule an initial sync if we detect problems with either our account or our local
        // data has been deleted. (Note that it's possible to clear app data WITHOUT affecting
        // the account list, so wee need to check both.)
        if (newAccount || !setupComplete) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putBoolean(PREF_SETUP_COMPLETE, true).commit();
        }
    }


}
