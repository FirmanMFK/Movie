package com.firman.movie.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey("is_dark_theme")

    val isDarkThemeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_KEY] ?: true
        }

    suspend fun toggleTheme() {
        context.dataStore.edit { preferences ->
            val current = preferences[THEME_KEY] ?: true
            preferences[THEME_KEY] = !current
        }
    }
}
