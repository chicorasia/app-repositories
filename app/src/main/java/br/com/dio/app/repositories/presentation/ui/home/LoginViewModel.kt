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

    init {
        _navegaParaHome.value = UsuarioLogado.usuarioLogado != null
    }

    fun setUsuarioLogado(user: String?) {
        user?.let{
            UsuarioLogado.usuarioLogado = it
            _navegaParaHome.value = true
        }
    }

}