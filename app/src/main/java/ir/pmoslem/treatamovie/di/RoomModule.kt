package ir.pmoslem.treatamovie.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.treatamovie.model.AppDatabase
import ir.pmoslem.treatamovie.model.MovieDao
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

}