package com.nomaan.simplerecipes

import android.app.Application
import com.nomaan.simplerecipes.utils.di.apiModule
import com.nomaan.simplerecipes.utils.di.dataStoreModule
import com.nomaan.simplerecipes.utils.di.databaseModule
import com.nomaan.simplerecipes.utils.di.repositoryModule
import com.nomaan.simplerecipes.utils.di.retrofitModule
import com.nomaan.simplerecipes.utils.di.useCaseModule
import com.nomaan.simplerecipes.utils.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SimpleRecipesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SimpleRecipesApplication)
            modules(listOf(viewModelModule, repositoryModule, useCaseModule, apiModule, retrofitModule, databaseModule, dataStoreModule))
        }
    }
}