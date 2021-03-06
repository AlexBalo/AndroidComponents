package com.balocco.androidcomponents.data.remote

import com.balocco.androidcomponents.data.model.Genres
import com.balocco.androidcomponents.data.model.MoviesPage
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL_INTERNAL = "https://api.themoviedb.org/"
private const val BASE_IMAGE_URL_INTERNAL = "https://image.tmdb.org/t/p/w500"
private const val API_VERSION = "3"

interface RemoteDataSource {
    @GET("${API_VERSION}/movie/top_rated")
    fun fetchTopRatedMovies(@Query("page") page: Int = 1): Single<MoviesPage>

    @GET("${API_VERSION}/genre/movie/list")
    fun fetchGenres(): Single<Genres>

    companion object {
        const val BASE_URL = BASE_URL_INTERNAL
        const val BASE_IMAGE_URL = BASE_IMAGE_URL_INTERNAL
    }
}