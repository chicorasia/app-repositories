package br.com.dio.app.repositories.presentation.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.BottomSheetDialogBinding
import br.com.dio.app.repositories.databinding.DetailFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Esse fragmento representa a tela de detalhes de um Repo.
 */
class DetailFragment : Fragment() {

    private val mViewModel: DetailViewModel by viewModel()
    private val binding: DetailFragmentBinding by lazy {
        DetailFragmentBinding.inflate(layoutInflater)
    }
    private val bottomSheetDialogBinding: BottomSheetDialogBinding by lazy {
        BottomSheetDialogBinding.inflate(layoutInflater)
    }

    private val argumentos: DetailFragmentArgs by navArgs<DetailFragmentArgs>()

    private lateinit var bottomSheetLayout: ConstraintLayout
    private lateinit var detailBottomSheet: DetailBottomSheet
    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(bottomSheetLayout)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * Vincular o DetailViewModel e o NavController com o XML
         */
        binding.viewModel = mViewModel
        binding.navController = findNavController()

        initBottomSheet()
        initRepoObserver()

        return binding.root

    }

    /**
     * This is a mess...
     */
    private fun initBottomSheet() {
        bottomSheetLayout = binding.root.findViewById(R.id.bottomSheetLayout)
        bottomSheetDialogBinding.lifecycleOwner = viewLifecycleOwner
        bottomSheetLayout.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    /**
     * Acessa os argumentos recebidos via Navigation e atualiza dos dados na tela.
     * Por enquanto, apenas o nome do Repo.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel.fetchRepo(owner = argumentos.owner, repoName = argumentos.repoName)
    }

    fun initRepoObserver() {
        mViewModel.repo.observe(viewLifecycleOwner) {
            when(it) {
                State.Loading -> {
                    binding.detailHelloText.text = "Carregando..."
                }
                is State.Error -> {
                    binding.detailHelloText.text = "Ocorreu um erro ao carregar"
                }
                is State.Success -> {
                    binding.detailHelloText.text = "${it.result.name}"
                    bottomSheetDialogBinding.bind(it.result)
                }
            }
        }
    }

    companion object {
        fun newInstance() = DetailFragment()
    }

}

private fun BottomSheetDialogBinding.bind(result: Repo) {
    this.repo = result
    executePendingBindings()
}
