package dadm.vamactor.quotationshake.data.favourites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dadm.vamactor.quotationshake.data.favourites.FavouritesContract

@Entity(tableName = FavouritesContract.FavouritesTable.TABLE_NAME)
data class DatabaseQuotationDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FavouritesContract.FavouritesTable.COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = FavouritesContract.FavouritesTable.COLUMN_TEXT)
    val text: String,

    @ColumnInfo(name = FavouritesContract.FavouritesTable.COLUMN_AUTHOR)
    val author: String
)