package com.son.movie.database

import android.content.Context
import androidx.core.content.OnConfigurationChangedProvider
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Dao
interface MovieDao {
    @Query("select * from databasemovie")
    fun getMovies(): LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg movies: DatabaseMovie)
}

@Database(entities = [DatabaseMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE: MovieDatabase

fun getDatabase(context: Context): MovieDatabase {
    synchronized(MovieDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE =
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "movie"
                ).build()
        }
    }
    return INSTANCE
}