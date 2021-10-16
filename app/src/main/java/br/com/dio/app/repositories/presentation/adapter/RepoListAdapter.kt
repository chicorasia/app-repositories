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
 * ListAdapter. Recebe um clickListener como parâmetro do construtor, que
 * depois é repassado para o método bind() do ViewHolder.
 */
class RepoListAdapter(
    private val clickListener: RepoListClickListener
    ) : ListAdapter<Repo, RepoListAdapter.RepoViewHolder>(RepoDiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
        RepoViewHolder.from(parent)

    /**
     * Aqui o clickListener é simplesmente passado o ViewHolder.
     */
    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
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
         * "repo", e o clickListener recebido do Adapter para a variável clickListener.
         * Como o ViewHolder tem conhecimento do Repo, eu posso simplesmente chamar o
         * onClick() no XML, passando o repo como parâmetro (ver o item_repo.xml).
         * A vinculação é definida no arquivo XML com o apoio de BindingAdapters.
         */
        fun bind(item: Repo, clickListener: RepoListClickListener) {
            binding.repo = item
            binding.clickListener = clickListener
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