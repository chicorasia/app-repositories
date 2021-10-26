package br.com.dio.app.repositories.presentation.ui.home

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.BlendMode
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.GithubApiFilter
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.databinding.HomeFragmentBinding
import br.com.dio.app.repositories.presentation.adapter.RepoListAdapter
import br.com.dio.app.repositories.presentation.adapter.RepoListClickListener
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Esse fragmento corresponde à tela principal (inicial) do app.
 */
class HomeFragment : Fragment() {

    /**
     * Usa o delegate do Koin para injetar a dependência
     */
    val mViewModel: HomeViewModel by viewModel()
    val binding: HomeFragmentBinding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    /**
     * Inicializa o adapter passando uma instância do RepoListClickListener
     * e o seu comportamento. A navegação é controlada pelo ViewModel com uma
     * variável observável. Depois de disparar a navegação a variável é resetada.
     */
    private val adapter = RepoListAdapter(RepoListClickListener { repo ->
        mViewModel.navegaParaDetail(repo)
        mViewModel.doneNavegaParaDetail()
    })

    val user = UsuarioLogado.usuarioLogado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initOptionMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        initBinding()
        initNavegacaoLogin()
        initUserInfo()
        initNavegacaoDetail()
        return binding.root
    }

    /**
     * Infla o menu overflow
     */
    @SuppressLint("RestrictedApi")
    private fun initOptionMenu() {
        with(binding.homeToolbar) {
            /**
             * A inflação do menu tem que acontecer aqui dentro
             * pra conseguir mostrar o ícone de troca de usuário
             */
            inflateMenu(R.menu.main_menu)

            if (menu is MenuBuilder) (menu as MenuBuilder).setOptionalIconsVisible(true)
            menu.findItem(R.id.action_change_user)
                .setOnMenuItemClickListener { _ ->
                    mViewModel.navegaParaLogin()
                    mViewModel.doneNavegaParaLogin()
                    true
                }

            /**
             * Vinculando as buscas à API aos itens do menu. Cada item
             * dispara uma nova busca com a string adequada definida
             * na enum GithubApiFilter
             */
            menu.findItem(R.id.action_sort_date)
                .setOnMenuItemClickListener { _ ->
                    user?.let {
                        mViewModel.getRepoList(
                            it.login,
                            GithubApiFilter.SORT_BY_PUSHED
                        )
                    }
                    true
                }
            menu.findItem(R.id.action_sort_name)
                .setOnMenuItemClickListener { _ ->
                    user?.let {
                        mViewModel.getRepoList(
                            it.login,
                            GithubApiFilter.SORT_BY_NAME
                        )
                    }
                    true
                }
        }
    }

    /**
     * Inicializa um observer para expor as informações do dono do repositório
     */
    private fun initUserInfo() {
        mViewModel.user.observe(viewLifecycleOwner) {
            it?.let {
                binding.user = it
            }
        }
    }


    /**
     * Esse método inicializa o observador que dispara a navegação para a tela de login;
     * Sempre que a navegação ocorre, o UsuarioLogado é resetado depois de ter seu valor
     * atribuído ao "currentUser". Essa String é então passada para o LoginFragment como
     * argumento.
     */
    private fun initNavegacaoLogin() {
        mViewModel.navegaParaLogin.observe(viewLifecycleOwner) { navegaParaLogin ->
            if (navegaParaLogin) {
                val currentUser: String = user?.login ?: ""
                UsuarioLogado.previousUser = user.also {
                    UsuarioLogado.usuarioLogado = null
                }
                val directions = HomeFragmentDirections.actionGlobalLoginFragment()
                directions.user = currentUser
                findNavController().navigate(directions)
            }
        }
    }

    /**
     * Esse método inicializa a propriedade binding do layout e atribui o
     * ViewLifeCycleOwner para permitir a observação de LiveData.
     */
    private fun initBinding() {
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user?.let {
            mViewModel.getRepoList(it.login, GithubApiFilter.SORT_BY_NAME)
        }
        binding.homeRepoRv.adapter = adapter
        initRepoObserver(view)
    }


    /**
     * Esse método configura o observer do campo repo do ViewModel.
     */
    private fun initRepoObserver(view: View) {
        mViewModel.repo.observe(viewLifecycleOwner) {
            when (it) {
                State.Loading -> {
                    mViewModel.showProgressBar()
                }
                is State.Error -> {
                    mViewModel.hideProgressBar()
                    Snackbar.make(view, it.error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                is State.Success -> {
                    mViewModel.hideProgressBar()
                    adapter.submitList(it.result)
                }
            }
        }
    }

    /**
     * Esse método configura o observer do campo navegaParaDetail e a navegação
     * para o fragmento de detalhes do repo.
     * Optei por passar somente os parâmetros owner e repoName como String
     * porque o DetailViewModel emprega um caso de uso próprio para carregar
     * os detalhes do Repo.
     */
    private fun initNavegacaoDetail() {
        mViewModel.navegaParaDetail.observe(viewLifecycleOwner) { repo ->
            repo?.let {
                val directions =
                    HomeFragmentDirections.vaiDeHomeFragmentParaDetailFragment(
                        repo.name,
                        repo.owner.login
                    )
                findNavController().navigate(directions)
            }
        }
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}