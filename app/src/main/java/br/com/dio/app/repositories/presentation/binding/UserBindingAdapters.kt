package br.com.dio.app.repositories.presentation.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.R
import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.User
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
@BindingAdapter("userAvatarIv")
fun ImageView.setAvatar(user: User?) {
    user?.let{ user ->
        Glide.with(this).load(user.avatarUrl).into(this)
        this.clipToOutline = true
    }
    if (user == null) {
        setImageResource(R.drawable.ic__avatar_placeholder)
    }
}

@BindingAdapter("userNameTv")
fun TextView.setRepoName(user: User?) {
    user?.let{
        text = it.login
    }
}

@BindingAdapter("userRepoCount")
fun TextView.setRepoCount(user: User?) : Int {
    return user?.let {
        it.publicRepos
    } ?: 0
}

@BindingAdapter("userNameLogin")
fun TextView.setUserName(user: User?) {
    user?.let{
        text = it.login
    }
    if (user == null) {
        text = resources.getText(R.string.welcome)
    }
}