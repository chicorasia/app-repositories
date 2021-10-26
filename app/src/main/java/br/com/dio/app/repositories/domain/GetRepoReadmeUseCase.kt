package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.repodetails.RepoDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRepoReadmeUseCase(private val repoDetails: RepoDetails): UseCase<Query, String?>() {

    override suspend fun execute(param: Query): Flow<String?> {
        return flow {
            emit(repoDetails.getRepoReadme(param))
        }
    }

}