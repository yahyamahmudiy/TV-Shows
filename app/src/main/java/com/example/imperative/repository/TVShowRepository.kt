package com.example.imperative.repository

import com.example.imperative.db.TVShowDao
import com.example.imperative.model.TVShow
import com.example.imperative.networking.TVShowService
import dagger.Provides
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowService: TVShowService,private val tvShowDao: TVShowDao) {
    /* *
    * Retrofit related
    * */

    suspend fun apiTvShowPopular(page:Int) = tvShowService.apiTVShowPopular(page)

    suspend fun apiTVShowDetails(q:Int) = tvShowService.apiTVShowDetails(q)

    /* *
    * Room related
    * */

    suspend fun getTVShowFromDB() = tvShowDao.getTVShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tvShowDao.insertTVShowToDB(tvShow)
    suspend fun deleteTVShowFromDB() = tvShowDao.deleteTvShowsFromDB()
}