package com.github.omarmiatello.noexp.utils

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

internal val json = Json(
    JsonConfiguration.Stable.copy(
        ignoreUnknownKeys = true,
        prettyPrint = true,
        encodeDefaults = false
    )
)
