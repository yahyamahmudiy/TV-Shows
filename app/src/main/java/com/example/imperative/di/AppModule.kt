package com.example.imperative.di

import android.app.Application
import com.example.imperative.db.AppDatabase
import com.example.imperative.db.TVShowDao
import com.example.imperative.networking.Server.Development
import com.example.imperative.networking.Server.IS_TESTER
import com.example.imperative.networking.Server.Pruduction
import com.example.imperative.networking.TVShowService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /* *
    * Retrofit related
    * */
    @Provides
    fun server(): String {
        if (IS_TESTER) return Development()
        return Pruduction()
    }

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService():TVShowService{
        return retrofitClient().create(TVShowService::class.java)
    }

    /* *
    * Room related
    * */

    @Provides
    @Singleton
    fun appDatabase(context:Application): AppDatabase {

        return AppDatabase.getAppDatabaseInstance(context)
    }

    @Provides
    @Singleton
    fun TVShowDao(appDatabase: AppDatabase):TVShowDao{
        return appDatabase.getTVShowDao()
    }
}