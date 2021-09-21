package br.com.dio.app.repositories.presentation.di

import br.com.dio.app.repositories.presentation.ui.home.HomeViewModel
import br.com.dio.app.repositories.presentation.ui.home.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Esse object é responsável pela instanciação dos ViewModels. Segue
 * o padrão estabelecido nos outros arquivos de modules.
 */
object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule() : Module {
        return module {
            viewModel { HomeViewModel(get()) }
            viewModel { LoginViewModel(get()) }
        }
    }
}