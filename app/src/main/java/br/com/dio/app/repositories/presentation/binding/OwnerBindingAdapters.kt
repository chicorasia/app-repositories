package br.com.dio.app.repositories.presentation.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.data.model.Owner
import com.bumptech.glide.Glide

/**
 * Esse arquivo mantém adapters para exibir os dados do Owner dos repositórios.
 */

/**
 * Esse adapter usa a biblioteca Glide para carregar uma imagem à partir
 * da sua URL. A ImageView possui um atributo background que recebe um
 * shape oval (um círculo). Na última linha, se seta o atributo clipToOutline
 * para aplicar a máscara circular à imagem.
 */
@BindingAdapter("ownerUserAvatarIv")
fun ImageView.setAvatar(owner: Owner?) {
    owner?.let{ owner ->
        Glide.with(this).load(owner.avatarURL).into(this)
        this.clipToOutline = true
    }
}

@BindingAdapter("ownerUserNameTv")
fun TextView.setRepoName(owner: Owner?) {
    owner?.let{
        text = it.login
    }
}