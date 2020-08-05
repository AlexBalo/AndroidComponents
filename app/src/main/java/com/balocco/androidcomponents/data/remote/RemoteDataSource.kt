package com.balocco.androidcomponents.data.remote

import com.balocco.androidcomponents.data.model.MoviesPage
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {
    @GET("3/movie/top_rated")
    fun fetchTopRatedMovies(@Query("page") page: Int = 1): Single<MoviesPage>
}