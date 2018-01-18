package de.ka.chappted.commons.arch.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import de.ka.chappted.App
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApp(): App = app

}

