package de.ka.chappted.mock;

import java.util.ArrayList;

import de.ka.chappted.api.Client;
import de.ka.chappted.api.model.OAuthToken;
import retrofit2.Call;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by th on 21.12.17.
 */

public class MockService implements Client {

    private final BehaviorDelegate<Client> delegate;

    public MockService(BehaviorDelegate<Client> service) {
        this.delegate = service;
    }

    @Override
    public Call<OAuthToken> getNewAccessToken(String refreshToken, String clientId, String clientSecret, String redirectUri, String grantType) {
        return null;
    }

    @Override
    public Call<OAuthToken> getNewTokens(String username, String password, String clientId, String clientSecret, String redirectUri, String grantType) {
        return null;
    }

    @Override
    public Call<Void> getNothing() {
        return null;
    }

    /*
    @Override
    public Call<QuoteOfTheDayResponse> getQuoteOfTheDay() {
        QuoteOfTheDayResponse quoteOfTheDayResponse = new QuoteOfTheDayResponse();
        Contents contents = new Contents();
        Quote quote = new Quote();
        quote.setQuote("Always code as if the guy who ends up maintaining your code will be a violent psychopath who knows where you live.");
        ArrayList<Quote> quotes = new ArrayList<>();
        quotes.add(quote);
        contents.setQuotes(quotes);
        quoteOfTheDayResponse.setContents(contents);
        return delegate.returningResponse(quoteOfTheDayResponse).getQuoteOfTheDay();
    }
    */
}
