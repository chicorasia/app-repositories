package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

/**
 * Esse use case tem a função de buscar a lista de Repos no
 * repositório. Repare que ele depende da interface RepoRepository,
 * e não da classe concreta RepoRepositoryImpl.
 * Ele estende a classe UseCase, passando (nos generics) o parâmetro (param)
 * e o resultado esperado (source).
 * As dependências dos use cases são resolvidas no DomainModule.
 * Cada use case deve idealmente ter uma única função e
 * ser facilmente testável.
 */
class ListUserRepositoriesUseCase(private val repository: RepoRepository)  : UseCase<String, List<Repo>>(){

    /**
     * Espera um nome de usuario como String e retorna um Flow
     * de dados à partir do repository
     */
    override suspend fun execute(param: String): Flow<List<Repo>> =
        repository.listRepositories(param)

}
