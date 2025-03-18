package dadm.vamactor.quotationshake.di

import android.content.Context
import androidx.room.Room
import dadm.vamactor.quotationshake.data.favourites.FavouritesContract
import dadm.vamactor.quotationshake.data.favourites.FavouritesDao
import dadm.vamactor.quotationshake.data.favourites.FavouritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavouritesProviderModule {
    @Provides
    @Singleton
    fun provideFavouritesDatabase(@ApplicationContext context: Context): FavouritesDatabase {
        return Room.databaseBuilder(
            context,
            FavouritesDatabase::class.java,
            FavouritesContract.DATABASE_NAME
        ).build()
    }
    @Provides
    fun provideFavouritesDao(database: FavouritesDatabase): FavouritesDao {
        return database.favouritesDao()
    }
}