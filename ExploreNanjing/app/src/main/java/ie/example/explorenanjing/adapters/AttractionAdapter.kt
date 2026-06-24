package ie.example.explorenanjing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.example.explorenanjing.databinding.CardAttractionBinding
import ie.example.explorenanjing.models.AttractionModel

class AttractionAdapter(
    private var items: List<AttractionModel>,
    private val listener: AttractionListener
) : RecyclerView.Adapter<AttractionAdapter.MainHolder>() {

    interface AttractionListener {
        fun onAttractionClick(attractionId: Long)
        fun onFavoriteToggle(attractionId: Long, isFav: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardAttractionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class MainHolder(private val binding: CardAttractionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: AttractionModel) {
            binding.title.text = attraction.name
            binding.ratingBar.rating = attraction.rating
            Glide.with(itemView.context).load(attraction.imageUrl).into(binding.image)
            binding.root.setOnClickListener { listener.onAttractionClick(attraction.id) }
            binding.btnFav.isSelected = false // 初始化，根据store设置
            binding.btnFav.setOnClickListener {
                val isFav = !it.isSelected
                it.isSelected = isFav
                listener.onFavoriteToggle(attraction.id, isFav)
            }
        }
    }
}