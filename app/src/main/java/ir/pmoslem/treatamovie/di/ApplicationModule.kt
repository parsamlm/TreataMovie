package ir.pmoslem.treatamovie.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.treatamovie.view.main.MoviesAdapter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideMoviesAdapter(@ApplicationContext context: Context): MoviesAdapter = MoviesAdapter(context)
}