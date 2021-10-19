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


    private val _repo = MutableLiveData<State<Repo>>()
    val repo: LiveData<State<Repo>>
        get() = _repo

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
                }
        }

    }
}