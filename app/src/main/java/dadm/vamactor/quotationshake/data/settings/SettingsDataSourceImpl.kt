package dadm.vamactor.quotationshake.data.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dadm.vamactor.quotationshake.data.settings.SettingsDataSourceImpl.Keys.LANGUAGE
import dadm.vamactor.quotationshake.data.settings.SettingsDataSourceImpl.Keys.USER_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsDataSourceImpl @Inject constructor(private val dataStore: DataStore<Preferences>): SettingsDataSource {
    object Keys {
        val USER_NAME = stringPreferencesKey("username")
        val LANGUAGE = stringPreferencesKey("language_preference")
    }

    override fun getUserName(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[Keys.USER_NAME].orEmpty()
            }
    }

    override suspend fun getUserNameSnapshot(): String {
        return dataStore.data.first()[USER_NAME] ?: ""
    }


    override suspend fun setUserName(userName: String) {
        dataStore.edit { preferences: MutablePreferences ->
            preferences[USER_NAME] = userName
        }
    }

    override fun getLanguage(): Flow<String>{
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[LANGUAGE] ?: "en"
            }
    }

    override suspend fun  getLanguageSnapshot(): String{
        return dataStore.data.first()[LANGUAGE] ?: "en"
    }
    override suspend fun  setLanguage(language: String){
        dataStore.edit { preferences: MutablePreferences ->
            preferences[LANGUAGE] = language
        }
    }
}








