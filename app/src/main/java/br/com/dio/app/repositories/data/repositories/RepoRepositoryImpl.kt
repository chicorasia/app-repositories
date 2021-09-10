package br.com.dio.app.repositories.data.repositories

import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.services.GithubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

/**
 * Essa classe implementa a interface RepoRepository, recebendo os services
 * necessários (no caso, somente o GithubService). Os dados são retornados
 * na forma de fluxo (Flow), como espera a interface.
 */
class RepoRepositoryImpl(private val service: GithubService) : RepoRepository {

    /**
     * Implementação o método obrigatório; é usado o contrutor flow { } para
     * converter e emitir a lista recebida da na forma de um fluxo.
     */
    override suspend fun listRepositories(user: String): Flow<List<Repo>> = flow {
        try {
            val repoList = service.listRepositories(user)
            emit(repoList)
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possível acessar a API web!")
        }
    }

}