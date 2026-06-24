package ie.example.explorenanjing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.example.explorenanjing.databinding.ItemCommentBinding
import ie.example.explorenanjing.models.CommentModel
import java.text.SimpleDateFormat
import java.util.Date

class CommentAdapter(
    private var items: List<CommentModel>,
    private val listener: CommentListener
) : RecyclerView.Adapter<CommentAdapter.MainHolder>() {

    interface CommentListener {
        fun onDeleteComment(comment: CommentModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class MainHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentModel) {
            binding.userName.text = comment.userName
            binding.timestamp.text = SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(comment.timestamp))
            binding.commentText.text = comment.text
            if (comment.imageUrl.isNotEmpty()) {
                Glide.with(itemView.context).load(comment.imageUrl).into(binding.commentImage)
            }
            binding.root.setOnLongClickListener {
                listener.onDeleteComment(comment)
                true
            }
        }
    }
}