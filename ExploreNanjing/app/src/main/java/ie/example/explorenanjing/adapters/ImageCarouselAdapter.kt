package ie.example.explorenanjing.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ie.example.explorenanjing.databinding.ItemCarouselImageBinding

class ImageCarouselAdapter(
    private val context: Context,
    private val imageUrls: List<String>
) : RecyclerView.Adapter<ImageCarouselAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCarouselImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Glide.with(context).load(imageUrls[position]).into(holder.binding.image)
    }

    override fun getItemCount(): Int = imageUrls.size

    inner class Holder(val binding: ItemCarouselImageBinding) : RecyclerView.ViewHolder(binding.root)
}