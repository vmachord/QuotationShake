package dadm.vamactor.quotationshake.data.favourites

import dadm.vamactor.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavouritesDataSourceImpl @Inject constructor(private val favouritesDao: FavouritesDao): FavouritesDataSource{
    override fun getAllFavourites(): Flow<List<DatabaseQuotationDto>> {
        return favouritesDao.getAllFavourites()
    }

    override fun getFavouriteById(id: Long): Flow<DatabaseQuotationDto?> {
        return favouritesDao.getFavouriteById(id)
    }

    override suspend fun insertFavourite(quotation: DatabaseQuotationDto?) {
        if (quotation != null)
            favouritesDao.addFavourite(quotation)
    }

    override suspend fun deleteFavourite(quotation: DatabaseQuotationDto?) {
        if (quotation != null)
            favouritesDao.removeFavourite(quotation)
    }

    override suspend fun deleteAllFavourites() {
        favouritesDao.clearFavourites()
    }
}