
package br.com.dio.app.repositories.data.services

import br.com.dio.app.repositories.data.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    /**
     * Um hack rápido para retornar mais repos por consulta; 100 é o limite
     * máximo de resultados por página que a API permite. O ideal é trabalhar com
     * queries paginadas conforme o usuário rola.
     */
    @GET("users/{user}/repos?per_page=100")
    suspend fun listRepositories(@Path("user") user:String) : List<Repo>

}