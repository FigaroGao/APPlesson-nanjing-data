package ie.example.explorenanjing.store

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ie.example.explorenanjing.models.CommentModel
import timber.log.Timber
import java.io.File

class JsonCommentStore(private val context: Context) : CommentStore {
    private val gson = Gson()
    private val fileName = "comments.json"
    private val comments = ArrayList<CommentModel>()
    private var lastId = 0L

    init {
        load()
    }

    private fun file(): File = File(context.filesDir, fileName)

    private fun load() {
        try {
            val f = file()
            if (f.exists()) {
                val text = f.readText()
                val type = object : TypeToken<List<CommentModel>>() {}.type
                val list: List<CommentModel> = gson.fromJson(text, type) ?: emptyList()
                comments.clear()
                comments.addAll(list)
                lastId = comments.map { it.id }.maxOrNull() ?: 0L
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun save() {
        try {
            val f = file()
            f.writeText(gson.toJson(comments))
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override fun findForAttraction(attractionId: Long): List<CommentModel> =
        comments.filter { it.attractionId == attractionId }.sortedByDescending { it.timestamp }

    override fun findAll(): List<CommentModel> = comments

    override fun create(comment: CommentModel) {
        comment.id = ++lastId
        comments.add(comment)
        save()
        Timber.i("Comment saved for attraction ${comment.attractionId}")
    }

    override fun delete(comment: CommentModel) {
        comments.removeIf { it.id == comment.id }
        save()
    }
}