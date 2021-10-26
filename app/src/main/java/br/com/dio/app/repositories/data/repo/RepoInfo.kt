package br.com.dio.app.repositories.data.repo

import br.com.dio.app.repositories.data.model.Repo

/**
 * Essa interface abstrai a busca pelos detalhes de um Repo
 * específico na Github API.
 */
interface RepoInfo {

    suspend fun getRepo(owner: String, repoName: String) : Repo

}