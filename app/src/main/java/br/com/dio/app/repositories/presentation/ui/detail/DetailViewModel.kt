package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.GetRepoInfoUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


/**
 * Essa classe dá suporte ao DetailFragment
 */
class DetailViewModel(private val getRepoInfoUseCase: GetRepoInfoUseCase) : ViewModel() {

//    TODO: adicionar um getRepoReadMeUseCase
//    TODO: adicionar um getRepoScreenshotsUseCase

    /**
     * Esse campo mantém o Repo como um State, para facilitar
     * a manipulação da UI a partir do resultado da consulta.
     * Segue o mesmo padrão usado no HomeFragment.
     * Repare que o State agora é type-safe por meio da aplicação
     * dos generics.
     */
    private val _repo = MutableLiveData<State<Repo>>()
    val repo: LiveData<State<Repo>>
        get() = _repo


    /**
     * Por enquanto estou duplicando os dados do Repo
     * em campos de texto. Mas uma solução melhor seria
     * usar um adapter e fazer o binding dos dados
     * corretamente para reduzir o acoplamento
     */
    private val _repoName = MutableLiveData<String>()
    val repoName: LiveData<String>
        get() = _repoName

    private val _repoDescription = MutableLiveData<String>()
    val repoDescription: LiveData<String>
        get() = _repoDescription


    /**
     * Recupera um único repo da API e atribui ao campo _repo
     */

    fun fetchRepo(owner: String, repoName: String) {
        val query = Query(owner, repoName)
        viewModelScope.launch {
            getRepoInfoUseCase(query)
                .onStart {
                    _repo.postValue(State.Loading)
                }
                .catch {
                    _repo.postValue(State.Error(it))
                }
                .collect {
                    _repo.postValue(State.Success(it!!))
                    _repoName.postValue(it.name)
                    _repoDescription.postValue(it.description.toString())
                }
        }

    }

}