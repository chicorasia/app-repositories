package br.com.dio.app.repositories.presentation.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.view.menu.MenuBuilder
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.GithubApiFilter
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.databinding.HomeFragmentBinding
import br.com.dio.app.repositories.presentation.adapter.RepoListAdapter
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
    val adapter = RepoListAdapter()
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
                .setOnMenuItemClickListener { menuItem ->
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
                .setOnMenuItemClickListener { menuItem ->
                    user?.let{
                        mViewModel.getRepoList(it.login,
                        GithubApiFilter.SORT_BY_PUSHED)
                    }
                    true
                }
            menu.findItem(R.id.action_sort_name)
                .setOnMenuItemClickListener { menuItem ->
                    user?.let{
                        mViewModel.getRepoList(it.login,
                            GithubApiFilter.SORT_BY_NAME)
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
                HomeViewModel.State.Loading -> {
                    mViewModel.showProgressBar()
                }
                is HomeViewModel.State.Error -> {
                    mViewModel.hideProgressBar()
                    Snackbar.make(view, it.error.message.toString(), Snackbar.LENGTH_LONG).show()
                }
                is HomeViewModel.State.Success -> {
                    mViewModel.hideProgressBar()
                    adapter.submitList(it.list)

                }
            }
        }
    }


    companion object {
        fun newInstance() = HomeFragment()
    }
}