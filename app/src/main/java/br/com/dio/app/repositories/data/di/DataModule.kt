package br.com.dio.app.repositories.data.di

import android.util.Log
import br.com.dio.app.repositories.data.repo.RepoInfo
import br.com.dio.app.repositories.data.repo.RepoInfoImpl
import br.com.dio.app.repositories.data.repositories.RepoRepository
import br.com.dio.app.repositories.data.repositories.RepoRepositoryImpl
import br.com.dio.app.repositories.data.services.GithubService
import br.com.dio.app.repositories.data.user.UserInfo
import br.com.dio.app.repositories.data.user.UserInfoImpl
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
 * A filosofia adotada é agrupar os módulos por camadas.
 */

object DataModule {

    private const val OK_HTTP = "Ok Http"
    private const val BASE_URL = "https://api.github.com"

    /**
     * Essa função fica exposta publicamente e é chamada na classe App.
     * Note o uso de concatenação para juntar várias listas de módulos em
     * uma única chamada. Isso evita duplicação de código na classe App.
     */
    fun load() {
        loadKoinModules(networkModule() + repositoriesModule()
                + userModule() + repoModule())
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
     * Esse método instancia uma RepoRepositoryImpl indicando sua dependência
     */
    private fun repositoriesModule() : Module {
        return module {
            single<RepoRepository> { RepoRepositoryImpl(get()) }
        }
    }

    /**
     * Esse método instancia o UserInforImpl; aqui pode ser usada uma factory
     * normal - não vejo necessidade de tê-lo como um singleton.
     */
    private fun userModule() : Module {
        return module {
            factory<UserInfo> { UserInfoImpl(get()) }
        }
    }


    /**
     * Esse método é responsável por instanciar um RepoInfoImpl; como no
     * caso do UserInfo, preferi usar uma factory porque faz sentido ter um singleton.
     */
    private fun repoModule() : Module {
        return module {
            factory<RepoInfo> { RepoInfoImpl(get()) }
        }
    }

    /**
     * Essa função instancia um objeto Retrofit a partir dos
     * parâmetros recebidos via construtor: o cliente OkHttp (por causa
     * do interceptor) e o conversor de Json.
     */
    private inline fun <reified T> createService(client: OkHttpClient, factory: Moshi): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(T::class.java)

    }


}