package br.com.dio.app.repositories.presentation.ui.home

import androidx.lifecycle.*
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
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
    private val listUserRepositoriesUseCase: ListUserRepositoriesUseCase
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
     * Esse campo matém as informações do dono dos repositórios (usuário ativo)
     */

    private val _owner = MutableLiveData<Owner>(null)
    val owner: LiveData<Owner>
        get() = _owner

    /**
     * Esse campo dispara a navegação para o LoginFragment. Os métodos seguintes
     * permitem alterar o valor e resetar o valor após a navegação.
     */
    private val _navegaParaLogin = MutableLiveData<Boolean>(false)
    val navegaParaLogin : LiveData<Boolean>
        get() = _navegaParaLogin

    fun navegaParaLogin() {
        _navegaParaLogin.value = true
    }

    fun doneNavegaParaLogin() {
        _navegaParaLogin.value = false
    }

    /**
     * Esse campo mantém o State do Flow. Foi usada a técnica de encapsulamento
     * padrão recomendada pela Google.
     */
    private val _repo = MutableLiveData<State>()
    val repo: LiveData<State>
        get() = _repo

    /**
     * Conta a quantidade de repositórios recebidos da API; está limitado ao máximo de
     * repos retornado por página.
     */
    val repoSize = Transformations.map(repo) {
        if(it is State.Success) {
            return@map it.list.size
        } else {
            return@map 0
        }
    }


    /**
     * Essa sintaxe funciona porque, na superclasse UseCase<Param, Source> o operador
     * invoke aponta para a função execute(user: String). Equivale a escrever:
     *      listUserRepositoriesUseCase.execute(user)
     * O retorno dessa função é um Flow<List<Repo>>; flow possui três estados possíveis
     * que devem ser tratados e atribuídos ao _repo: onStart{ }, catch{ } e collect{ }.
     */
    fun getRepoList(user: String) {
        viewModelScope.launch {
            listUserRepositoriesUseCase(user)
                .onStart {
                    _repo.postValue(State.Loading)
                }
                .catch {
                    _repo.postValue(State.Error(it))
                }
                .collect {
                    _owner.postValue(it.first().owner)
                    _repo.postValue(State.Success(it))
                }
        }
    }

    /**
     * Essa classe selada representa os três estados possíveis do Flow: carregando, erro
     * e sucesso.
     */
    sealed class State {

        /**
         * O estado de Loading pode ser um object porque não possui atributos.
         */
        object Loading : State()

        /**
         * Os casos de Success e Failure possuem atributos, então é necessário
         * criá-los como data classes. O atributo é modificado conforme o estado:
         * uma List<Repo> no caso de sucesso e um Throwable no caso de falha.
         */
        data class Success(val list: List<Repo>) : State()

        data class Error(val error: Throwable) : State()
    }

}