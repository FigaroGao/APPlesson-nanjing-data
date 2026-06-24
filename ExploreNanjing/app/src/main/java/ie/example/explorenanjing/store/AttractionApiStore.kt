package ie.example.explorenanjing.store

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ie.example.explorenanjing.models.AttractionModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

interface AttractionApi {
    @GET("attractions") // 替换为你的endpoint
    suspend fun getAll(): List<AttractionModel>
}

class AttractionApiStore(baseUrl: String) : AttractionStore {
    private val api: AttractionApi
    private val cache = mutableMapOf<Long, AttractionModel>() // 简单缓存

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AttractionApi::class.java)
    }

    override suspend fun findAll(): List<AttractionModel> = withContext(Dispatchers.IO) {
        try {
            val data = api.getAll()
            data.forEach { cache[it.id] = it }
            data
        } catch (e: Exception) {
            Timber.e(e, "API fetch failed")
            emptyList()
        }
    }

    override fun findById(id: Long): AttractionModel? = cache[id]

    // create/update/delete: 如果API支持，实现；否则stub
    override fun create(attraction: AttractionModel) {}
    override fun update(attraction: AttractionModel) {}
    override fun delete(attraction: AttractionModel) {}
}