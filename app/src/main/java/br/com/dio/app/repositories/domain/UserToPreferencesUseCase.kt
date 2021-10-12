package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.util.PreferencesUtils

/**
 * Esse arquivo concentra os UseCases relacionados às operações
 * de persistência de usuário nas Preferences
 */


/**
 * Esse use case é responsável por salvar um usuário
 * nas Preferences.
 */
class SaveUserToPreferencesUseCase(
    private val preferencesUtils: PreferencesUtils
) {

    suspend operator fun invoke(user: User) {
        saveUser(user)
    }

    suspend fun saveUser(user: User) {
        preferencesUtils.saveUser(user)
    }

}


/**
 * Esse use case é responsável por carregar um usuário
 * do arquivo de preferências. Usei um operator para
 * simplificar a sintaxe.
 */
class LoadUserFromPreferencesUseCase(
    private val preferencesUtils: PreferencesUtils
) {

    operator fun invoke() : User?{
        return loadUser()
    }

    private fun loadUser() : User? {
        return preferencesUtils.loadUser()
    }

}

/**
 * Limpa o usuário gravado nas Preferences
 */
class ClearUserFromPreferencesUseCase(
    private val preferencesUtils: PreferencesUtils
) {
    operator fun invoke() {
        clearUser()
    }

    private fun clearUser() {
        preferencesUtils.clearUser()
    }
}

