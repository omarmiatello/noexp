package com.github.omarmiatello.noexp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

private val json =
    Json(JsonConfiguration.Stable.copy(ignoreUnknownKeys = true, prettyPrint = true, encodeDefaults = false))

@Serializable
data class NoExpDB(
    val archive: Map<String, Map<String, ArchivedDao>>? = null,
    val barcode: Map<String, BarcodeDao>? = null,
    val home: Map<String, ProductDao>? = null,
    val lastQr: String? = null,
    val category: Map<String, CategoryDao>? = null
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

