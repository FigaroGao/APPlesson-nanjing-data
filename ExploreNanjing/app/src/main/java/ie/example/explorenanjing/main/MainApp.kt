package ie.example.explorenanjing.main

import android.app.Application
import ie.example.explorenanjing.store.AttractionApiStore
import ie.example.explorenanjing.store.FavoriteHistoryStore
import ie.example.explorenanjing.store.JsonCommentStore
import timber.log.Timber

class MainApp : Application() {
    lateinit var attractionStore: ie.example.explorenanjing.store.AttractionStore
    lateinit var commentStore: ie.example.explorenanjing.store.CommentStore
    lateinit var favHistoryStore: ie.example.explorenanjing.store.FavoriteHistoryStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        // Use API Store (replace with your base URL)
        attractionStore = AttractionApiStore("https://my-json-server.typicode.com/yourusername/yourrepo/") // 示例，替换

        commentStore = JsonCommentStore(applicationContext)
        favHistoryStore = FavoriteHistoryStore(applicationContext)
        Timber.i("ExploreNanjing App started")
    }
}