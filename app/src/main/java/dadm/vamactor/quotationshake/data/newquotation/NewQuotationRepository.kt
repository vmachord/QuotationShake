package dadm.vamactor.quotationshake.data.newquotation

import domain.model.Quotation

interface NewQuotationRepository {
    suspend fun getNewQuotation(): Result<Quotation>;
}