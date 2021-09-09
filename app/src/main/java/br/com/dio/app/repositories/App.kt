package br.com.dio.app.repositories

import android.app.Application
import br.com.dio.app.repositories.data.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        /**
         * Invoca a função load() do DataModule para instanciar os
         * serviços web
         */
        DataModule.load()

    }
}