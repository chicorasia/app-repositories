package br.com.dio.app.repositories.presentation


/**
 * Esse arquivo mantém funções auxiliares e utilitárias de apoio ao DetailFragment
 */

/**
 * Essa função utilitária extrai nomes de arquivos PNG de uma string de texto.
 * Usa uma expressão regular para encontrar os nomes dos arquivos em uma String markdown.
 * Retorna os resultados como um Set<String> porque  não quero ter itens repetidos.
 * Estou me valendo da implementação padrão LinkedHashSet para preservar a ordem
 * de inserção dos elementos no Set.
 */
internal fun String.getScreenshotFileNamesAsList() : Set<String> {

    val regex = Regex("([a-zA-Z0-9_&%$#@-])*.png|([a-zA-Z0-9_&%\$#@-])*.jpg")
    val matches = regex.findAll(this)
    return matches.map {
        it.value
    }.toSet()
}