package ir.pmoslem.treatamovie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.treatamovie.model.ApiService
import ir.pmoslem.treatamovie.model.MovieDao
import ir.pmoslem.treatamovie.model.repository.ContentRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideContentRepository(api: ApiService, movieDao: MovieDao): ContentRepository = ContentRepository(api, movieDao)



}