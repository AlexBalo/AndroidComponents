package com.balocco.androidcomponents.data

import com.balocco.androidcomponents.TestUtils
import com.balocco.androidcomponents.data.local.LocalDataSource
import com.balocco.androidcomponents.data.model.MoviesPage
import com.balocco.androidcomponents.data.remote.RemoteDataSource
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: MoviesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = MoviesRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `When loading top rated movies, loads movies from local data source`() {
        val movies = mutableListOf(TestUtils.createMovie("1"), TestUtils.createMovie("2"))
        whenever(localDataSource.fetchAllMoviesSortedByRating()).thenReturn(Flowable.just(movies))

        repository.loadTopRatedMovies().test().assertResult(movies)
    }

    @Test
    fun `When inserting movies, inserts movies in local data source`() {
        val movies = mutableListOf(TestUtils.createMovie("1"), TestUtils.createMovie("2"))
        whenever(localDataSource.insertMovies(movies)).thenReturn(Completable.complete())

        repository.storeTopRatedMovies(movies).test().assertComplete()
    }

    @Test
    fun `When fetching top rated movies, no page specified requests first page from source`() {
        val moviesPage = MoviesPage(
            page = 1,
            totalPages = 10,
            totalResults = 1000,
            results = mutableListOf()
        )
        whenever(remoteDataSource.fetchTopRatedMovies()).thenReturn(Single.just(moviesPage))

        repository.fetchTopRatedMovies().test().assertResult(moviesPage)
    }

    @Test
    fun `When fetching top rated movies, page specified requests specified page from source`() {
        val moviesPage = MoviesPage(
            page = 3,
            totalPages = 10,
            totalResults = 1000,
            results = mutableListOf()
        )
        whenever(remoteDataSource.fetchTopRatedMovies(3)).thenReturn(Single.just(moviesPage))

        repository.fetchTopRatedMovies(3).test().assertResult(moviesPage)
    }
}