package br.com.dio.app.repositories.data.model

import com.squareup.moshi.Json


/**
 * Essa data class representa um Repo recebido da API. Usei a anotação @Json para
 * mapear corretamente alguns parâmetros e manter a padronização camelCase
 * indicada para o Kotlin. Os atributos License e Description precisam ser nuláveis para
 * acomodar valores nulos retornados da API.
 */
data class Repo (
    val id: Long,
    val name: String,
    val owner: Owner,
    @Json(name = "html_url")
    val htmlURL: String,
    val description: String?,
    @Json(name = "stargazers_count")
    val stargazersCount: Long,
    val language: String?,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "pushed_at")
    val pushedAt: String,
    val license: License?,
    @Json(name = "default_branch")
    val defaultBranch: String
)