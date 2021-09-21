package br.com.dio.app.repositories.data.user

import br.com.dio.app.repositories.data.model.User

/**
 * Essa interface abstrai a implementação de UserInfo
 */
interface UserInfo {

    suspend fun getUserInfo(user: String) : User
}