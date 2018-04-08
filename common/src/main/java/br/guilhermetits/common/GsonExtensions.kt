package br.guilhermetits.common

import com.google.gson.Gson
import java.lang.reflect.Type

fun <T : Any> T.toJson(gson: Gson): String {
    return gson.toJson(this)
}

inline fun <reified T : Any> String.fromJson(gson: Gson): T {
    return gson.fromJson(this, T::class.java)
}