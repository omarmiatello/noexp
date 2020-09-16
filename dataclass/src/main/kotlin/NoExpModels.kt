package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.DateUtils
import com.github.omarmiatello.noexp.utils.json
import kotlinx.serialization.Serializable
import kotlin.math.max
import kotlin.math.roundToInt

sealed class NoExpModel {
    abstract fun toJson(): String
}

@Serializable
data class Quantity(
    val min: Int? = null,
    val desired: Int? = null,
    val max: Int? = null,
    val maxPerWeek: Int? = null,
    val maxPerYear: Int? = null,
) : NoExpModel() {
    val maxPerDay: Double? = max((maxPerWeek ?: 0) / 7.0, (maxPerYear ?: 0) / 365.0).takeIf { it != 0.0 }
    val minDaysForConsume = maxPerDay?.let { (1 / it).roundToInt() }

    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}

@Serializable
data class Product(
    val name: String,
    val pictureUrl: String? = null,
    val barcode: String? = null,
    val qr: String,
    val insertDate: Long,
    val expireDate: Long,
    val lastCheckDate: Long,
    val cat: List<Category> = emptyList(),
    val catParents: List<Category> = emptyList(),
    val position: String? = null,
) : NoExpModel() {
    fun expireInDays(now: Long = System.currentTimeMillis()) = DateUtils.millisToDays(expireDate, now)

    fun expireFormatted(now: Long = System.currentTimeMillis()) = DateUtils.formatRelativeShort(expireDate, now)

    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}

@Serializable
data class Category(
    val name: String,
    val alias: List<String> = emptyList(),
    val directParent: String? = null,
    val allParents: List<String> = emptyList(),
    val directChildren: List<String> = emptyList(),
    val quantity: Quantity? = null,
    val expireDays: Int? = null,
) : NoExpModel(), Comparable<Category> {
    private val sortKey get() = allParents.joinToString("") + name

    override fun compareTo(other: Category): Int = sortKey.compareTo(other.sortKey)

    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}

@Serializable
data class ProductCart(
    val name: String,
    val pictureUrl: String? = null,
    val barcode: String? = null,
    val insertDate: Long,
    val expireDate: Long,
    val cat: List<Category> = emptyList(),
    val catParents: List<Category> = emptyList(),
) : NoExpModel() {
    fun expireInDays(now: Long = System.currentTimeMillis()) = DateUtils.millisToDays(expireDate, now)

    fun expireFormatted(now: Long = System.currentTimeMillis()) = DateUtils.formatRelativeShort(expireDate, now)

    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}