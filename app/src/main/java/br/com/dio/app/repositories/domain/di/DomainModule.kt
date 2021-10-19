package br.com.dio.app.repositories.domain.di

import br.com.dio.app.repositories.domain.GetRepoInfoUseCase
import br.com.dio.app.repositories.domain.GetUserUseCase
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Essa classe dá apoio ao Koin para a resolução das injeções de dependências
 * necessárias aos use cases, mantendo a filosofia de organização dos módulos
 * conforme as camadas da aplicação.
 * Diferente do NetworkModule, aqui é utilizada a função factory { } porque
 * queremos ter uma nova instância do use case a cada vez que ele é chamado.
 */
object DomainModule {

    /**
     * Encapsulando o carregamento dos modules. Essa função é pública
     * e invocada no App.kt
     */
    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule() : Module {
        return module {
            factory { ListUserRepositoriesUseCase(get()) }
            factory { GetUserUseCase(get()) }
            factory { GetRepoInfoUseCase(get()) }
        }
    }

}