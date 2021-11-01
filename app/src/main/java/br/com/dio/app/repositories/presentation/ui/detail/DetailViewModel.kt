package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.app.repositories.core.Query
import br.com.dio.app.repositories.core.RemoteException
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.domain.GetRepoInfoUseCase
import br.com.dio.app.repositories.domain.GetRepoReadmeUseCase
import br.com.dio.app.repositories.domain.di.GetRepoScreenshotUseCase
import br.com.dio.app.repositories.presentation.getScreenshotFileNamesAsList
import com.limerse.slider.model.CarouselItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


/**
 * Essa classe dá suporte ao DetailFragment
 */

const val REPO_README_FALLBACK = "*Este repo não possui um arquivo README.md*"

class DetailViewModel(
    private val getRepoInfoUseCase: GetRepoInfoUseCase,
    private val getRepoReadmeUseCase: GetRepoReadmeUseCase,
) : ViewModel() {


    /**
     * Esse use case vai ser usado para gerar uma lista de URLs das screenshots no
     * formato esperado pelo Carousel. Como ele precisa receber um repo como
     * parâmetro do construtor, optei por uma inicialização tardia
     * depois que o repo é recuperado da API
     */
    private lateinit var getRepoScreenshotsUseCase: GetRepoScreenshotUseCase

    /**
     *
     */
    private val _repoListCarouselItem = MutableLiveData<List<CarouselItem>>()
    val repoListCarouselItem: LiveData<List<CarouselItem>>
        get() = _repoListCarouselItem

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

    private val _repoReadme = MutableLiveData<String>()
    val repoReadme: LiveData<String>
        get() = _repoReadme


    /**
     * Um campo para manter uma lista de screenshots.
     * Algo me diz que isso deveria ir para uma classe à parte.
     */
    private val _repoScreenshot = MutableLiveData<State<Int>>()
    val repoScreenshot: LiveData<State<Int>>
        get() = _repoScreenshot


    /**
     * Recupera um único repo da API e atribui ao campo _repo.
     * Depois, instancia o GetRepoScreenShot com o objeto
     * recebido da API
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
                    getRepoScreenshotsUseCase = GetRepoScreenshotUseCase(it)
                    _repoName.postValue(it.name)
                    with(it) {
                        fetchReadme(this)
                    }
                }
        }
    }

    /**
     * Esse método recupera o arquivo md do README e atribui
     * ao campo _repoReadme. Se o repo não tiver um arquivo,
     * trata a RemoteException adicionando um texto 'fallback'.
     */
    private fun fetchReadme(repo: Repo) {
        val query = Query(repo.owner.login, repo.name, repo.defaultBranch)
        viewModelScope.launch {
            try {
                _repoReadme.value = getRepoReadmeUseCase(query).first()
            } catch (ex: RemoteException) {
                _repoReadme.value = REPO_README_FALLBACK
            }

        }
    }

    /**
     * Esse método processa o String do readme para extrair
     * os nomes de arquivo das screenshots, usando a função
     * definida em DetailUtil para gerar um Set<String>
     * Depois ele atribui o tamanho desse set a _repoScreenshot
     * e invoca o GetRepoScreenShotUseCase para povoar a
     * list com os objetos CarouselItem.
     */
    fun fetchScreenshot(readme: String) {
        viewModelScope.launch {
            _repoScreenshot.postValue(State.Loading)
            /**
             * Um atraso de 500 milissegundos só para efeitos cosméticos...
             */
            delay(500)
            try {
                val filenames = readme.getScreenshotFileNamesAsList()
                _repoScreenshot.postValue(State.Success<Int>(filenames.size))
                _repoListCarouselItem.postValue(getRepoScreenshotsUseCase.generateListOfCarouselItems(filenames))
            } catch (ex: Exception) {
                _repoScreenshot.postValue(State.Error(ex))
            }
        }
    }

}