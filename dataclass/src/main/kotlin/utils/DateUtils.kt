package com.github.omarmiatello.noexp.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object DateUtils {
    private val dateFormat by lazy { SimpleDateFormat("dd MMMM YYYY", Locale.ITALIAN) }

    fun millisToDays(dateMillis: Long, now: Long = System.currentTimeMillis()) = (dateMillis - now) / (1000 * 60 * 60 * 24)

    fun formatRelativeShort(dateMillis: Long, now: Long = System.currentTimeMillis()): String {
        val h = ((dateMillis - now) / (1000 * 60 * 60)).also { if (abs(it) < 36) return "${it}h" }
        val d = (h / 24).also { if (abs(it) < 45) return "${it}d" }
        val m = (d / 30).also { if (abs(it) < 13) return "${it}M" }
        return "${m / 12}y"
    }

    fun formatDateItalian(dateMillis: Long): String = dateFormat.format(Date(dateMillis))
}