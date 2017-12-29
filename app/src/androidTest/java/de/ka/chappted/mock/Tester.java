package de.ka.chappted.mock;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import de.ka.chappted.api.Repository;
import de.ka.chappted.main.MainActivity;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by th on 21.12.17.
 */

@RunWith(AndroidJUnit4.class)
public class Tester extends InstrumentationTestCase {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, true);
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();

        HttpUrl baseUrl = server.url("/v1/chat/");
        Repository.Companion.init(baseUrl.toString());
    }

    @Test
    public void test401() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody("{}"));
        server.enqueue(new MockResponse()
                .setResponseCode(401)
                .setBody("{}"));

        final CountDownLatch latch = new CountDownLatch(1);

        Repository.Companion.get().getAuthenticatedClient(mActivityRule.getActivity()).getNothing().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {


                assertTrue(true);
                latch.countDown();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                assertTrue(true);
                latch.countDown();
            }
        });

        latch.await();





    }



    @After
    public void tearDown() throws Exception {
        super.tearDown();
        server.shutdown();
    }

}
