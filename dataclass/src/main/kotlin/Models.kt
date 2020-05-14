package com.github.omarmiatello.noexp

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val description: String? = null,
    val pictureUrl: String? = null,
    val barcode: String? = null,
    val qr: String,
    val insertDate: Long,
    val expireDate: Long,
    val min: Int? = null,
    val desired: Int? = null,
    val max: Int? = null,
    val maxPerWeek: Int? = null,
    val maxPerYear: Int? = null,
    val cat: List<Category> = emptyList(),
    val catParents: List<Category> = emptyList()
) {
    fun expireInDays(now: Long = System.currentTimeMillis()) = expireInDays(now, expireDate)
    fun expireFormatted(now: Long = System.currentTimeMillis()) = expireFormatted(now, expireDate)
}

@Serializable
data class Category(
    val name: String,
    val alias: List<String> = emptyList(),
    val directParent: String? = null,
    val allParents: List<String> = emptyList(),
    val directChildren: List<String> = emptyList(),
    val min: Int? = null,
    val desired: Int? = null,
    val max: Int? = null,
    val maxPerWeek: Int? = null,
    val maxPerYear: Int? = null
) : Comparable<Category> {
    private val sortKey get() = allParents.joinToString("") + name
    override fun compareTo(other: Category): Int = sortKey.compareTo(other.sortKey)
}