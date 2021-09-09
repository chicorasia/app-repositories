package br.com.dio.app.repositories.data.model

import com.squareup.moshi.Json

/**
 * Essa classe representa um dono do Repo e é recebida da API. É uma dependência da
 * classe Repo.
 */
data class Owner (
    val login: String,
    @Json(name = "avatar_url")
    val avatarURL: String
)
