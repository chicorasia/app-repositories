package br.com.dio.app.repositories.util

import android.content.Context
import br.com.dio.app.repositories.data.model.User


/**
 * Essa classe facilita o acesso Às SharedPreferences. A solução ainda é pouco elegante,
 * gravando os três parâmetros do User como valores separados nas SharedPreferences.
 */
class PreferencesUtils(val context: Context) {

    val prefsGithubUserName = context.getSharedPreferences(GITHUB_USERNAME, Context.MODE_PRIVATE)
    val prefsGithubAvatarUrl = context.getSharedPreferences(GITHUB_AVATAR_URL, Context.MODE_PRIVATE)
    val prefsGithubRepoCount =
        context.getSharedPreferences(GITHUB_USER_REPOCOUNT, Context.MODE_PRIVATE)

    companion object {

        const val GITHUB_USERNAME = "br.com.dio.app.repositories.GITHUB_USER"
        const val GITHUB_AVATAR_URL = "br.com.dio.app.repositories.GITHUB_AVATAR_URL"
        const val GITHUB_USER_REPOCOUNT = "br.com.dio.app.repositories.GITHUB_USER_REPOCOUNT"

    }

    /**
     * Instancia um User à partir dos dados primitivos gravados nas preferences
     */
    fun loadUser(): User? {
        val login = prefsGithubUserName.getString(GITHUB_USERNAME, null)
        val avatarUrl = prefsGithubAvatarUrl.getString(GITHUB_AVATAR_URL, null)
        val repoCount = prefsGithubRepoCount.getInt(GITHUB_USER_REPOCOUNT, 0)

        return if (login != null && avatarUrl != null) {
            User(login, avatarUrl, repoCount)
        } else {
            null
        }

    }

    /**
     * Salva um user recebido como parâmetro
     */
    fun saveUser(user: User) {
        with(prefsGithubUserName.edit()) {
            putString(GITHUB_USERNAME, user.login)
            commit()
        }
        with(prefsGithubAvatarUrl.edit()) {
            putString(GITHUB_AVATAR_URL, user.avatarUrl)
            commit()
        }
        with(prefsGithubRepoCount.edit()) {
            putInt(GITHUB_USER_REPOCOUNT, user.publicRepos)
            commit()
        }
    }

    /**
     * Grava um usuário nulo nas SharedPreferences. É necessário porque um valor
     * não-nulo dispara a navegação para a tela principal.
     */
    fun clearUser() {
        with(prefsGithubUserName.edit()) {
            putString(GITHUB_USERNAME, null)
            commit()
        }
        with(prefsGithubAvatarUrl.edit()) {
            putString(GITHUB_AVATAR_URL, null)
            commit()
        }
        with(prefsGithubRepoCount.edit()) {
            putInt(GITHUB_USER_REPOCOUNT, 0)
            commit()
        }
    }

}