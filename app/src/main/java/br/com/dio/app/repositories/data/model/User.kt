package br.com.dio.app.repositories.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class User(
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String,
    @Json(name = "public_repos")
    val publicRepos: Int
)  {
    val repoCount = publicRepos.toString()
}
