package br.com.dio.app.repositories.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.data.model.Repo
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import org.w3c.dom.Text

/**
 * Esse arquivo mantém vários adaptadores para fazer o binding
 * dos dados no item_repo.xml
 */

@BindingAdapter("itemUserAvatarIv")
fun ImageView.setAvatar(repo: Repo?) {
    repo?.let{ repo ->
        Glide.with(this).load(repo.owner.avatarURL).into(this)
    }
}

@BindingAdapter("itemRepoNameTv")
fun TextView.setRepoName(repo: Repo?) {
    repo?.let{
        text = it.name
    }
}

@BindingAdapter("itemDescricaoTv")
fun TextView.setRepoDescricao(repo: Repo?) {
    repo?.let {
        text = it.description
    }
}

@BindingAdapter("itemStarChip")
fun Chip.setStargazerCount(repo: Repo?) {
    repo?.let {
        text =  it.stargazersCount.toString()
    }
}

//    TODO: criar adapter para formatar o itemLanguageChip conforme valor
@BindingAdapter("itemLanguageChip")
fun Chip.setLanguage(repo: Repo?) {
    repo?.let {
        text = it.language
    }
}

@BindingAdapter("itemUpdateChip")
fun Chip.setUpdate(repo: Repo?) {
    repo?.let{
        text = repo.updatedAt.subSequence(0, 8)
    }
}