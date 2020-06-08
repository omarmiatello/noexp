package com.github.omarmiatello.noexp

import com.github.omarmiatello.noexp.utils.cacheText
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object ExternalData {
    private var client = OkHttpClient()

    private fun urlGet(url: String) =
        client.newCall(Request.Builder().url(url).build())
            .execute()
            .use { response -> response.body!!.string() }

    private fun urlPut(url: String, json: String) =
        client.newCall(Request.Builder().url(url).put(json.toRequestBody()).build())
            .execute()
            .use { response -> response.body!!.string() }

    fun getNoExp(url: String, forceUpdate: Boolean) =
        cacheText("noexp-db.json", forceUpdate) { urlGet(url) }
            .let { NoExpDB.fromJson(it) }

    fun setNoExp(url: String, json: String) =
        urlPut(url, json).let { NoExpDB.fromJson(it) }
}