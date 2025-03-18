package dadm.vamactor.quotationshake.data.favourites.model

import domain.model.Quotation

fun DatabaseQuotationDto.toDomain(): Quotation {
    return Quotation(
        id = this.id.toString(),
        text = this.text,
        author = this.author
    )
}
