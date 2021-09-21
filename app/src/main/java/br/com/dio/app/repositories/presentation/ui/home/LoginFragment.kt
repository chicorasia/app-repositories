package br.com.dio.app.repositories.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.databinding.LoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Esse fragmento representa a tela inicial do app, que pede a informação de
 * nome de usuário do github
 */
class LoginFragment : Fragment() {


    private val mViewModel: LoginViewModel by viewModel()
    private val binding: LoginFragmentBinding by lazy {
        LoginFragmentBinding.inflate(layoutInflater)
    }

    /**
     * Esse atributo recebe os argumentos que vieram via
     * Navigation Component
     */
    private val args by navArgs<LoginFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        /**
         * Inicializa as variáveis do arquivo XML e o LifeCycleOwner
         */
        binding.navController = findNavController()
        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initNomeUsuarioEdt()
        initBtnEnviar()
        initNavegaParaHomeObserver()
        initSnackbar()

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
                val directions = LoginFragmentDirections.vaiDeLoginFragmentParaHomeFragment()
                findNavController().navigate(directions)
            }
        }
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