package com.example.imperative.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imperative.model.TVShow

@Database(entities = [TVShow::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTVShowDao(): TVShowDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null
        fun getAppDatabaseInstance(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DB_TV_SHOWS"
                ).fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()
            }
            return DB_INSTANCE!!
        }
    }
}