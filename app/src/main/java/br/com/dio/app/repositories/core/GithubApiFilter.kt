package br.com.dio.app.repositories.core

enum class GithubApiFilter(private val value: String) {
    SORT_BY_NAME("full_name"),
    SORT_BY_PUSHED("pushed");

    val string = value
}