package br.com.dio.app.repositories.presentation.binding

import br.com.dio.app.repositories.R

/**
 * Mantém uma lista de cores para os Chips; poderia ser uma enum, mas
 * preferi fazer como um Map<K, V> para ficar mais simples. O modificador
 * "internal" indica que esse mapa só é acessível por classes dentro
 * do mesmo pacote.
 * Novas cores e linguagens devem ser adicionadas nesse map!
 */
internal val chipColorMap: Map<String, Int> = mapOf(
    Pair("Java", R.color.java_yellow),
    Pair("Python", R.color.python_blue),
    Pair("Kotlin", R.color.kotlin_purple),
    Pair("Html", R.color.html_orange),
    Pair("Other", R.color.other_green)
)