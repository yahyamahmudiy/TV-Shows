package com.example.imperative

import com.example.imperative.di.AppModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun checkStatusCode() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertEquals(response.code(), 200)
    }

    @Test
    fun responseIsSSuccessful() = runTest {

        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertTrue(response.isSuccessful)

    }

    @Test
    fun checkTVShowListNotNull() = runTest {

        val response = AppModule().tvShowService().apiTVShowPopular(1)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)

    }

    @Test
    fun checkTVShowListSize() = runTest {

        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        assertEquals(tvShowPopular!!.tv_shows.size, 20)

    }

    @Test
    fun checkFirstShowStatus() = runTest {

        val response = AppModule().tvShowService().apiTVShowPopular(1)
        val tvShowPopular = response.body()
        val tvShows = tvShowPopular!!.tv_shows
        val tvShow = tvShows[0]
        assertTrue(tvShow.status == "Running")

    }
}