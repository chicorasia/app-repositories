package br.com.dio.app.repositories.data.repodetails

import br.com.dio.app.repositories.core.Query
import kotlinx.coroutines.flow.Flow


/**
 * Essa interface abstrai a implementação de um RepoDetailsRepository.
 *
 */
interface RepoDetails {

    /**
     * Um método para receber um arquivo README.md como um String.
     * O retorno pode ser nulo caso o repo não tenha um arquivo
     * README.md.
      */
    suspend fun getRepoReadme(param: Query) : String?

}