package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.features.movies.*
import org.koin.dsl.module.module

val domainModule = module{

    single(override = true) { Network() as MoviesRepository }

    single { GetMovieDetails() }

    single { GetMovies() }

    single { PlayMovie() }
}