package br.com.dio.app.repositories.presentation

import junit.framework.Assert.assertTrue
import org.junit.Test

internal class DetailUtilKtTest {

    val markdown = """- Compartilhar um cartão a partir da tela de home
                    - Remover um cartão com clique longo no item da lista
                    ## Screenshots

                    ![Screenshot_1](Screenshot_1.png)
                    ![Screenshot_2](Screenshot_2.png)
                    ![Screenshot_3](Screenshot_3.png)
                    ![Screenshot_4](Screenshot_4.png)
                    ![Screenshot_5](Screenshot_5.png)
                    ****
                    """

    val markdownNoScreenshots = """
## Histórico de versões

#### Versão 0.3

- Lista de contatos ordenada alfabeticamente
- Contados agrupados pela inicial do primeiro nome

#### Versão 0.21

Refatoração do app para melhor implementação dos princípios de Clean Architecture.

#### Versão 0.2

Adicionadas as funcionalidades:

- Busca de itens em tempo real
- Editar um cartão
                    """

    @Test
    fun dadoUmaStringDeTexto_deveExtraiNomesDeArquivosDasScreenshots() {

        val setScreenshot = markdown.getScreenshotFileNamesAsList()
        val result = setOf("Screenshot_1.png", "Screenshot_2.png", "Screenshot_3.png", "Screenshot_4.png", "Screenshot_5.png")

        println(setScreenshot)
        println(result)

        assertTrue(setScreenshot == result)

    }

    @Test
    fun dadoUmaStringDeTextoSemScreenshots_DeveRetornarUmGrupoVazio() {

        val setScreenshot = markdownNoScreenshots.getScreenshotFileNamesAsList()
        val result = emptySet<String>()

        println(setScreenshot)
        println(result)

        assertTrue(setScreenshot == result)


    }

}