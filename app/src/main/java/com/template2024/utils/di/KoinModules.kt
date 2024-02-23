package com.template2024.utils.di

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.template2024.BuildConfig
import com.template2024.data.repositories.RecipesRepositoryImpl
import com.template2024.data.sources.local.Database
import com.template2024.data.sources.remote.api.API
import com.template2024.data.sources.remote.interceptor.AuthInterceptor
import com.template2024.domain.repositories.RecipesRepository
import com.template2024.domain.usecases.GetCategoriesUseCase
import com.template2024.ui.screens.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}

val repositoryModule = module {
    single<RecipesRepository> { RecipesRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    single { GetCategoriesUseCase(get()) }
}

val apiModule = module {
    fun provideRetrofitApi(retrofit: Retrofit): API {
        return retrofit.create(API::class.java)
    }

    single { provideRetrofitApi(get()) }
}

val retrofitModule = module {
    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    fun provideAuthInterceptor() : AuthInterceptor {
        return AuthInterceptor()
    }

    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    fun provideRetrofit(factory: Gson, okHttpClient: OkHttpClient): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(factory))

        return retrofitBuilder.build()
    }

    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideAuthInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get(), get()) }
}

val databaseModule = module {
    fun provideDatabase(context: Context) : Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "appDatabase"
        /* Add migrations when updating database structure */
        ).build()
    }

    single {
        provideDatabase(androidContext())
    }
}