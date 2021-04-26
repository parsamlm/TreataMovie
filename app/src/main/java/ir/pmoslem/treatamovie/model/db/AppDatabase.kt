package ir.pmoslem.treatamovie.model.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class, MovieDetails::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    abstract fun getMovieDetailsDao(): MovieDetailsDao
}