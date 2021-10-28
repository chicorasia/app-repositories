package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.GetRepoInfoUseCase
import br.com.dio.app.repositories.domain.GetRepoReadmeUseCase
import br.com.dio.app.repositories.presentation.getScreenshotFileNamesAsList
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


/**
 * Essa classe dá suporte ao DetailFragment
 */
class DetailViewModel(
    private val getRepoInfoUseCase: GetRepoInfoUseCase,
    private val getRepoReadmeUseCase: GetRepoReadmeUseCase,
) : ViewModel() {

//    TODO: adicionar um getRepoScreenshots

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


    private val _repoReadme = MutableLiveData<String>()
    val repoReadme: LiveData<String>
        get() = _repoReadme


    /**
     * Um campo para manter uma lista de screenshots.
     * Algo me diz que isso deveria ir para uma classe à parte.
     * TODO: Usar uma classe State para melhorar a representação no Fragment
     */
    private val _repoScreenshot = MutableLiveData<Int>()
    val repoScreenshot: LiveData<Int>
        get() = _repoScreenshot


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
                    with(it) {
                        fetchReadme(this)
                    }
                }
        }

    }

    /**
     * Esse método recupera o arquivo md do README e atribui
     * ao campo _repoReadme.
     */
    private fun fetchReadme(repo: Repo) {
        val query = Query(repo.owner.login, repo.name, repo.defaultBranch)
        viewModelScope.launch {
            _repoReadme.value = getRepoReadmeUseCase(query).first()
        }
    }

    fun fetchScreenshot(readme: String) {
        _repoScreenshot.value = readme.getScreenshotFileNamesAsList().size
    }

}