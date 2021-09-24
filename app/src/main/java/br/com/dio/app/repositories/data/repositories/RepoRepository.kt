package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.data.model.Repo
import kotlinx.coroutines.flow.Flow

/**
 * Essa interface abstrai a implementação de um RepoRepository; essa é uma
 * boa prática SOLID: "dependa de interfaces e não de implementações".
 * Espera dois parâmetros: o nome de usuário e o critério de ordenação
 * dos resultados.
 */
interface RepoRepository {

    suspend fun listRepositories(param: Query) : Flow<List<Repo>>

}


