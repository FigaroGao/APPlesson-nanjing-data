package ie.example.explorenanjing.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import java.io.File

class FavoriteHistoryStore(private val context: Context) {
    private val gson = Gson()
    private val favFileName = "favorites.json"
    private val historyFileName = "history.json"
    private val favorites = mutableSetOf<Long>()
    private val history = mutableListOf<Long>() // LinkedList-like for order

    init {
        loadFavorites()
        loadHistory()
    }

    private fun favFile(): File = File(context.filesDir, favFileName)
    private fun historyFile(): File = File(context.filesDir, historyFileName)

    private fun loadFavorites() {
        try {
            val f = favFile()
            if (f.exists()) {
                val text = f.readText()
                val type = object : TypeToken<Set<Long>>() {}.type
                favorites.addAll(gson.fromJson(text, type) ?: emptySet())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun loadHistory() {
        try {
            val f = historyFile()
            if (f.exists()) {
                val text = f.readText()
                val type = object : TypeToken<List<Long>>() {}.type
                history.addAll(gson.fromJson(text, type) ?: emptyList())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun saveFavorites() {
        try {
            favFile().writeText(gson.toJson(favorites))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun saveHistory() {
        try {
            historyFile().writeText(gson.toJson(history))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun addToHistory(id: Long) {
        if (history.contains(id)) history.remove(id)
        history.add(0, id) // 添加到开头，按最近顺序
        saveHistory()
    }

    fun getHistory(): List<Long> = history

    fun toggleFavorite(id: Long): Boolean {
        return if (favorites.contains(id)) {
            favorites.remove(id)
            saveFavorites()
            false
        } else {
            favorites.add(id)
            saveFavorites()
            true
        }
    }

    fun getFavorites(): Set<Long> = favorites
}