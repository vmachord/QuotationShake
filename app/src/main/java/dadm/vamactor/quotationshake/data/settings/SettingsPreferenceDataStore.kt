package dadm.vamactor.quotationshake.data.settings

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first

import javax.inject.Inject

class SettingsPreferenceDataStore @Inject constructor(private val settingsRepository: SettingsRepository): PreferenceDataStore() {
    private val USER_NAME_KEY = stringPreferencesKey("username")
    private val LANGUAGE_KEY = stringPreferencesKey("language_preference")

    override fun getString(key: String?, defValue: String?): String? {
        return runBlocking(Dispatchers.IO) {
            when (key) {
                USER_NAME_KEY.name -> settingsRepository.getUserName().first()
                LANGUAGE_KEY.name -> settingsRepository.getLanguage().first()
                else -> defValue
            }
        }
    }

    override fun putString(key: String?, value: String?) {
        value?.let {
            CoroutineScope(Dispatchers.IO).launch {
                when (key) {
                    USER_NAME_KEY.name -> settingsRepository.setUserName(it)
                    LANGUAGE_KEY.name -> settingsRepository.setLanguage(it)
                    else -> {}
                }
            }
        }
    }
}