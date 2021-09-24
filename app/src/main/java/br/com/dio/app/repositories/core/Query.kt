package br.com.dio.app.repositories.core

/**
 * Essa data class encapsula as chamadas à Api. O endpoint
 * @GET("users/{user}/repos?sort=sorting")
 * depende de dois parâmetros (user e sorting).
 * O valor padrão de sorting é null e ele pode ser facilmente
 * sobrescrito ao se instanciar a Query.
 * Isso permite abstrair o parâmetro do UseCase e evita
 * a obscuridade e o acoplamento decorrentes de  de se lidar
 * com arrays de strings.
 */
data class Query(
    val user: String,
    val sorting: String? = null
)

