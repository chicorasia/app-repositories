package br.com.dio.app.repositories.core

/**
 * Essa enum mantém strings para ordenação das
 * buscas na API conforme padrão esperado pelo serviço
 */

enum class GithubApiFilter(private val value: String) {
    SORT_BY_NAME("full_name"),
    SORT_BY_PUSHED("pushed");

    val sortby = value
}