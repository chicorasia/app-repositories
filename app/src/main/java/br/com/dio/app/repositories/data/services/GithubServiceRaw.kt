package br.com.dio.app.repositories.data.services

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Essa interface define um serviço que acessa o conteúdo "raw" do usuário
 * no Github. A URL base é diferente - raw.githubusercontent.com.
 * É usada para acessar diretamente o README.md (e provavelmente também
 * as screenshots...)
 *
 */
interface GithubServiceRaw {

    /**
     * Esse endpoint recupera o arquivo README.md
     */
    @GET("{owner}/{repo}/{branch}/README.md")
    suspend fun getRepoReadme(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("branch") defaultBranch: String
    ): String?

}