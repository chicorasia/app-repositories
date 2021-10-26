package br.com.dio.app.repositories.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.DetailBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * TODO: Essa classe não está sendo usada. Considere deletar.
 */
class DetailBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var mViewModel: DetailViewModel
    private lateinit var repo: Repo
    private val binding: DetailBottomSheetBinding by lazy {
        DetailBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun updateViewModel(viewModel: DetailViewModel) {
        mViewModel = viewModel
    }

    fun bind(item: Repo) {
        repo = item
    }


    companion object {
        const val TAG = "DetailBottomSheet"
    }

}
