package br.com.dio.app.repositories.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.ItemRepoBinding

/**
 * Essa classe define um adapter para a recyclerview de Repos. Estende a classe
 * ListAdapter.
 */
class RepoListAdapter() : ListAdapter<Repo, RepoListAdapter.RepoViewHolder>(RepoDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder.from(parent)

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * Optei por usar uma classe aninhada ao invés de classe interna para poder
     * manter duas boas práticas: (1) inflar o layout a partir da própria classe
     * ViewHolder e (2) delegar o binding dos dados para a própria classe ViewHolder.
     */
    class RepoViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {

            fun from(parent: ViewGroup) : RepoViewHolder {
                val binding: ItemRepoBinding = ItemRepoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return RepoViewHolder(binding)
            }

        }

        /**
         * Estou usando um DataBindingLayout; aqui eu atribuo o item para a variável
         * "repo". A vinculação é definida no arquivo XML com o apoio de BindingAdapters.
         */
        fun bind(item: Repo) {
            binding.repo = item
        }

    }

    /**
     * Métodos CallBack para o DiffUtil. Isso é quase boilerplate.
     */
    class RepoDiffUtilCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }

    }



}