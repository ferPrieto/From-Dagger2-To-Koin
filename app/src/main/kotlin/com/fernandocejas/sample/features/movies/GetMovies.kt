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
package com.fernandocejas.sample.features.movies

import com.fernandocejas.sample.core.interactor.UseCase
import com.fernandocejas.sample.core.interactor.UseCase.None
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class GetMovies : UseCase<List<Movie>, None>(), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    override suspend fun run(params: None) = moviesRepository.movies()
}
