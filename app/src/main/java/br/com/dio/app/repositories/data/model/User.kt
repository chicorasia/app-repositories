package br.com.dio.app.repositories.data.model

import com.squareup.moshi.Json

data class User(
    val login: String,
    @Json(name = "avatar_url")
    val avatar_url: String,
    @Json(name = "public_repos")
    val publicRepos: Int
)
