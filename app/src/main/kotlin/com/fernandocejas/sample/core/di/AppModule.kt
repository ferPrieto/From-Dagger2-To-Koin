package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.features.login.Authenticator
import com.fernandocejas.sample.features.movies.MovieDetailsAnimator
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val appModule = module {
    factory { Navigator(androidApplication()) }
}