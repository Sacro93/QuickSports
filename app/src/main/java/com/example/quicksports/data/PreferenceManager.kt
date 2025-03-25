package com.example.quicksports.data

import android.content.Context
import androidx.core.content.edit

object PreferenceManager {
    private const val PREF_NAME = "quick_sports_prefs"
    private const val KEY_KEEP_LOGGED_IN = "keep_logged_in"

    fun setKeepLoggedIn(context: Context, value: Boolean) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit() {
                putBoolean(KEY_KEEP_LOGGED_IN, value)
            }
    }

    fun shouldKeepLoggedIn(context: Context): Boolean {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_KEEP_LOGGED_IN, false)
    }
}
