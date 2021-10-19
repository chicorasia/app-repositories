package br.com.dio.app.repositories.presentation.di

import br.com.dio.app.repositories.domain.ClearUserFromPreferencesUseCase
import br.com.dio.app.repositories.domain.LoadUserFromPreferencesUseCase
import br.com.dio.app.repositories.domain.SaveUserToPreferencesUseCase
import br.com.dio.app.repositories.presentation.ui.detail.DetailViewModel
import br.com.dio.app.repositories.util.PreferencesUtils
import br.com.dio.app.repositories.presentation.ui.home.HomeViewModel
import br.com.dio.app.repositories.presentation.ui.user.UserViewModel
import org.koin.android.ext.koin.androidApplication
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
        loadKoinModules(viewModelModule() + preferencesModule() + preferencesUseCase())
    }

    private fun viewModelModule() : Module {
        return module {
            viewModel { HomeViewModel(get(), get()) }
            viewModel { UserViewModel(get(), get(), get()) }
            viewModel { DetailViewModel() }
        }
    }

    private fun preferencesModule() : Module {
        return module {
            factory<PreferencesUtils> { PreferencesUtils(androidApplication().applicationContext) }
        }
    }

    /**
     * Lida com os usecases relacionados à gravação de usuário nas SharedPreferences
     */
    private fun preferencesUseCase() : Module {
        return module {
            factory<SaveUserToPreferencesUseCase> { SaveUserToPreferencesUseCase(get()) }
            factory<LoadUserFromPreferencesUseCase> { LoadUserFromPreferencesUseCase(get()) }
            factory<ClearUserFromPreferencesUseCase> { ClearUserFromPreferencesUseCase(get()) }
        }
    }
}