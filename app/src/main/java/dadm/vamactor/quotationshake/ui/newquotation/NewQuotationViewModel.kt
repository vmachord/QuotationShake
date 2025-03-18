package dadm.vamactor.quotationshake.ui.newquotation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.vamactor.quotationshake.data.favourites.FavouritesRepository
import dadm.vamactor.quotationshake.data.newquotation.NewQuotationRepository
import dadm.vamactor.quotationshake.data.settings.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.Quotation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.map

@HiltViewModel
class NewQuotationViewModel @Inject constructor(private val repository: NewQuotationRepository, private val settingsRepository: SettingsRepository, private val favouritesRepository: FavouritesRepository): ViewModel() {
    val userName: StateFlow<String> = settingsRepository.getUserName()
        .stateIn(
            scope = viewModelScope,
            initialValue = "",
            started = SharingStarted.WhileSubscribed()
        )
    private val _currentQuotation = MutableStateFlow<Quotation?>(null)
    val currentQuotation: StateFlow<Quotation?> = _currentQuotation.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _errorState = MutableStateFlow<Throwable?>(null)
    val errorState: StateFlow<Throwable?> = _errorState

    fun resetError() {
        _errorState.value = null
    }
    fun getNewQuotation() {
        _isLoading.value = true
        viewModelScope.launch {
                repository.getNewQuotation().fold(
                    onSuccess = { quotation ->
                        _currentQuotation.value = quotation
                    },
                    onFailure = { error ->
                        _errorState.value = error
                    }
                )
            _isLoading.value = false
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val isAddToFavouritesVisible = currentQuotation.flatMapLatest { currentQuotation ->
        if (currentQuotation == null) flowOf(false)
        else favouritesRepository.getFavouriteById(currentQuotation.id.substringBeforeLast("/").substringAfterLast("/").toLong(16))
            .map { quotationInDatabase ->
                quotationInDatabase == null
            }
    }.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    fun addToFavourites(){
        viewModelScope.launch {
            val quotation = _currentQuotation.value
            if (quotation != null) {
                favouritesRepository.addFavourite(quotation)
            }
        }
    }
}