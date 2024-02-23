package com.template2024

import android.app.Application
import com.template2024.utils.di.apiModule
import com.template2024.utils.di.databaseModule
import com.template2024.utils.di.repositoryModule
import com.template2024.utils.di.retrofitModule
import com.template2024.utils.di.useCaseModule
import com.template2024.utils.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class Template2024Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Template2024Application)
            modules(listOf(viewModelModule, repositoryModule, useCaseModule, apiModule, retrofitModule, databaseModule))
        }
    }
}