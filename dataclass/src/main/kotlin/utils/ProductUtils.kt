package com.github.omarmiatello.noexp.utils

import com.github.omarmiatello.noexp.ProductHome

fun List<ProductHome>.expiresFormatted(
    formatter: (Pair<String, Int>) -> CharSequence = { (exp, count) -> if (count == 1) exp else "${count}x $exp" }
) = map { it.expireFormatted() ?: "NoExp" }
    .groupBy { it }
    .mapValues { it.value.size }
    .toList()
    .joinToString(transform = formatter)