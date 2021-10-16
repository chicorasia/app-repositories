package br.com.dio.app.repositories.presentation.adapter

import br.com.dio.app.repositories.data.model.Repo

/**
 * Essa classe define um listener de cliques no item da RecyclerView
 */
class RepoListClickListener(val clickListener: (repo: Repo) -> Unit) {

    /**
     * Possui um único método onClick(); seu comportamento é sobrescrito
     * durante a instanciação do Adapter.
     */
    fun onClick(repo: Repo) = clickListener(repo)
}