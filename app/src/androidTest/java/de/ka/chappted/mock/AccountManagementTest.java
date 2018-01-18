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
import de.ka.chappted.api.Repository;
import de.ka.chappted.auth.OAuthUtils;
import de.ka.chappted.auth.login.LoginActivityViewModel;
import de.ka.chappted.commons.arch.injection.AppModule;
import de.ka.chappted.commons.arch.injection.ChapptedComponent;
import de.ka.chappted.commons.arch.injection.DaggerChapptedComponent;
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
        OAuthUtils.INSTANCE.deleteOAuthAccount(mContext.getApplicationContext());
    }

    @Test
    public void test_login_after_401() throws Exception {

        server.enqueue(new MockResponse() // first 401 triggers 401 check -> register is presented
                .setResponseCode(401)
                .setBody(MockUtil.getJsonFromFile(mContext, "authError.json")));
        server.enqueue(new MockResponse()
                .setResponseCode(401) // second one says that the check has failed -> stop 401 check
                .setBody(MockUtil.getJsonFromFile(mContext, "authError.json")));

        // here comes the test where we force a 401
        final CountDownLatch latch1 = new CountDownLatch(1);

        LoginActivityViewModel viewModel = new LoginActivityViewModel(mActivityRule.getActivity().getApplication());
        HttpUrl baseUrl = server.url("/");
        TestApiModule testApiModule = new TestApiModule(baseUrl.toString());
        //build a new Dagger2 component using the test override
        ChapptedComponent componentWithOverride = DaggerChapptedComponent.builder()
                //mind the Test in the class name, see a class above
                .appModule(new AppModule((App) mActivityRule.getActivity().getApplication()))
                .apiModule(testApiModule)
                .build();

        componentWithOverride.inject(viewModel);

        Repository repository = testApiModule.provideRepository();

        //triggering a call with the need of authentication, triggers a login
        repository.getUser(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Assert.assertEquals("The response code should be 401", 401, response.code());

                latch1.countDown();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });

        latch1.await();

        server.enqueue(new MockResponse() // the user tries to login and succeeds!
                .setResponseCode(200)
                .setBody(MockUtil.getJsonFromFile(mContext, "oauthtoken.json")));

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

        viewModel.login(mActivityRule.getActivity(), userName, "adad");

        latch2.await();
    }


    @After
    public void tearDown() throws Exception {
        super.tearDown();
        server.shutdown();
    }

}
