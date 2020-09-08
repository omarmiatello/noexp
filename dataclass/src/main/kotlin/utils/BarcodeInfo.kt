package com.github.omarmiatello.noexp.utils

data class BarcodeInfo(
    val fullCode: String
) {
    val isCustomCode20 = fullCode.startsWith("20")
    val isCustomCode get() = isCustomCode20
    val isSpecialFormat = isCustomCode20 && fullCode.substring(6..7) == "00"

    val productCode = if (isSpecialFormat) fullCode.take(6) else fullCode
    val price = if (isSpecialFormat) fullCode.drop(6).dropLast(1).toInt() else 0
}
