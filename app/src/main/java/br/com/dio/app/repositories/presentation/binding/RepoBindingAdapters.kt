package br.com.dio.app.repositories.presentation

import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.presentation.binding.chipColorMap
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Esse arquivo mantém vários adaptadores para fazer o binding
 * dos dados no item_repo.xml
 */

const val OTHER = "Other"

@BindingAdapter("itemUserAvatarIv")
fun ImageView.setAvatar(repo: Repo?) {
    repo?.let { repo ->
        Glide.with(this).load(repo.owner.avatarURL).into(this)
    }
}

@BindingAdapter("itemRepoNameTv")
fun TextView.setRepoName(repo: Repo?) {
    repo?.let {
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
        text = it.stargazersCount.toString()
    }
}

/**
 * Esse adapter atribui a linguagem e seleciona a cor do chip
 * a partir do map em ChipColors.kt. Caso o retorno da API seja
 * null ou uma linguagem ausente do map ele aplica o valor
 * default. Essa solução possui baixo acoplamento.
 */
@BindingAdapter("itemLanguageChip")
fun Chip.setLanguage(repo: Repo?) {
    repo?.let {
        text = it.language ?: OTHER
        val color = it.language.let { language ->
            chipColorMap[language]
        } ?: chipColorMap[OTHER]
        color?.let { chipColor ->
            setChipBackgroundColorResource(chipColor)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("itemUpdateChip")
fun Chip.setUpdate(repo: Repo?) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu").withZone(ZoneId.from(ZoneOffset.UTC))
    repo?.let {
        val date = Instant.parse(repo.updatedAt)
        val dataFormatada = formatter.format(date)
        text = dataFormatada
    }
}

