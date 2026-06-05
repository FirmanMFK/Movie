package com.firman.movie.core.di

import com.firman.movie.core.datastore.SettingsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { SettingsRepository(androidContext()) }
}
