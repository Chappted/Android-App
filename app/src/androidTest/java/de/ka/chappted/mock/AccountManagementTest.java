package de.ka.chappted.mock;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.ka.chappted.App;
import de.ka.chappted.auth.OAuthUtils;
import de.ka.chappted.auth.login.LoginActivityViewModel;
import de.ka.chappted.main.MainActivity;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A test suite for account management.
 * <p>
 * Uses an array of testing frameworks:
 * Android Instrumentation, MockWebServer, Mockito, JUnit
 * <p>
 * Created by Thomas Hofmann on 21.12.17.
 */
@RunWith(AndroidJUnit4.class)
public class AccountManagementTest extends InstrumentationTestCase {

    private Context mContext;

    private TestInjector mTestInjector;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        mContext = InstrumentationRegistry.getInstrumentation().getContext();

        // enable server mocking
        server = new MockWebServer();
        server.start();

        // delete the current user account
        OAuthUtils.INSTANCE.deleteOAuthAccount(mActivityRule.getActivity().getApplicationContext());

        HttpUrl baseUrl = server.url("/");
        mTestInjector = new TestInjector(baseUrl.toString(), true);
        mTestInjector.overrideInjection((App) mActivityRule.getActivity().getApplication());
    }

    @Test
    public void test_login_after_401() throws Exception {

        // here comes the test where we force a login, because the account is not there / deleted
        final CountDownLatch latch1 = new CountDownLatch(1);
        LoginActivityViewModel viewModel = new LoginActivityViewModel(mActivityRule.getActivity().getApplication());

        server.enqueue(new MockResponse() // something unimportant, as we HAVE to login, but must need authorization
                .setResponseCode(200) // we do not pass 401 to not confuse the interceptor
                .setBody(MockUtil.getJsonFromFile(mContext, "authError.json")));

        //triggering a call with the need of authentication, triggers a login
        mTestInjector.getRepository().getUser(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Assert.assertEquals("We get a 200", 200, response.code());

                latch1.countDown();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // should not fail..
                latch1.countDown();

            }
        });

        latch1.await();

        // here comes the test, if the submit button leads to a success!
        final CountDownLatch latch2 = new CountDownLatch(1);

        final String userName = "TestUser";

        viewModel.setListener(
                new LoginActivityViewModel.LoginListener() {
                    @Override
                    public void onRegisterRequested() {
                    }

                    @Override
                    public void onAccountLoginCompleted(@NotNull Intent loginIntent) {
                        String actualToken = loginIntent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
                        String actualAccountName = loginIntent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                        Assert.assertEquals("token should be 1234", "1234", actualToken);
                        Assert.assertEquals("account name should be ", userName, actualAccountName);

                        latch2.countDown();
                    }
                });

        server.enqueue(new MockResponse() // the user login mock
                .setResponseCode(200)
                .setBody(MockUtil.getJsonFromFile(mContext, "oauthtoken.json")));

        viewModel.login(mActivityRule.getActivity().getApplicationContext(), userName, "adad");

        latch2.await();
    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
        server.shutdown();
    }

}
