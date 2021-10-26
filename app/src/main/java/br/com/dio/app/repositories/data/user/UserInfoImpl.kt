package br.com.dio.app.repositories.data.user

import android.os.RemoteException
import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.data.services.GithubService
import retrofit2.HttpException

/**
 * Essa classe implementa a interface UserInfo, recebendo os services
 * necessários (no caso, somente o GithubService). Os dados são retornados
 * na forma de um objeto User ou lança uma exceção se o usuário não for
 * encontrado.
 */
class UserInfoImpl(private val githubService: GithubService) : UserInfo {

    /**
     * Implementação do método obrigatório.
     */
    override suspend fun getUserInfo(user: String): User {
        try {
            return githubService.getUser(user)
        } catch (ex: HttpException) {
            throw RemoteException("O usuário $user não foi encontrado!")
        }
    }
}