package com.github.omarmiatello.noexp.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object DateUtils {
    private val dateFormat by lazy { SimpleDateFormat("dd MMMM YYYY", Locale.ITALIAN) }

    fun millisToDays(dateMillis: Long, now: Long = System.currentTimeMillis()) =
        (dateMillis - now) / (1000 * 60 * 60 * 24)

    fun formatRelative(dateMillis: Long, now: Long = System.currentTimeMillis()): Pair<Int, String> {
        val h = ((dateMillis - now) / (1000 * 60 * 60)).toInt().also { if (abs(it) < 36) return it to "h" }
        val d = (h / 24).also { if (abs(it) < 45) return it to "d" }
        val m = (d / 30).also { if (abs(it) < 13) return it to "M" }
        return m / 12 to "y"
    }

    fun formatRelativeShort(dateMillis: Long, now: Long = System.currentTimeMillis()) =
        with(formatRelative(dateMillis, now)) { "$first$second" }

    fun formatDateItalian(dateMillis: Long): String = dateFormat.format(Date(dateMillis))
}