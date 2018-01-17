package de.ka.chappted.mock;

import org.mockito.Mock;

import de.ka.chappted.api.Repository;
import de.ka.chappted.injection.ApiModule;

public class TestApiModule extends ApiModule {

    @Mock
    private Repository mRepository;

    public TestApiModule(String url) {
        mRepository = new Repository(url, true);
    }

    @Override
    public Repository provideRepository() {
        return mRepository;
    }
}
