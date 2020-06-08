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
    val expireDateByCategory: Map<String, Int>? = null,
    val expireDateByBarcode: Map<String, Int>? = null
) : NoExpDBModel() {
    override fun toJson() = json.stringify(serializer(), this)

    companion object {
        fun fromJson(string: String) = json.parse(serializer(), string)
    }
}

sealed class NoExpDBModel {
    abstract fun toJson(): String

    @Serializable
    data class ProductDao(
        val name: String? = null,
        val description: String? = null,
        val pictureUrl: String? = null,
        val barcode: String? = null,
        val qr: String? = null,
        val insertDate: Long? = null,
        val expireDate: Long? = null,
        val min: Int? = null,
        val desired: Int? = null,
        val max: Int? = null,
        val maxPerWeek: Int? = null,
        val maxPerYear: Int? = null,
        val cat: List<String>? = null,
        val catParents: List<String>? = null
    ) : NoExpDBModel() {
        override fun toJson() = json.stringify(serializer(), this)

        fun toMap() = mapOf(
            "name" to name,
            "description" to description,
            "pictureUrl" to pictureUrl,
            "barcode" to barcode,
            "qr" to qr,
            "insertDate" to insertDate,
            "expireDate" to expireDate,
            "min" to min,
            "desired" to desired,
            "max" to max,
            "maxPerWeek" to maxPerWeek,
            "maxPerYear" to maxPerYear,
            "cat" to cat,
            "catParents" to catParents
        )

        companion object {
            fun fromJson(string: kotlin.String) = json.parse(serializer(), string)
        }
    }

    @Serializable
    data class BarcodeDao(
        val name: String? = null,
        val description: String? = null,
        val pictureUrl: String? = null,
        val barcode: String? = null,
        val min: Int? = null,
        val desired: Int? = null,
        val max: Int? = null,
        val maxPerWeek: Int? = null,
        val maxPerYear: Int? = null,
        val cat: List<String>? = null,
        val catParents: List<String>? = null
    ) : NoExpDBModel() {
        override fun toJson() = json.stringify(serializer(), this)

        companion object {
            fun fromJson(string: String) = json.parse(serializer(), string)
        }
    }

    @Serializable
    data class ArchivedDao(
        val barcode: String? = null,
        val qr: String? = null,
        val insertDate: Long? = null,
        val expireDate: Long? = null,
        val archiveDate: Long? = null
    ) : NoExpDBModel() {
        override fun toJson() = json.stringify(serializer(), this)

        companion object {
            fun fromJson(string: String) = json.parse(serializer(), string)
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
        val maxPerYear: Int? = null
    ) : NoExpDBModel() {
        override fun toJson() = json.stringify(serializer(), this)

        companion object {
            fun fromJson(string: String) = json.parse(serializer(), string)
        }
    }
}

