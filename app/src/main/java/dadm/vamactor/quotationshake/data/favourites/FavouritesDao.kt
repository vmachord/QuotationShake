package dadm.vamactor.quotationshake.data.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dadm.vamactor.quotationshake.data.favourites.model.DatabaseQuotationDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(quotation: DatabaseQuotationDto)

    @Delete
    suspend fun removeFavourite(quotation: DatabaseQuotationDto)

    @Query("SELECT * FROM ${FavouritesContract.FavouritesTable.TABLE_NAME}")
    fun getAllFavourites(): Flow<List<DatabaseQuotationDto>>

    @Query("SELECT * FROM ${FavouritesContract.FavouritesTable.TABLE_NAME} WHERE ${FavouritesContract.FavouritesTable.COLUMN_ID} = :id")
    fun getFavouriteById(id: Long): Flow<DatabaseQuotationDto?>

    @Query("DELETE FROM ${FavouritesContract.FavouritesTable.TABLE_NAME}")
    suspend fun clearFavourites()
}