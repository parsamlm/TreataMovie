package ir.pmoslem.treatamovie.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.pmoslem.treatamovie.model.server.ApiService
import ir.pmoslem.treatamovie.model.db.MovieDao
import ir.pmoslem.treatamovie.model.db.MovieDetailsDao
import ir.pmoslem.treatamovie.model.repository.ContentFavoriteRepository
import ir.pmoslem.treatamovie.model.repository.DetailsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideContentFavoriteRepository(api: ApiService, movieDao: MovieDao): ContentFavoriteRepository =
        ContentFavoriteRepository(api, movieDao)


    @Singleton
    @Provides
    fun provideDetailsRepository(
        api: ApiService,
        movieDao: MovieDao,
        movieDetailsDao: MovieDetailsDao
    ): DetailsRepository =
        DetailsRepository(api, movieDao, movieDetailsDao)


}