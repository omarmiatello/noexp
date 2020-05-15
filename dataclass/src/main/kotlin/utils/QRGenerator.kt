package com.github.omarmiatello.noexp.utils

object QRGenerator {
    const val URL_PREFIX = "https://jackl.dev/home/"

    val baseN = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    fun intToBase36(num: Int): String {
        val max = baseN.length
        return if (num < max) {
            "${baseN[num % max]}"
        } else {
            "${intToBase36((num / max) - 1)}${baseN[num % max]}"
        }
    }

    fun base36ToInt(base36: String): Int {
        val max = baseN.length
        var num = 0
        base36.toUpperCase().reversed().forEachIndexed { index, c ->
            num += (baseN.indexOf(c).takeIf { it != -1 }!! + 1) * (Math.pow(
                max.toDouble(),
                index.toDouble()
            ).toInt())
        }
        return num - 1
    }
}

inline fun Int.toBase36() = QRGenerator.intToBase36(this)

inline fun String.base36ToInt() = QRGenerator.base36ToInt(this)

fun String.toQrLastPartOrNull() = takeIf { startsWith(QRGenerator.URL_PREFIX) }?.removePrefix(QRGenerator.URL_PREFIX)