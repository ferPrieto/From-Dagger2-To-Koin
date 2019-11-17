package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.features.login.Authenticator
import com.fernandocejas.sample.features.movies.*
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {
    viewModel { MoviesViewModel() }

    viewModel { MovieDetailsViewModel() }

    single { MoviesAdapter() }

    single { MovieDetailsAnimator() }

    single { Authenticator() }
}