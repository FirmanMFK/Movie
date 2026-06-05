package com.firman.movie

import android.app.Application
import com.firman.movie.core.di.coreModule
import com.firman.movie.core.network.networkModule
import com.firman.movie.feature.browse.di.browseModule
import com.firman.movie.feature.detail.di.detailModule
import com.firman.movie.feature.watchlist.di.watchlistModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MovieApplication)
            modules(
                coreModule,
                networkModule,
                browseModule,
                watchlistModule,
                detailModule
            )
        }
    }
}
