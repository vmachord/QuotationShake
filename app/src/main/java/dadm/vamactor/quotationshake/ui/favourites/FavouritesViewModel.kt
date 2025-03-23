package dadm.vamactor.quotationshake.ui.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.vamactor.quotationshake.data.favourites.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.Quotation
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val favouritesRepository : FavouritesRepository): ViewModel() {
   val favoriteQuotations: StateFlow<List<Quotation?>> = favouritesRepository.getAllFavourites()
       .stateIn(
           scope = viewModelScope,
           initialValue = listOf(),
           started = SharingStarted.WhileSubscribed()
       )

    val isDeleteAllMenuVisible = favoriteQuotations
        .map { list -> list.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )
    fun deleteQuotationAtPosition(position: Int) {
        viewModelScope.launch {
            val currentQuotations = favoriteQuotations.value

            if (position in currentQuotations.indices) {
                val quotationToDelete = currentQuotations[position]
                Log.d("DeleteQuotation", "Quotation to delete: $quotationToDelete")
                favouritesRepository.removeFavourite(quotationToDelete)
            }
        }
    }

    fun deleteAllQuotations() {
        viewModelScope.launch {
            favouritesRepository.clearFavourites()
        }
    }
}