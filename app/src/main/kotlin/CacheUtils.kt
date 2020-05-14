package com.github.omarmiatello.noexp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.lang.reflect.Type

val root = File("data")
val categoriesFile = root.resolve("categories.txt")

private val gson = Gson()

inline fun <reified T : Any> gsonType(): Type = (object : TypeToken<T>() {}).type
fun Any.toGson() = gson.toJson(this)
inline fun <reified T : Any> String.parseGson() = parseGsonType<T>(gsonType<T>())
fun <T : Any> String.parseGsonType(typeOf: Type) = gson.fromJson<T>(this, typeOf)
inline fun <reified T : Any> cache(filename: String, forceUpdate: Boolean = false, block: () -> T) =
    cacheText(filename, forceUpdate) { block().toGson() }.parseGson<T>()

inline fun cacheText(filename: String, forceUpdate: Boolean = false, block: () -> String) = root.resolve(filename)
    .let { file -> if (!forceUpdate && file.exists()) file.readText() else block().also { file.writeText(it) } }
