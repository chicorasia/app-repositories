package br.com.dio.app.repositories.data.repo

import android.os.RemoteException
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.services.GithubService
import retrofit2.HttpException


/**
 * Essa classe implementa a interface RepoInfo, recebendo um único Repo
 * da Github API ou lançando uma exceção caso ocorra um erro.
 */
class RepoInfoImpl(private val githubService: GithubService) : RepoInfo {

    /**
     * Implementação do método obrigatório
     */
    override suspend fun getRepo(owner: String, repoName: String): Repo {
        try {
            return githubService.getRepo(owner, repoName)
        } catch (ex: HttpException) {
            throw RemoteException("O repositório $repoName não foi encontrado!")
        }
    }


}