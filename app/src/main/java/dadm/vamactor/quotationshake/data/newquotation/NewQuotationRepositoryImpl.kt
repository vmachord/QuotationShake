package dadm.vamactor.quotationshake.data.newquotation

import dadm.vamactor.quotationshake.utils.NoInternetException
import domain.model.Quotation
import javax.inject.Inject
import dadm.vamactor.quotationshake.data.newquotation.model.toDomain

class NewQuotationRepositoryImpl  @Inject constructor(private val dataSource: NewQuotationDataSource,private val connectivityChecker: ConnectivityChecker):NewQuotationRepository {
    override suspend fun getNewQuotation(): Result<Quotation> {
        return if (connectivityChecker.isConnectionAvailable()) {
            val language = arrayOf("en", "ru", "xx").random();
            val response = dataSource.getNewQuotation(language)
            response.toDomain()
        } else {
            Result.failure(NoInternetException())
        }
    }
}