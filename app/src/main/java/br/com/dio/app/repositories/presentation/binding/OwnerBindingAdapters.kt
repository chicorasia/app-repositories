package br.com.dio.app.repositories.presentation.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.data.model.Owner
import com.bumptech.glide.Glide

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