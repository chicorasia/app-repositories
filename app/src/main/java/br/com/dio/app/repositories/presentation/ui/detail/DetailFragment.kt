package br.com.dio.app.repositories.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.DetailFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
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

    private val bottomSheetBehavior by lazy {
        BottomSheetBehavior.from(binding.detailInclude.root)
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
     * A bottom sheet está sendo tratada como um layout incluído nesse fragment.
     * Deve ter uma maneira melhor de fazer isso, movendo-a para um fragmento à parte,
     * mas isso é o melhor que eu consegui fazer até agora.
     */
    private fun initBottomSheet() {

        binding.detailInclude.viewModel = mViewModel

        /**
         * É necessário definir o lifeCycleOwner para o LiveDate funcionar corretamente.
         * Note que não esão sendo usados observers - está tudo sendo resolvido
         * por meio de data binding no arqui XML
         */
        binding.detailInclude.lifecycleOwner = viewLifecycleOwner

        binding.detailInclude.root.setOnClickListener {
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
                }
            }
        }
    }

    companion object {
        fun newInstance() = DetailFragment()
    }

}
