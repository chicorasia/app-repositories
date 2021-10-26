package br.com.dio.app.repositories.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.core.GithubApiFilter
import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.model.User
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.domain.ClearUserFromPreferencesUseCase
import br.com.dio.app.repositories.domain.ListUserRepositoriesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * Essa classe dá suporte ao HomeFragment. Deve ter apenas os Use Cases como dependência
 * e não deve ter repositório, DAO ou service como dependência para manter as boas
 * práticas de Clean Architecture
 */
class HomeViewModel(
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase,
    private val clearUserFromPreferencesUseCase: ClearUserFromPreferencesUseCase
) : ViewModel() {

    /**
     * Esse campo e as respectivas funções controlam a visibilidade
     * da ProgressBar
     */
    private val _progressBarVisible = MutableLiveData<Boolean>(false)
    val progressBarVisible: LiveData<Boolean>
        get() = _progressBarVisible

    fun showProgressBar() {
        _progressBarVisible.value = true
    }

    fun hideProgressBar() {
        _progressBarVisible.value = false
    }

    /**
     * Esse campo mantém as informações do dono dos repositórios (usuário ativo)
     */

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    /**
     * Esse campo dispara a navegação para o LoginFragment. Os métodos seguintes
     * permitem alterar o valor e resetar o valor após a navegação.
     */
    private val _navegaParaLogin = MutableLiveData<Boolean>(false)
    val navegaParaLogin : LiveData<Boolean>
        get() = _navegaParaLogin

    fun navegaParaLogin() {
        clearUserFromPreferencesUseCase()
        _navegaParaLogin.value = true
    }

    fun doneNavegaParaLogin() {
        _navegaParaLogin.value = false
    }

    /**
     * Esse campo dispara a navegação para o DetailFragment; a navegação
     * acontece quando o valor do campo não é nulo.
     */
    private val _navegaParaDetail = MutableLiveData<Repo?>(null)
    val navegaParaDetail: LiveData<Repo?>
        get() = _navegaParaDetail

    fun navegaParaDetail(repo: Repo) {
        _navegaParaDetail.value = repo
    }

    fun doneNavegaParaDetail() {
        _navegaParaDetail.value= null
    }


    /**
     * Esse campo mantém o State do Flow. Foi usada a técnica de encapsulamento
     * padrão recomendada pela Google.
     */
    private val _repo = MutableLiveData<State<List<Repo>>>()
    val repo: LiveData<State<List<Repo>>>
        get() = _repo

    /**
     * Essa sintaxe funciona porque, na superclasse UseCase<Param, Source> o operador
     * invoke aponta para a função execute(user: String). Equivale a escrever:
     *      listUserRepositoriesUseCase.execute(user)
     * O retorno dessa função é um Flow<List<Repo>>; flow possui três estados possíveis
     * que devem ser tratados e atribuídos ao _repo: onStart{ }, catch{ } e collect{ }.
     */
    fun getRepoList(user: String, sorting: GithubApiFilter) {
        viewModelScope.launch {
            listUserRepositoriesUseCase(Query(user = user, option1 = sorting.sortby))
                .onStart {
                    _repo.postValue(State.Loading)
                    _user.postValue(UsuarioLogado.usuarioLogado)
                }
                .catch {
                    _repo.postValue(State.Error(it))
                }
                .collect {
                    _repo.postValue(State.Success(it))
                }
        }
    }

}