package com.github.omarmiatello.noexp.utils

import kotlin.reflect.full.memberProperties

fun Any?.toConstructorString(maxSample: Int = 5): String = when (this) {
    is String -> "\"${this}\""
    is Number -> toString()
    is List<*> -> "listOf(\n${take(maxSample).joinToString(",\n") { it.toConstructorString() }}\n)"
    is Map<*, *> -> "mapOf(\n${entries.take(maxSample).joinToString(",\n") { "${it.key.toConstructorString()} to ${it.value.toConstructorString()}" }}\n)"
    null -> "null"
    else -> buildString {
        appendLine("${this@toConstructorString::class.simpleName}(")
        this@toConstructorString::class.memberProperties.map {
            appendLine("${it.name} = ${it.call(this@toConstructorString).toConstructorString()},")
        }
        append(")")
    }
}