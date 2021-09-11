package br.com.dio.app.repositories.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.data.user.UsuarioLogado
import br.com.dio.app.repositories.databinding.LoginFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {


    private val mViewModel: LoginViewModel by viewModel()
    private val binding: LoginFragmentBinding by lazy {
        LoginFragmentBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding.viewModel = mViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.loginBtn.setOnClickListener {
            val user = binding.loginUsernameEdt.text.toString()
            mViewModel.setUsuarioLogado(user)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.navegaParaHome.observe(viewLifecycleOwner) { navegaParaHome ->
            if(navegaParaHome){
                val directions = LoginFragmentDirections.vaiDeLoginFragmentParaHomeFragment()
                findNavController().navigate(directions)
            }
        }

    }


}