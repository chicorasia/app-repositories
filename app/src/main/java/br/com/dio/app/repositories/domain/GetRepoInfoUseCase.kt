package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repo.RepoInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Esse UseCase faz consultar ao endpoint de detalhes do Repo.
 * A query precisa ter dois parâmetros, obrigatoriamente:
 * user e repoName como option.
 */

class GetRepoInfoUseCase(private val repoInfo: RepoInfo) : UseCase<Query, Repo?>() {

    override suspend fun execute(param: Query): Flow<Repo?> {
        return flow {
            param.option1?.let {
                emit(repoInfo.getRepo(
                    owner = param.user,
                    repoName = param.option1
                ))
            }
        }
    }
}