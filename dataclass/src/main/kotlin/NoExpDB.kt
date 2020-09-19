package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.json
import kotlinx.serialization.Serializable

@Serializable
data class NoExpDB(
    val archive: Map<String, Map<String, ArchivedDao>>? = null,
    val barcode: Map<String, BarcodeDao>? = null,
    val home: Map<String, ProductDao>? = null,
    val lastQr: String? = null,
    val category: Map<String, CategoryDao>? = null,
    val expireDateByBarcode: Map<String, Long>? = null,
    val cart: Map<String, ProductCartDao>? = null,
) : NoExpDBModel() {
    override fun toJson() = json.encodeToString(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.decodeFromString(serializer(), string)
    }
}

sealed class NoExpDBModel {
    abstract fun toJson(): String

    @Serializable
    data class ProductDao(
        val name: String? = null,
        val pictureUrl: String? = null,
        val barcode: String? = null,
        val qr: String? = null,
        val insertDate: Long? = null,
        val expireDate: Long? = null,
        val expireDateType: String? = null,
        val lastCheckDate: Long? = null,
        val cat: List<String>? = null,
        val catParents: List<String>? = null,
        val position: String? = null,
    ) : NoExpDBModel() {
        override fun toJson() = json.encodeToString(serializer(), this)

        fun toMap() = mapOf(
            "name" to name,
            "pictureUrl" to pictureUrl,
            "barcode" to barcode,
            "qr" to qr,
            "insertDate" to insertDate,
            "expireDate" to expireDate,
            "lastCheckDate" to lastCheckDate,
            "cat" to cat,
            "catParents" to catParents,
            "position" to position,
        )

        companion object {
            fun fromJson(string: String) = json.decodeFromString(serializer(), string)
        }
    }

    @Serializable
    data class ProductCartDao(
        val name: String? = null,
        val pictureUrl: String? = null,
        val barcode: String? = null,
        val insertDate: Long? = null,
        val expireDate: Long? = null,
        val expireDateType: String? = null,
        val cat: List<String>? = null,
        val catParents: List<String>? = null,
    ) : NoExpDBModel() {
        override fun toJson() = json.encodeToString(serializer(), this)

        fun toMap() = mapOf(
            "name" to name,
            "pictureUrl" to pictureUrl,
            "barcode" to barcode,
            "insertDate" to insertDate,
            "expireDate" to expireDate,
            "cat" to cat,
            "catParents" to catParents,
        )

        companion object {
            fun fromJson(string: String) = json.decodeFromString(serializer(), string)
        }
    }

    @Serializable
    data class BarcodeDao(
        val name: String? = null,
        val pictureUrl: String? = null,
        val barcode: String? = null,
    ) : NoExpDBModel() {
        override fun toJson() = json.encodeToString(serializer(), this)

        companion object {
            fun fromJson(string: String) = json.decodeFromString(serializer(), string)
        }
    }

    @Serializable
    data class ArchivedDao(
        val barcode: String? = null,
        val qr: String? = null,
        val insertDate: Long? = null,
        val expireDate: Long? = null,
        val expireDateType: String? = null,
        val archiveDate: Long? = null,
    ) : NoExpDBModel() {
        override fun toJson() = json.encodeToString(serializer(), this)

        companion object {
            fun fromJson(string: String) = json.decodeFromString(serializer(), string)
        }
    }

    @Serializable
    data class CategoryDao(
        val name: String? = null,
        val alias: List<String>? = null,
        val directParent: String? = null,
        val allParents: List<String>? = null,
        val directChildren: List<String>? = null,
        val min: Int? = null,
        val desired: Int? = null,
        val max: Int? = null,
        val maxPerWeek: Int? = null,
        val maxPerYear: Int? = null,
        val expireDays: Int? = null,
    ) : NoExpDBModel() {
        override fun toJson() = json.encodeToString(serializer(), this)

        fun toMap() = mapOf(
            "name" to name,
            "alias" to alias,
            "directParent" to directParent,
            "allParents" to allParents,
            "directChildren" to directChildren,
            "min" to min,
            "desired" to desired,
            "max" to max,
            "maxPerWeek" to maxPerWeek,
            "maxPerYear" to maxPerYear,
            "expireDays" to expireDays,
        )

        companion object {
            fun fromJson(string: String) = json.decodeFromString(serializer(), string)
        }
    }
}

