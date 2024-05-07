package com.nomaan.simplerecipes.utils.di

import android.content.Context
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nomaan.simplerecipes.BuildConfig
import com.nomaan.simplerecipes.data.repositories.DataStoreRepository
import com.nomaan.simplerecipes.data.repositories.RecipesRepositoryImpl
import com.nomaan.simplerecipes.data.sources.local.Database
import com.nomaan.simplerecipes.data.sources.remote.api.API
import com.nomaan.simplerecipes.data.sources.remote.interceptor.NetworkInterceptor
import com.nomaan.simplerecipes.domain.repositories.RecipesRepository
import com.nomaan.simplerecipes.domain.usecases.DeleteMealDetailsUseCase
import com.nomaan.simplerecipes.domain.usecases.GetCategoriesUseCase
import com.nomaan.simplerecipes.domain.usecases.GetMealDetailsUseCase
import com.nomaan.simplerecipes.domain.usecases.GetMealsByCategoryUseCase
import com.nomaan.simplerecipes.domain.usecases.GetSavedMealsUseCase
import com.nomaan.simplerecipes.domain.usecases.SaveMealDetailsUseCase
import com.nomaan.simplerecipes.ui.screens.home.HomeViewModel
import com.nomaan.simplerecipes.ui.screens.mealdetails.MealDetailsViewModel
import com.nomaan.simplerecipes.ui.screens.meals.MealsViewModel
import com.nomaan.simplerecipes.ui.screens.savedmealslist.SavedMealsListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MealsViewModel(get(), get(), get()) }
    viewModel { MealDetailsViewModel(get(), get(), get(), get()) }
    viewModel { SavedMealsListViewModel(get()) }
}

val repositoryModule = module {
    single<RecipesRepository> { RecipesRepositoryImpl(get(), get()) }
}

val useCaseModule = module {
    single { GetCategoriesUseCase(get()) }
    single { GetMealsByCategoryUseCase(get()) }
    single { GetSavedMealsUseCase(get()) }
    single { GetMealDetailsUseCase(get()) }
    single { SaveMealDetailsUseCase(get()) }
    single { DeleteMealDetailsUseCase(get()) }
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

    fun provideAuthInterceptor(): NetworkInterceptor {
        return NetworkInterceptor()
    }

    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(networkInterceptor)
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
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "appDatabase"
            /* Add migrations when updating database structure */
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        provideDatabase(androidContext())
    }
}

val dataStoreModule = module {
    fun provideDataStore(context: Context): DataStoreRepository {
        return DataStoreRepository.getInstance(context)
    }

    single {
        provideDataStore(androidContext())
    }
}