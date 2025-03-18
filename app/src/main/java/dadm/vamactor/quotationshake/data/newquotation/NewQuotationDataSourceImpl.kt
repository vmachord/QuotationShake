package dadm.vamactor.quotationshake.data.newquotation

import dadm.vamactor.quotationshake.data.newquotation.model.RemoteQuotationDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.random.Random

class NewQuotationDataSourceImpl@Inject constructor(private val retrofit: Retrofit):NewQuotationDataSource {
    private val retrofitQuotationService = retrofit.create(NewQuotationRetrofit::class.java)
    override suspend fun getNewQuotation(language: String): Response<RemoteQuotationDto> {
        val num = Random.nextInt(1, 100)
        return try {
            retrofitQuotationService.getQuotation(language)
        } catch (e: Exception) {
            Response.error(
                400,
                ResponseBody.create(MediaType.parse("text/plain"), e.toString())
            )
        }
    }
}

