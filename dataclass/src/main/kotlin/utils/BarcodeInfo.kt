package com.github.omarmiatello.noexp.utils

data class BarcodeInfo(
    val fullCode: String
) {
    val isInternalCode = fullCode.take(2).toInt() in 20..29
    val productCode: String
    val price: Int

    init {
        when {
            isInternalCode && fullCode.substring(6..7) == "00" -> {
                productCode = fullCode.take(6)
                price = fullCode.drop(6).dropLast(1).toInt()
            }
            isInternalCode && fullCode.substring(7..8) == "00" -> {
                productCode = fullCode.take(7)
                price = fullCode.drop(7).dropLast(1).toInt()
            }
            else -> {
                productCode = fullCode
                price = 0
            }
        }
    }
}
