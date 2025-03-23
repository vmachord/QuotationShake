package dadm.vamactor.quotationshake.ui.settings
import androidx.preference.PreferenceFragmentCompat
import android.os.Bundle
import dadm.vamactor.quotationshake.R
import dadm.vamactor.quotationshake.data.settings.SettingsPreferenceDataStore
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {
    @Inject
    lateinit var settingsPreferenceDataStore: SettingsPreferenceDataStore

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = settingsPreferenceDataStore
        setPreferencesFromResource(R.xml.preferences_settings, rootKey)
    }

}










