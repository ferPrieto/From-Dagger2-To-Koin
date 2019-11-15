package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.features.movies.*
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MoviesViewModel() }

    viewModel { MovieDetailsViewModel() }

    single { GetMovieDetails() }

    single { PlayMovie() }

    single { MoviesAdapter() }

    single { GetMovies() }

}