package br.com.dio.app.repositories.data.repodetails

import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.data.services.GithubServiceRaw
import retrofit2.HttpException

/**
 * Essa classe implementa a interface RepoDetaisRepository. Segue o padrão
 * empregado nos outros repositórios.
 */
class RepoDetailsImpl(private val service: GithubServiceRaw) : RepoDetails {

    /**
     * Implementação o método obrigatório; é usado o contrutor flow { } para
     * converter e emitir o String recebido da na forma de um fluxo.
     * Recebe um objeto Query, que traz o nome de usuário, o nome do
     * Repo e o branch default.
     */
    override suspend fun getRepoReadme(param: Query): String? {
        try {
            return service.getRepoReadme(
                owner = param.user,
                repo = param.option1.toString(),
                defaultBranch = param.option2.toString()
            )
        } catch (ex: HttpException) {
            throw RemoteException("Não foi possível acessar o conteúdo raw!")
        }
    }
}