package dadm.vamactor.quotationshake.data.favourites

import domain.model.Quotation
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    suspend fun addFavourite(quotation: Quotation?)

    suspend fun removeFavourite(quotation: Quotation?)

    fun getAllFavourites(): Flow<List<Quotation?>>

    fun getFavouriteById(id: Long): Flow<Quotation?>

    suspend fun clearFavourites()
}