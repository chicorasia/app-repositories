package br.com.dio.app.repositories.presentation.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Esse fragmento representa a tela inicial do app, que pede a informação de
 * nome de usuário do github
 */
class UserFragment : Fragment() {


    private val mViewModel: UserViewModel by viewModel()
    private val binding: LoginFragmentBinding by lazy {
        LoginFragmentBinding.inflate(layoutInflater)
    }

    /**
     * Esse atributo recebe os argumentos que vieram via
     * Navigation Component
     */
    private val args by navArgs<UserFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /**
         * Inicializa as variáveis do arquivo XML e o LifeCycleOwner
         */
        binding.navController = findNavController()
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.user = UsuarioLogado.previousUser

        initNomeUsuarioEdt()
        initBtnEnviar()
        initNavegaParaHomeObserver()
        initSnackbar()
        initCloseBtn()

        return binding.root

    }

    /**
     * Inicializa um observer do campo snackBar; exibe uma mensagem de
     * erro quando ocorre falha na validação do usuário.
     */
    private fun initSnackbar() {
        mViewModel.snackBar.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    /**
     * Preenche o EditText com o nome de usuário (String) recebido via navArgs<>.
     * É essencialmente cosmético porque, nesse momento, o UsuarioLogado já
     * é null, mas melhora a experiência do usuário, que não precisa
     * digitar novamente se não pretender trocar de conta.
     */
    private fun initNomeUsuarioEdt() {
        args.user?.let {
            binding.loginUsernameEdt.setText(it) ?: ""
        }
    }

    /**
     * Observa o campo navegaParaHome do ViewModel e dispara a navegação para o HomeFragment
     */
    private fun initNavegaParaHomeObserver() {
        mViewModel.navegaParaHome.observe(viewLifecycleOwner) { navegaParaHome ->
            if (navegaParaHome) {
                val directions =
                    UserFragmentDirections.vaiDeLoginFragmentParaHomeFragment()
                findNavController().navigate(directions)
            }
        }
    }

    /**
     * Este método inicializa o botão de fechar a tela de usuário.
     * Como faz duas operações - salva o usuário atual nas preferências
     * e fecha a tela - não consegui definí-lo no XML.
     */
    private fun initCloseBtn() {
        with(binding.loginCloseBtn) {
            if(this.isVisible) {
                setOnClickListener {
                    restorePreviousUser()
                    findNavController().popBackStack()
                }
            }
        }
    }

    /**
     * Essa função encapsula o método que grava o usuário anterior
     * nas SharedPreferences. Efetivamente cancela a troca de usuário.
     */
    private fun restorePreviousUser() {
        binding.user?.let { mViewModel.cancelChangeUser(it) }
    }


    /**
     * Esse método configura o ClickListener. Faz uma validação básica (se está preenchido) e
     * aciona o método do ViewModel passando o nome de usuário do campo EditText como parâmetro.
     */
    private fun initBtnEnviar() {
        binding.loginBtn.setOnClickListener {
            val user = binding.loginUsernameEdt.text.toString()
            if (!user.isNullOrBlank()) {
                mViewModel.setUsuarioLogado(user)
            }
        }
    }

}