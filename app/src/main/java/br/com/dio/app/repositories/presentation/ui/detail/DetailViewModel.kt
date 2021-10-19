package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var helloText = "Hello, I'm detail fragment"

    fun setRepoNameText(repoName: String) {
        helloText = repoName
    }
}