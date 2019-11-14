package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.features.movies.MoviesApi
import org.koin.dsl.module.module
import retrofit2.Retrofit

val apiModule = module {
    single(createOnStart = false) { get<Retrofit>().create(MoviesApi::class.java) }
}