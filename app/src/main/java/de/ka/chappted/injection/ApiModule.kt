package de.ka.chappted.injection

import dagger.Module
import dagger.Provides
import de.ka.chappted.api.Repository
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRepository(): Repository{
        return Repository()
    }
}