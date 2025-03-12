package dadm.vamactor.quotationshake.di

import dadm.vamactor.quotationshake.data.newquotation.NewQuotationDataSource
import dadm.vamactor.quotationshake.data.newquotation.NewQuotationDataSourceImpl
import dadm.vamactor.quotationshake.data.newquotation.NewQuotationRepository
import dadm.vamactor.quotationshake.data.newquotation.NewQuotationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NewQuotationBinderModule {
    @Binds
    abstract fun bindNewQuotationRepository(impl: NewQuotationRepositoryImpl): NewQuotationRepository
    @Binds
    abstract fun bindNewQuotationDataSource(impl: NewQuotationDataSourceImpl): NewQuotationDataSource
}