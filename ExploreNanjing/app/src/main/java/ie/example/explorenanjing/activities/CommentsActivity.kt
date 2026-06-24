package ie.example.explorenanjing.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.button.MaterialButtonToggleGroup
import ie.example.explorenanjing.adapters.CommentAdapter
import ie.example.explorenanjing.databinding.ActivityCommentsBinding
import ie.example.explorenanjing.main.MainApp
import ie.example.explorenanjing.models.CommentModel

class CommentsActivity : AppCompatActivity(), CommentAdapter.CommentListener {
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var app: MainApp
    private var attractionId: Long = 0L
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        attractionId = intent.getLongExtra("attractionId", 0L)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // 类别过滤按钮 (ToggleGroup)
        binding.toggleGroup.addOnButtonCheckedListener { _: MaterialButtonToggleGroup, checkedId: Int, isChecked: Boolean ->
            if (isChecked) {
                selectedCategory = when (checkedId) {
                    binding.btnExcellent.id -> "Excellent"
                    binding.btnGood.id -> "Good"
                    binding.btnOrdinary.id -> "Ordinary"
                    binding.btnBad.id -> "Bad"
                    else -> null
                }
                refreshComments()
            }
        }

        binding.btnAddComment.setOnClickListener {
            val text = binding.editComment.text.toString()
            val cat = selectedCategory ?: "Ordinary" // 默认
            val c = CommentModel(attractionId = attractionId, ratingCategory = cat, text = text)
            app.commentStore.create(c)
            refreshComments()
            binding.editComment.setText("")
        }

        refreshComments()
    }

    private fun refreshComments() {
        var data = app.commentStore.findForAttraction(attractionId)
        selectedCategory?.let { cat ->
            data = data.filter { it.ratingCategory == cat }
        }
        binding.recyclerView.adapter = CommentAdapter(data, this)
    }

    override fun onDeleteComment(comment: CommentModel) {
        if (comment.userName == "Visitor") { // 只删自己的
            app.commentStore.delete(comment)
            refreshComments()
        }
    }
}