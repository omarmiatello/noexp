package com.github.omarmiatello.noexp.utils

import kotlin.reflect.full.memberProperties

fun Any?.toConstructorString(maxSample: Int, excludedField: String? = null): String = when (this) {
    is String -> "\"${this}\""
    is Number -> toString()
    is List<*> -> "listOf(\n${take(maxSample).joinToString(",\n") { it.toConstructorString(maxSample) }}\n)"
    is Map<*, *> -> "mapOf(\n${
        entries.take(maxSample)
            .joinToString(",\n") { "${it.key.toConstructorString(maxSample)} to ${it.value.toConstructorString(maxSample)}" }
    }\n)"
    null -> "null"
    else -> buildString {
        appendLine("${this@toConstructorString::class.simpleName}(")
        this@toConstructorString::class.memberProperties.map {
            appendLine(
                "${it.name} = ${
                    it.call(this@toConstructorString)
                        .toConstructorString(if (it.name == excludedField) 1000 else maxSample)
                },"
            )
        }
        append(")")
    }
}