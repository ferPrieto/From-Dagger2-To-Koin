/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample

import android.app.Application
import com.fernandocejas.sample.core.di.appModule
import com.fernandocejas.sample.core.di.dataModule
import com.fernandocejas.sample.core.di.domainModule
import com.fernandocejas.sample.core.di.presentationModule
import com.squareup.leakcanary.BuildConfig
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, presentationModule, domainModule, dataModule))
        this.initializeLeakDetection()
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
