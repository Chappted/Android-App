package de.ka.chapptedapi;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import junit.framework.Assert;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.ka.chapptedapi.auth.OAuthUtils;
import de.ka.chapptedapi.jlsapi.JlsError;
import de.ka.chapptedapi.jlsapi.JlsCallback;
import de.ka.chapptedapi.jlsapi.JlsResponse;
import de.ka.chapptedapi.ui.register.RegisterActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

/**
 * A test suite for account management.
 * <p>
 * Uses an array of testing frameworks:
 * Android Instrumentation, MockWebServer, JUnit
 * <p>
 * Created by Thomas Hofmann on 21.12.17.
 */
@RunWith(AndroidJUnit4.class)
public class AccountManagementTest extends InstrumentationTestCase  {

    private MockWebServer mMockWebServer;
    private Context mContext;

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityRule =
            new ActivityTestRule<>(RegisterActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        super.setUp();

        // completely delete the user account from the device, offer a clean start
        OAuthUtils.INSTANCE.deleteOAuthAccount(mActivityRule.getActivity().getApplicationContext());

        // save the instrumentation test context for easy access
        mContext = InstrumentationRegistry.getInstrumentation().getContext();

        // start server mocking
        mMockWebServer = new MockWebServer();
        mMockWebServer.start();
    }


    @Test
    public void test_mock_get_user() throws Exception {

        // a simple mocked request on getUser() assuming that this will somehow return a 201 code:

        final CountDownLatch waitForGetUser = new CountDownLatch(1);

        ChapptedApi.INSTANCE.init(mMockWebServer.url("/").toString());

        mMockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody("hey"));
        mMockWebServer.enqueue(new MockResponse().setResponseCode(401).setBody("hey"));

        if (ChapptedApi.INSTANCE.getRepository() == null){
            return;
        }

        ChapptedApi.INSTANCE.getRepository().getUser(mContext, new JlsCallback<Void>() { // fire request


            @Override
            public void onSuccess(@NotNull JlsResponse<Void> response) {
                Assert.assertEquals("This should give us the 201 status.",
                        201,
                        response.getCode());

                waitForGetUser.countDown();
            }

            @Override
            public void onFailed(@Nullable JlsError error) {



                throw new RuntimeException("This should never be called!");
            }
        });

        waitForGetUser.await();
    }


    @Test
    public void test_mock_login() throws InterruptedException {

        /*
        // a login is made with the help of a o auth token.

        final CountDownLatch waitForLogin = new CountDownLatch(1);

        final String userName = "TestUser";

        viewModel.setListener(
                new LoginActivityViewModel.LoginListener() {
                    @Override
                    public void onRegisterRequested() {
                        // not in the testing scope
                    }

                    @Override
                    public void onAccountLoginCompleted(@NotNull Intent loginIntent) {
                        String actualToken = loginIntent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
                        String actualAccountName = loginIntent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

                        Assert.assertEquals(
                                "Token should be 1234",
                                "1234",
                                actualToken);

                        Assert.assertEquals(
                                "Account name should be " + userName,
                                userName,
                                actualAccountName);

                        waitForLogin.countDown();
                    }
                });

        mMockWebServer.enqueue(new MockResponse() // the user login mock response
                .setResponseCode(200)
                .setBody(MockUtil.getJsonFromFile(mContext, "oauthtoken.json")));

        // perform the login
        viewModel.login(
                mActivityRule.getActivity().getApplicationContext(),
                userName,
                "anyPassword");

        waitForLogin.await();

        // Confirm that the app made the HTTP requests we were expecting
        RecordedRequest request = mMockWebServer.takeRequest();
        assertEquals(
                "The path for logging in should be /oauth/token ",
                "/oauth/token",
                request.getPath());
                */
    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();

        // shut down the mock web server: close the connection
       mMockWebServer.shutdown();
    }

}
