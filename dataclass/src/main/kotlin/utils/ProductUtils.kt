package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.Product

fun List<Product>.expiresFormatted(
    formatter: (Pair<String, Int>) -> CharSequence = { if (it.second == 1) it.first else "${it.second}x ${it.first}" }
) = map { it.expireFormatted() }
    .groupBy { it }
    .map { it.key to it.value.size }
    .joinToString(transform = formatter)