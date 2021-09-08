package br.com.dio.app.repositories.data.services

import br.com.dio.app.repositories.domain.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/repos")
    suspend fun listRepositories(@Path("user") user:String) : List<Repo>

}
