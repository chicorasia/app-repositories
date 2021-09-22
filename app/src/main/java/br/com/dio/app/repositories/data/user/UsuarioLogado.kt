package br.com.dio.app.repositories.data.user

import br.com.dio.app.repositories.data.model.User

/**
 * Esse singleton mantém os dados do usuario logado, i.e., a conta do github.
 *
 */
object UsuarioLogado {

    var usuarioLogado: User? = null
    var previousUser: User? = null
    val username = usuarioLogado?.login

}