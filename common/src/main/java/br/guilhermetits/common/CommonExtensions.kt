package br.guilhermetits.common

fun <T> T.takeIfNotNull(vararg objects: Any?): T? {
    return if (objects.any { it == null }) null else this
}