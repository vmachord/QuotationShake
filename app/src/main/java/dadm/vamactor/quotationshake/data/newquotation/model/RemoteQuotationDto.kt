package dadm.vamactor.quotationshake.data.newquotation.model

import com.squareup.moshi.JsonClass
import domain.model.Quotation
import retrofit2.Response
import java.io.IOException

fun RemoteQuotationDto.toDomain() = Quotation(
    id = quoteLink,
    text = quoteText,
    author = quoteAuthor
)

fun Response<RemoteQuotationDto>.toDomain() =
    if (isSuccessful) Result.success((body() as RemoteQuotationDto).toDomain())
    else Result.failure(IOException())

@JsonClass(generateAdapter = true)
data class RemoteQuotationDto(
    val quoteText: String,
    val quoteAuthor: String,
    val senderName: String,
    val senderLink: String,
    val quoteLink: String
)