package ir.pmoslem.treatamovie.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.treatamovie.model.db.AppDatabase
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.db.MovieDetailsDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "db_movies").build()

    @Singleton
    @Provides
    fun provideMoviesDao(appDatabase: AppDatabase): MovieDao = appDatabase.getMovieDao()

    @Singleton
    @Provides
    fun provideMovieDetailsDao(appDatabase: AppDatabase): MovieDetailsDao =
        appDatabase.getMovieDetailsDao()

}