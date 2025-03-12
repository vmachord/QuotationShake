package dadm.vamactor.quotationshake.ui.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.Quotation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(): ViewModel() {
    private val _favoriteQuotations = MutableStateFlow<List<Quotation>>(getFavoriteQuotations())
    val favoriteQuotations: StateFlow<List<Quotation>> = _favoriteQuotations

    val isDeleteAllMenuVisible = favoriteQuotations
        .map { list -> list.isNotEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = true
        )

    fun getFavoriteQuotations(): List<Quotation> {
        val quotations = mutableListOf<Quotation>()

        quotations.add(
            Quotation(
                id = "100",
                text = "Life is like riding a bicycle. To keep your balance, you must keep moving.",
                author = "Albert Einstein"
            )
        )
        quotations.add(
            Quotation(
                id = "101",
                text = "Happiness depends upon ourselves.",
                author = "Anonymous"
            )
        )
        quotations.addAll(List(18) { i ->
            Quotation(
                id = "$i",
                text = "Random quotation #$i",
                author = if (i % 2 == 0) "Author $i" else "Anonymous"
            )
        })

        return quotations
    }

    fun deleteQuotationAtPosition(position: Int) {
        val currentQuotations = _favoriteQuotations.value
        val updatedQuotations = currentQuotations.minus(currentQuotations[position])
        _favoriteQuotations.value = updatedQuotations
    }

    fun deleteAllQuotations(){
        _favoriteQuotations.value = emptyList()
    }
}