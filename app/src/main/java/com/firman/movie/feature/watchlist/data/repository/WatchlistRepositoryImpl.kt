package com.firman.movie.feature.watchlist.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.firman.movie.feature.browse.domain.model.Movie
import com.firman.movie.feature.watchlist.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private val Context.watchlistDataStore: DataStore<Preferences> by preferencesDataStore(name = "watchlist")

class WatchlistRepositoryImpl(private val context: Context) : WatchlistRepository {

    private val WATCHLIST_KEY = stringPreferencesKey("watchlist_movies")

    override fun getWatchlist(): Flow<List<Movie>> {
        return context.watchlistDataStore.data.map { preferences ->
            val jsonString = preferences[WATCHLIST_KEY] ?: "[]"
            try {
                Json.decodeFromString<List<Movie>>(jsonString)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override fun getBookmarkedIds(): Flow<Set<Int>> {
        return getWatchlist().map { movies -> movies.map { it.id }.toSet() }
    }

    override suspend fun toggleWatchlist(movie: Movie) {
        context.watchlistDataStore.edit { preferences ->
            val jsonString = preferences[WATCHLIST_KEY] ?: "[]"
            val currentList = try {
                Json.decodeFromString<List<Movie>>(jsonString).toMutableList()
            } catch (e: Exception) {
                mutableListOf()
            }

            val existingMovie = currentList.find { it.id == movie.id }
            if (existingMovie != null) {
                currentList.remove(existingMovie)
            } else {
                currentList.add(movie)
            }

            preferences[WATCHLIST_KEY] = Json.encodeToString(currentList)
        }
    }
}
