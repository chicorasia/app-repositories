package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.data.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserUseCase(private val userInfo: UserInfo) : UseCase<String, User>() {

    override suspend fun execute(param: String): Flow<User> {
        return flow {
            emit(userInfo.getUserInfo(param))
        }
    }

}