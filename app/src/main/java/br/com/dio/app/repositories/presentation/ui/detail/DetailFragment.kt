package br.com.dio.app.repositories.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.core.State
import br.com.dio.app.repositories.databinding.DetailBottomSheetBinding
import br.com.dio.app.repositories.databinding.DetailFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.limerse.slider.model.CarouselItem
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
        initScreenshotObserver()
        initCarousel()

        return binding.root

    }

    /**
     * Inicializa o carossel de imagens
     */
    private fun initCarousel() {
        with(binding.detailCarousel) {
            registerLifecycle(lifecycle)
            mViewModel.repoListCarouselItem.observe(viewLifecycleOwner) {
                setData(mViewModel.repoListCarouselItem.value ?: emptyList<CarouselItem>())
            }
        }
    }

    /**
     * A bottom sheet está sendo tratada como um layout incluído nesse fragment.
     * Deve ter uma maneira melhor de fazer isso, movendo-a para um fragmento à parte,
     * mas isso é o melhor que eu consegui fazer até agora.
     */
    private fun initBottomSheet() {

        with(binding.detailInclude) {

            viewModel = mViewModel
            behaviour = bottomSheetBehavior

            /**
             * É necessário definir o lifeCycleOwner para o LiveDate funcionar corretamente.
             * Note que não estão sendo usados observers - está tudo sendo resolvido
             * por meio de data binding no arquivo XML
             */
            lifecycleOwner = viewLifecycleOwner


            /**
             * Atribuindo os comportamentos tanto à bottom sheet quanto ao button
             */
            root.setOnClickListener {
                toggleBottomSheetState()
            }
            bsCloseBtn.setOnClickListener {
                toggleBottomSheetState()
            }

            /**
             * Atribui o string do arquivo README.md à visualização de Markdown
             */
            mViewModel.repoReadme.observe(viewLifecycleOwner) {
                it?.let{ bsReadme.setMarkDownText(it) }
            }
        }
    }

    /**
     * Esse método alterna o estado da Bottom Sheet. Ele também está
     * com as responsabilidades de virar o ícone e exibir/ocultar
     * o nome do Repo. Isso pode ser melhor resolvido por meio de um
     * BindingAdapter.
     */
    private fun DetailBottomSheetBinding.toggleBottomSheetState() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bsCloseBtn.rotation = 180F
            bsRepoNameTv.visibility = View.GONE
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bsCloseBtn.rotation = 0F
            bsRepoNameTv.visibility = View.VISIBLE
        }
    }

    /**
     * Acessa os argumentos recebidos via Navigation e atualiza dos dados na tela.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel.fetchRepo(owner = argumentos.owner, repoName = argumentos.repoName)
    }

    /**
     * Processa o string do README e atualiza a contagem de screenshots
     * no ViewModel
     */
    private fun initRepoObserver() {
        mViewModel.repoReadme.observe(viewLifecycleOwner) {
            it?.let {
                mViewModel.fetchScreenshot(it)
            }
        }
    }

    /**
     * Exibe o estado e o resultado do processamento do README.
     */
    private fun initScreenshotObserver() {
        mViewModel.repoScreenshot.observe(viewLifecycleOwner) { state ->
            with(binding) {
                when (state) {
                    State.Loading -> {
                        detailHelloText.visibility = View.VISIBLE
                        detailHelloText.text = getString(R.string.loading)
                        detailCarousel.showNavigationButtons = false
                    }
                    is State.Error -> {
                        detailHelloText.text = getString(R.string.loading_error)
                    }
                    is State.Success -> {
                        /**
                         * Modifica a visibilidade dos componentes do carrossel
                         * conforme o resultado da consulta e a quantidade de
                         * screenshots recebidas.
                         */
                        when {
                            state.result == 1 -> {
                                detailHelloText.visibility = View.GONE
                            }
                            state.result > 1 -> {
                                detailHelloText.visibility = View.GONE
                                detailCarousel.showNavigationButtons = true
                            }
                            else -> {
                                detailHelloText.text = getString(R.string.no_screenshots)
                                detailCarousel.showNavigationButtons = false
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = DetailFragment()
    }

}
