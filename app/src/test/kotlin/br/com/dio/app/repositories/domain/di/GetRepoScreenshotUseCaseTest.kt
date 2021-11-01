package br.com.dio.app.repositories.domain.di

import br.com.dio.app.repositories.data.model.Owner
import br.com.dio.app.repositories.data.model.Repo
import org.junit.Test

internal class GetRepoScreenshotUseCaseTest {

    val repo = Repo(owner = Owner("chicorasia", ""), description = "", id = 0L, stargazersCount = 0L, name = "testeRepo", language = "kotlin", defaultBranch = "test", htmlURL = "", createdAt = "", updatedAt = "", pushedAt = "", license = null)
    val setScreenshot = setOf<String>("Screenshot_1.jpg", "Screenshot_2.png")

    @Test
    fun deve_GerarListDeCarouselItems_AoReceberUmRepoEUmSetString() {

        val carouselItemList = GetRepoScreenshotUseCase(repo).generateListOfCarouselItems(setScreenshot)

        carouselItemList.forEach {
            println(it.imageUrl)
        }

    }
}