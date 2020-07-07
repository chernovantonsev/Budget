package ru.antonc.budget.ui.menu.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import ru.antonc.budget.R
import ru.antonc.budget.ThemeHelper

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val themePreference: ListPreference? = findPreference("themePref")
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val themeOption: String = newValue as String
                ThemeHelper.applyTheme(themeOption)
                true
            }
    }

}