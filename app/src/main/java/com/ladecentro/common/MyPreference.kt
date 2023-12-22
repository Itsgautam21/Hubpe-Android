package com.ladecentro.common

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyPreference @Inject constructor(@ApplicationContext context: Context) {

    private val preference =
        context.getSharedPreferences(Constants.PREFERENCE_TAG, Context.MODE_PRIVATE)

    fun getStoresTag(key: String): String? {
        return preference.getString(key, null)
    }

    fun setStoredTag(key: String, query: String) {
        preference.edit().putString(key, query).apply()
    }

    fun removeStoredTag(key: String) {
        preference.edit().remove(key).apply()
    }

    fun removeAllTags() {
        preference.edit().clear().apply()
    }
}