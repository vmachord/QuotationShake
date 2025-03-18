package dadm.vamactor.quotationshake.data.favourites

import dadm.vamactor.quotationshake.data.favourites.model.DatabaseQuotationDto
import domain.model.Quotation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.map


class FavouritesRepositoryImpl @Inject constructor(private val favoritesDataSource: FavouritesDataSource): FavouritesRepository {
    override fun getAllFavourites(): Flow<List<Quotation?>> {
        return favoritesDataSource.getAllFavourites()
            .map { databaseQuotationList ->
                databaseQuotationList.map { databaseQuotationDto ->
                    convertToQuotation(databaseQuotationDto)
                }
            }
    }
    override fun getFavouriteById(id: Long): Flow<Quotation?> {
        return favoritesDataSource.getFavouriteById(id)
            .map { databaseQuotationDto ->
                convertToQuotation(databaseQuotationDto)
            }
    }

    override suspend fun addFavourite(quotation: Quotation?) {
        val databaseQuotationDto = convertToDatabaseQuotationDto(quotation)
        favoritesDataSource.insertFavourite(databaseQuotationDto)
    }

    override suspend fun removeFavourite(quotation: Quotation?) {
        val databaseQuotationDto = convertToDatabaseQuotationDto(quotation)
        favoritesDataSource.deleteFavourite(databaseQuotationDto)
    }

    private fun convertToQuotation(dto: DatabaseQuotationDto?): Quotation? {
        if (dto != null) {
            return Quotation(
                id = dto.id.toString(),
                text = dto.text,
                author = dto.author
            )
        } else {
            return null
        }
    }

    override suspend fun clearFavourites() {
        favoritesDataSource.deleteAllFavourites()
    }

    private fun convertToDatabaseQuotationDto(quotation: Quotation?): DatabaseQuotationDto? {
        if (quotation != null) {
            return DatabaseQuotationDto(
                id = quotation.id.substringBeforeLast("/").substringAfterLast("/").toLong(16),
                text = quotation.text,
                author = quotation.author
            )
        } else {
            return null;
        }
    }
}