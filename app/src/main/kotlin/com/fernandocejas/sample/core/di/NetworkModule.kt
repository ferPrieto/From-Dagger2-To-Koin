package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.BuildConfig
import com.fernandocejas.sample.core.platform.NetworkHandler
import com.fernandocejas.sample.features.movies.MoviesRepository
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            })
        }.build()
    }

    single {
        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .build()

    }

    single { GsonBuilder().create() }

    single { MoviesRepository.Network(get(), get()) }

    single { NetworkHandler(get()) }
}
