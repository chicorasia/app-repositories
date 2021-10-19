package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.ViewModel


/**
 * Essa classe dá suporte ao DetailFragment
 */
class DetailViewModel : ViewModel() {

    var helloText = ""

    fun setRepoNameText(repoName: String) {
        helloText = repoName
    }
}