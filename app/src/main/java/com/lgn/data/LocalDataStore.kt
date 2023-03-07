package com.sinq.sinqadmin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.sinq.sinqadmin.core.Constants.PREFERENCES_ADMIN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataStore(private val context: Context) {
    companion object {
        private val Context.prefsDataStore: DataStore<Preferences>  by preferencesDataStore(
            name = PREFERENCES_ADMIN
        )
    }

    suspend fun saveStringValue(key:String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.prefsDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    suspend fun saveBooleanValue(key:String, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key)
        context.prefsDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }


    suspend fun clearAllPreferences() {
        context.prefsDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun saveIntValue(key:String, value: Int) {
        val dataStoreKey = intPreferencesKey(key)
        context.prefsDataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun getStringValue(key: String) : Flow<String?> {
        val dataStoreKey = stringPreferencesKey(key)
        return context.prefsDataStore.data
            .map { prefsDataStore ->
                prefsDataStore[dataStoreKey] ?: "-"
            }
    }

    fun getBooleanValue(key: String) : Flow<Boolean?> {
        val dataStoreKey = booleanPreferencesKey(key)
        return context.prefsDataStore.data
            .map { prefsDataStore ->
                prefsDataStore[dataStoreKey] ?: false
            }
    }

    fun getIntValue(key: String) : Flow<Int?> {
        val dataStoreKey = intPreferencesKey(key)
        return context.prefsDataStore.data
            .map { prefsDataStore ->
                prefsDataStore[dataStoreKey] ?: 0
            }
    }
}