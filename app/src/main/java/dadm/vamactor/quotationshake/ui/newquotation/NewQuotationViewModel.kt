package dadm.vamactor.quotationshake.ui.newquotation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dadm.vamactor.quotationshake.data.newquotation.NewQuotationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.model.Quotation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import javax.inject.Inject

@HiltViewModel
class NewQuotationViewModel @Inject constructor(private val repository: NewQuotationRepository): ViewModel() {
    private val _userName = MutableStateFlow(getUserName())
    val userName: StateFlow<String> = _userName.asStateFlow()
    private val _currentQuotation = MutableStateFlow<Quotation?>(null)
    val currentQuotation: StateFlow<Quotation?> = _currentQuotation.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _floatingButton = MutableStateFlow(false)
    val floatingButton: StateFlow<Boolean> = _floatingButton.asStateFlow()
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
                    _floatingButton.value = true
                },
                onFailure = { error ->
                    _errorState.value = error
                    _floatingButton.value = false
                }
            )
            _isLoading.value = false
        }
    }

    fun addToFavourites(){
        _floatingButton.value = false
    }

    private fun getUserName(): String {
        val names = setOf("Alice", "Bob", "Charlie", "David", "Emma", "")
        return names.random()
    }
}