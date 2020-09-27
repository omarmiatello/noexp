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
sealed class ExpireDate {
    @Serializable
    class Real(val value: Long) : ExpireDate()

    @Serializable
    class Estimated(val value: Long) : ExpireDate()

    @Serializable
    object NoExpire : ExpireDate()

    @Serializable
    object Unknown : ExpireDate()

    val valueIfReal get() = when (this) {
        is ExpireDate.Real -> value
        is ExpireDate.Estimated,
        ExpireDate.NoExpire,
        ExpireDate.Unknown -> null
    }

    val valueIfRealOrEstimate get() = when (this) {
        is ExpireDate.Real -> value
        is ExpireDate.Estimated -> value
        ExpireDate.NoExpire,
        ExpireDate.Unknown -> null
    }
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

sealed class ProductHome : NoExpModel() {
    abstract val name: String
    abstract val pictureUrl: String?
    abstract val barcode: String?
    abstract val insertDate: Long
    abstract val expireDate: ExpireDate
    abstract val cat: List<Category>
    abstract val catParents: List<Category>

    fun expireInDays(now: Long = System.currentTimeMillis()) = expireDate.let {
        when (it) {
            is ExpireDate.Real -> DateUtils.millisToDays(it.value, now)
            is ExpireDate.Estimated -> DateUtils.millisToDays(it.value, now)
            ExpireDate.NoExpire,
            ExpireDate.Unknown -> null
        }
    }

    fun expireFormatted(now: Long = System.currentTimeMillis()) = expireDate.let {
        when (it) {
            is ExpireDate.Real -> DateUtils.formatRelativeShort(it.value, now)
            is ExpireDate.Estimated -> DateUtils.formatRelativeShort(it.value, now)
            ExpireDate.NoExpire,
            ExpireDate.Unknown -> null
        }
    }
}

@Serializable
data class ProductQr(
    override val name: String,
    override val pictureUrl: String? = null,
    override val barcode: String? = null,
    override val insertDate: Long,
    override val expireDate: ExpireDate,
    override val cat: List<Category> = emptyList(),
    override val catParents: List<Category> = emptyList(),
    val qr: String,
    val lastCheckDate: Long,
    val position: String? = null,
) : ProductHome() {
    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}

@Serializable
data class ProductCart(
    override val name: String,
    override val pictureUrl: String? = null,
    override val barcode: String? = null,
    override val insertDate: Long,
    override val expireDate: ExpireDate,
    override val cat: List<Category> = emptyList(),
    override val catParents: List<Category> = emptyList(),
    val id: String? = null,
) : ProductHome() {
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

    fun estimateExpireDate(insertDate: Long) = expireDays?.let { insertDate + it.toLong() * 24 * 60 * 60 * 1000 }

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}
