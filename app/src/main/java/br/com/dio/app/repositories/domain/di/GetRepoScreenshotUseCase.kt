package br.com.dio.app.repositories.domain.di

import br.com.dio.app.repositories.data.model.Repo
import com.limerse.slider.model.CarouselItem

class GetRepoScreenshotUseCase(val repo: Repo?) {

    //recebe um Set<String> e um repo
    //gera uma lista de CarouselItems à partir dessas informações

    val RAW_GITHUB_URL = "https://raw.githubusercontent.com"

    fun generateListOfCarouselItems(screenshotFilename: Set<String>) : List<CarouselItem> {
        return repo?.let {
            screenshotFilename.map {
                CarouselItem("$RAW_GITHUB_URL/${repo.owner.login}/${repo.name}/${repo.defaultBranch}/$it", caption = "$it")
            }
        } ?: emptyList<CarouselItem>()
    }

}
