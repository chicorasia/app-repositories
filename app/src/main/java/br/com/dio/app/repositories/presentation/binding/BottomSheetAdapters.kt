package br.com.dio.app.repositories.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED

/**
 * Esse adapter serviria para controlar o ImageButton da Bottom Sheet,
 * mas ainda não consegui vincular os comportamentos corretamente.
 * TODO: vincular comportamentos corretamente
 */
@BindingAdapter("bsButtonIv")
fun ImageView.setButtonImage(behavior: BottomSheetBehavior<*>) {
    behavior.state?.let {
        if (it.equals(STATE_COLLAPSED)) {
            setImageResource(R.drawable.ic_up)
        } else {
            setImageResource(R.drawable.ic_close)
        }
    }
}