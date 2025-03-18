package dadm.vamactor.quotationshake.di

import dadm.vamactor.quotationshake.data.favourites.FavouritesDataSource
import dadm.vamactor.quotationshake.data.favourites.FavouritesDataSourceImpl
import dadm.vamactor.quotationshake.data.favourites.FavouritesRepository
import dadm.vamactor.quotationshake.data.favourites.FavouritesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FavouritesBinderModule {
    @Binds
    abstract fun bindFavouritesDataSource(impl: FavouritesDataSourceImpl): FavouritesDataSource

    @Binds
    abstract fun bindFavouritesRepository(impl: FavouritesRepositoryImpl): FavouritesRepository

}