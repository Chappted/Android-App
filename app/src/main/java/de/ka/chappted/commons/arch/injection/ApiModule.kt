package de.ka.chappted.commons.arch.injection

import dagger.Module
import dagger.Provides
import de.ka.chappted.api.Repository
import javax.inject.Singleton

@Module
open class ApiModule {

    @Provides
    @Singleton
    open fun provideRepository(): Repository {
        return Repository()
    }
}