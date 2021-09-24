package br.com.dio.app.repositories.domain

import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.UseCase
import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.data.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Esse UseCase permite consultas ao endpoint de informações
 * do usuário. Recebe um string (username) como e retorna um
 * uso como Flow.
 */
class GetUserUseCase(private val userInfo: UserInfo) : UseCase<Query, User>() {

    /**
     * Usa a função flow { } para emitir o User como um fluxo.
     */
    override suspend fun execute(param: Query): Flow<User> {
        return flow {
            emit(userInfo.getUserInfo(param.user))
        }
    }


}