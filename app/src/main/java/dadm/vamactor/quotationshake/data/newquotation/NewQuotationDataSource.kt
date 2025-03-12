package dadm.vamactor.quotationshake.data.newquotation

import dadm.vamactor.quotationshake.data.newquotation.model.RemoteQuotationDto
import retrofit2.Response

interface NewQuotationDataSource {
    suspend fun getNewQuotation(language : String): Response<RemoteQuotationDto>;
}