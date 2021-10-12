package br.com.dio.app.repositories.presentation.ui.user

import androidx.lifecycle.*
import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.domain.GetUserUseCase
import br.com.dio.app.repositories.domain.LoadUserFromPreferencesUseCase
import br.com.dio.app.repositories.domain.SaveUserToPreferencesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Esse ViewModel dá suporte ao fragmento de login de usuário
 */
class UserViewModel(private val userUseCase: GetUserUseCase,
                    private val saveUserToPreferencesUseCase: SaveUserToPreferencesUseCase,
                    private val loadUserFromPreferencesUseCase: LoadUserFromPreferencesUseCase
) : ViewModel() {

    /**
     * Esse campo dispara a navegação para o HomeFragment
     */
    private val _navegaParaHome = MutableLiveData<Boolean>(false)
    val navegaParaHome: LiveData<Boolean>
        get() = _navegaParaHome

    /**
     * Tenta carregar um usuário à partir das SharedPreferences;
     * Se o usuário não for nulo, dispara navegação para a tela
     * de home. Se for nulo, fica na tela de consulta.
     */
    init {
        val user: User? = loadUserFromPreferencesUseCase()
        user?.let {
            UsuarioLogado.usuarioLogado = it
        }
        _navegaParaHome.value = UsuarioLogado.usuarioLogado != null
    }

    /**
     * Esse campo mantém o estado do User conforme o resultado
     * da consulta à API, por meio do GetUserUseCase
     */
    private val _user = MutableLiveData<UserState>()
    val user: LiveData<UserState>
        get() = _user

    /**
     * Esse campo observável dispara a exibição de um snackbar quando
     * ocorre um erro de consulta à API
     */
    val snackBar = Transformations.map(_user) {
        when (it) {
            is UserState.NoUser -> null
            is UserState.Failure -> it.error.message
            is UserState.Success -> null
        }
    }

    /**
     * Esse método cancela a troca de usuário e salva o usuário anterior
     * novamente nas SharedPreferences
     */
    fun cancelChangeUser(previousUser: User) {
        viewModelScope.launch {
            saveUserToPreferencesUseCase(previousUser)
        }
    }

    /**
     * Recebe um username da UI, atribui ao UsuarioLogado e dispara a
     * navegação para o HomeFragment.
     */
    fun setUsuarioLogado(user: String) {
        viewModelScope.launch {
            userUseCase(Query(user))
                .onStart {
                    _user.postValue(UserState.NoUser)
                }
                .catch {
                    _user.postValue(UserState.Failure(it))
                }
                .collect {
                    _user.postValue(UserState.Success(it))
                    UsuarioLogado.usuarioLogado = it
                    saveUserToPreferencesUseCase(it)
                    _navegaParaHome.value = true
                }
        }
    }

    /**
     * Essa classe selada mantém o estado da validação e carregamento do usuário.
     * Adotei estrutura similar à classe State de consulta à lista de Repos.
     */
    sealed class UserState {

        object NoUser : UserState()

        data class Success(val user: User) : UserState()

        data class Failure(val error: Throwable) : UserState()

    }

}


