package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RepoQuery
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.services.GithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Essa interface abstrai a implementação de um RepoRepository; essa é uma
 * boa prática SOLID: "dependa de interfaces e não de implementações".
 * Espera dois parâmetros: o nome de usuário e o critério de ordenação
 * dos resultados.
 */
interface RepoRepository {

    suspend fun listRepositories(param: RepoQuery) : Flow<List<Repo>>

}


