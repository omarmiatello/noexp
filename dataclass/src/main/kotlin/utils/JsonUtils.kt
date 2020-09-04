package com.github.omarmiatello.noexp.utils

import kotlinx.serialization.json.Json

internal val json = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    encodeDefaults = false
}
