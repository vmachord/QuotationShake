package dadm.vamactor.quotationshake.data.favourites

import dadm.vamactor.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow

interface FavouritesDataSource {
    fun getAllFavourites(): Flow<List<DatabaseQuotationDto>>
    fun getFavouriteById(id: Long): Flow<DatabaseQuotationDto?>
    suspend fun insertFavourite(quotation: DatabaseQuotationDto?)
    suspend fun deleteFavourite(quotation: DatabaseQuotationDto?)
    suspend fun deleteAllFavourites()
}