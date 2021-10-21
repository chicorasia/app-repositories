package br.com.dio.app.repositories.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.dio.app.repositories.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_dialog, container, false)

    companion object {
        const val TAG = "DetailBottomSheet"
    }

}
