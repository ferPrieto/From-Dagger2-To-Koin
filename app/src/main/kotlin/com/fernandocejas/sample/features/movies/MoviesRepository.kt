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

import com.fernandocejas.sample.core.exception.Failure
import com.fernandocejas.sample.core.exception.Failure.NetworkConnection
import com.fernandocejas.sample.core.exception.Failure.ServerError
import com.fernandocejas.sample.core.functional.Either
import com.fernandocejas.sample.core.functional.Either.Left
import com.fernandocejas.sample.core.functional.Either.Right
import com.fernandocejas.sample.core.platform.NetworkHandler
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Call

interface MoviesRepository : KoinComponent {
    fun movies(): Either<Failure, List<Movie>>
    fun movieDetails(movieId: Int): Either<Failure, MovieDetails>
}

class Network : MoviesRepository, KoinComponent {

    private val networkHandler: NetworkHandler by inject()
    private val service: MoviesService by inject()

    override fun movies(): Either<Failure, List<Movie>> {
        return when (networkHandler.isConnected) {
            true -> request(service.movies(), { it.map { it.toMovie() } }, emptyList())
            false, null -> Left(NetworkConnection)
        }
    }

    override fun movieDetails(movieId: Int): Either<Failure, MovieDetails> {
        return when (networkHandler.isConnected) {
            true -> request(service.movieDetails(movieId), { it.toMovieDetails() }, MovieDetailsEntity.empty())
            false, null -> Left(NetworkConnection)
        }
    }

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Right(transform((response.body() ?: default)))
                false -> Left(ServerError)
            }
        } catch (exception: Throwable) {
            Left(ServerError)
        }
    }
}