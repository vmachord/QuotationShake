package dadm.vamactor.quotationshake.di;

import dadm.vamactor.quotationshake.data.settings.SettingsDataSource;
import dadm.vamactor.quotationshake.data.settings.SettingsDataSourceImpl;
import dadm.vamactor.quotationshake.data.settings.SettingsRepository;
import dadm.vamactor.quotationshake.data.settings.SettingsRepositoryImpl;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsBinderModule {
    @Binds
    abstract fun bindSettingsDataSource(impl:SettingsDataSourceImpl): SettingsDataSource

    @Binds
    abstract fun bindSettingsRepository(impl:SettingsRepositoryImpl): SettingsRepository
}