package br.com.dio.app.repositories.presentation.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.databinding.DetailFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Esse fragmento representa a tela de detalhes de um Repo.
 */
class DetailFragment : Fragment() {

    private val mViewModel: DetailViewModel by viewModel()
    private val binding: DetailFragmentBinding by lazy {
        DetailFragmentBinding.inflate(layoutInflater)
    }

    private val argumentos: DetailFragmentArgs by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Vincular o DetailViewModel e o NavController com o XML
         */
        binding.viewModel = mViewModel
        binding.navController = findNavController()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel.setRepoNameText(argumentos.repoName)
    }

    companion object {
        fun newInstance() = DetailFragment()
    }

}