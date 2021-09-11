package br.com.dio.app.repositories.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.dio.app.repositories.data.user.UsuarioLogado

/**
 * Esse ViewModel dá suporte ao fragmento de login de usuário
 */
class LoginViewModel : ViewModel() {

    /**
     * Esse campo dispara a navegação para o HomeFragment
      */
    private val _navegaParaHome = MutableLiveData<Boolean>(false)
    val navegaParaHome: LiveData<Boolean>
        get() = _navegaParaHome

    /**
     * Inicializa o ViewModel com valor false para ficar no LoginFragment
     */
    init {
        _navegaParaHome.value = UsuarioLogado.usuarioLogado != null
    }


    /**
     * Recebe um username da UI, atribui ao UsuarioLogado e dispara a
     * navegação para o HomeFragment. Esse método precisa ser aprimorado com
     * uma rotina de validação do nome de usuário junto à API.
     */
    //TODO: adicionar validação de nome de usuário
    //TODO: mover para um use case
    fun setUsuarioLogado(user: String) {
        user.let{
            UsuarioLogado.usuarioLogado = it
            _navegaParaHome.value = true
        }
    }

}