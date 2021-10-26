package br.com.dio.app.repositories.data.services

import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    /**
     * Esse endpoint acessa a lista de repositórios do usuário. Retorna a
     * quantidade padrão de 30 resultados por página.
     * O valor null para o parâmetro sorting é seguro e não
     * causa erros na API.
     */
    @GET("users/{user}/repos?sort=sorting")
    suspend fun listRepositories(
        @Path("user") user: String,
        @Query("sort") sorting: String?
    ): List<Repo>

    /**
     * Esse endpoint acessa as informações básicas do usuário. Optei por manter a separação
     * entre as classes User e Owner para evitar retrabalhos na classe Repo.
     */
    @GET("users/{username}")
    suspend fun getUser(@Path("username") user: String): User

    /**
     * Esse endpoint recebe dois parâmetros - nome de usuario e nome do Repo - e retorna
     * um único Repo.
     */
    @GET("repos/{owner}/{repo}")
    suspend fun getRepo(
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Repo


}