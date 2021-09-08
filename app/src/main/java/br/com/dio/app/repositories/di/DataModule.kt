package br.com.dio.app.repositories.di

import android.util.Log
import br.com.dio.app.repositories.data.services.GithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Essa object class é reponsável por instanciar e configurar os serviços web.
 * Ela tem duas responsabilidades: configurar os serviços web (OkHttp e Retrofit)
 * e fazer a injeçãoo das dependências por meio do Koin.
 * Eventualmente posso vir a separar as responsabilidades, dependendo das condições
 * de configuração de ViewModels e Fragments
 */

object DataModule {

    private const val OK_HTTP = " Ok Http"
    private const val BASE_URL = "https://api.github.com"

    /**
     * Essa função fica exposta publicamente e é chamada na classe App
     */
    fun load() {
        loadKoinModules(networkModule())
    }

    /**
     * Cria um módulo de rede com interceptação
     */
    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            /**
             * Usando a bibliteca Moshi para parsear o JSON. Também poderia
             * ser o Gson.
             */
            single {
                Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            }

            /**
             * Finalmente, cria um objeto Retrofit usando o OkHttpClient
             * e a factory de conversão Json
             */
            single {
                createService<GithubService>(get(), get())
            }

        }


    }

    /**
     * Essa função instancia um objeto Retrofit a partir dos
     * parâmetros recebidos via construtor: o cliente OkHttp (por causa
     * do interceptor) e o conversor de Json.
     */
    private inline fun <reified T> createService(client: OkHttpClient, factory: Moshi): T {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(T::class.java)

    }

}