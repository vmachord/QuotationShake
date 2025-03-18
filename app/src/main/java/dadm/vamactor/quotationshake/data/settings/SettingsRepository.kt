package dadm.vamactor.quotationshake.data.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getUserName(): Flow<String>;
    suspend fun  getUserNameSnapshot(): String;
    suspend fun  setUserName(userName: String);
    fun getLanguage(): Flow<String>;
    suspend fun  getLanguageSnapshot(): String;
    suspend fun  setLanguage(language: String);
}

