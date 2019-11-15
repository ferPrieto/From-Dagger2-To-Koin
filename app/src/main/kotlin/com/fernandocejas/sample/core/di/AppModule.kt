package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.core.navigation.RouteActivity
import com.fernandocejas.sample.features.login.Authenticator
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val appModule = module(override = true) {
    factory { Navigator(androidApplication()) }

    single { RouteActivity() }

    single { Authenticator() }

}