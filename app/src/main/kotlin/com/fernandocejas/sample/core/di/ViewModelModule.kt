package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.core.di.viewmodel.ViewModelFactory
import com.fernandocejas.sample.features.movies.MovieDetailsViewModel
import com.fernandocejas.sample.features.movies.MoviesViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get(), get()) }
    factory { ViewModelFactory(get()) }
}