package br.com.dio.app.repositories.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding()
        return binding.root
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

        mViewModel.getRepoList("chicorasia")
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