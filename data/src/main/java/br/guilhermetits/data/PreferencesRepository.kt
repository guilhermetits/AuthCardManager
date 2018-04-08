package br.guilhermetits.data

import android.content.SharedPreferences
import br.guilhermetits.common.fromJson
import com.google.gson.Gson
import br.guilhermetits.common.toJson

class PreferencesRepository(val sharedPreferences: SharedPreferences,
                            val gson: Gson) {

    fun <T : Any> putValue(key: String, value: T) {
        sharedPreferences.edit()
                .putString(key, value.toJson(gson))
                .apply()
    }

    inline fun <reified T : Any> getValue(key: String): T? {
        return sharedPreferences.getString(key, "")
                .takeIf { !it.isEmpty() }
                ?.fromJson(gson)
    }

    inline fun <reified T : Any> getValue(key: String, default: T): T {
        return this.getValue(key) ?: default
    }
}