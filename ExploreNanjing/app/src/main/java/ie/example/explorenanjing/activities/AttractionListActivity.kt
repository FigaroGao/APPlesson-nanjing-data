package ie.example.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ie.example.explorenanjing.adapters.AttractionAdapter
import ie.example.explorenanjing.databinding.ActivityAttractionListBinding
import ie.example.explorenanjing.main.MainApp
import ie.example.explorenanjing.models.AttractionModel
import timber.log.Timber
import kotlinx.coroutines.launch

class AttractionListActivity : AppCompatActivity(), AttractionAdapter.AttractionListener {
    private lateinit var binding: ActivityAttractionListBinding
    private lateinit var app: MainApp
    private var currentData: List<AttractionModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttractionListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        refreshList()

        binding.btnFavorites.setOnClickListener { showFavorites() }
        binding.btnHistory.setOnClickListener { showHistory() }
        binding.btnComments.setOnClickListener {
            // 可选: 全局评论或stub
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText ?: "")
                return true
            }
        })
    }

    private fun refreshList() {
        lifecycleScope.launch {
            currentData = app.attractionStore.findAll()
            binding.recyclerView.adapter = AttractionAdapter(currentData, this@AttractionListActivity)
        }
    }

    private fun filterList(q: String) {
        val filtered = currentData.filter { it.name.contains(q, true) || it.description.contains(q, true) }
        binding.recyclerView.adapter = AttractionAdapter(filtered, this)
    }

    private fun showFavorites() {
        val favIds = app.favHistoryStore.getFavorites()
        val favData = currentData.filter { favIds.contains(it.id) }
        binding.recyclerView.adapter = AttractionAdapter(favData, this)
    }

    private fun showHistory() {
        val historyIds = app.favHistoryStore.getHistory()
        val historyData = historyIds.mapNotNull { app.attractionStore.findById(it) } // 按顺序
        binding.recyclerView.adapter = AttractionAdapter(historyData, this)
    }

    override fun onAttractionClick(attractionId: Long) {
        app.favHistoryStore.addToHistory(attractionId)
        val i = Intent(this, AttractionDetailActivity::class.java)
        val attraction = app.attractionStore.findById(attractionId)
        i.putExtra("attraction", attraction)
        startActivity(i)
    }

    override fun onFavoriteToggle(attractionId: Long, isFav: Boolean) {
        app.favHistoryStore.toggleFavorite(attractionId)
        Timber.i("Fav toggled $attractionId -> $isFav")
        // 可选: 刷新列表如果在fav模式
    }
}