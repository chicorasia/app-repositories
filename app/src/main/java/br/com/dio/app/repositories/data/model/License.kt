package br.com.dio.app.repositories.data.model

import com.squareup.moshi.Json

/**
 * Essa classe representa um tipo de licença; também é recebido da API e é uma dependência
 * do objeto Repo
 */
data class License (
    val key: String,
    val name: String,
    @Json(name = "spdx_id")
    val spdxID: String,
    val url: String,
    @Json(name = "node_id")
    val nodeID: String
)
