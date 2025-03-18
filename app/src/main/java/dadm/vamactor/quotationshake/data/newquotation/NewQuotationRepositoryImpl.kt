package dadm.vamactor.quotationshake.data.newquotation

import dadm.vamactor.quotationshake.utils.NoInternetException
import domain.model.Quotation
import javax.inject.Inject
import dadm.vamactor.quotationshake.data.newquotation.model.toDomain
import dadm.vamactor.quotationshake.data.settings.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewQuotationRepositoryImpl  @Inject constructor(private val dataSource: NewQuotationDataSource,private val connectivityChecker: ConnectivityChecker, private val settingsRepository: SettingsRepository):NewQuotationRepository {
    private lateinit var language: String
    init {
        CoroutineScope(SupervisorJob()).launch {
            settingsRepository.getLanguage().collect { languageCode ->
                language = languageCode.ifEmpty{ "en" }
            }
        }
    }
    override suspend fun getNewQuotation(): Result<Quotation> {
        return if (connectivityChecker.isConnectionAvailable()) {
            val response = dataSource.getNewQuotation(language)
            response.toDomain()
        } else {
            Result.failure(NoInternetException())
        }
    }
}