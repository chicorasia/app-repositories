package br.com.dio.app.repositories.core

/**
 * Essa data class encapsula as chamadas à Api.
 *
 * O endpoint
 * @GET("users/{user}/repos?sort=sorting")
 * depende de dois parâmetros (user e sorting).
 *
 * O endpoint
 * @GET("repos/{owner}/{repo}")
 * depende de dois parâmetros, owner (que é o mesmo que user...) e repo.
 *
 * TODO: explicar quais endpoints usam as outras opções. Talvez eu devesse
 * mudar esse parâmetro para um Map...
 *
 * O valor padrão de option é null e ele pode ser facilmente
 * sobrescrito ao se instanciar a Query.
 * Isso permite abstrair o parâmetro do UseCase e evita
 * a obscuridade e o acoplamento decorrentes de  de se lidar
 * com arrays de strings.
 */
data class Query(
    val user: String,
    val option1: String? = null,
    val option2: String? = null
)

