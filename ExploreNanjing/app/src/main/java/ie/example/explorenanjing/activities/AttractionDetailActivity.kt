package ie.example.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import ie.example.explorenanjing.databinding.ActivityAttractionDetailBinding
import ie.example.explorenanjing.main.MainApp
import ie.example.explorenanjing.models.AttractionModel

class AttractionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttractionDetailBinding
    private lateinit var app: MainApp
    private var attraction: AttractionModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttractionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        attraction = intent.getParcelableExtra("attraction")
        attraction?.let { showData(it) }

        binding.btnComment.setOnClickListener {
            val i = Intent(this, CommentsActivity::class.java)
            i.putExtra("attractionId", attraction?.id ?: 0L)
            startActivity(i)
        }
    }

    private fun showData(a: AttractionModel) {
        binding.title.text = a.name
        binding.addressValue.text = a.address
        binding.openingValue.text = a.openingHours
        binding.levelValue.text = a.level
        binding.priceValue.text = a.priceInfo
        binding.descriptionValue.text = a.description
        binding.ratingBar.rating = a.rating

        // 背景图片
        Glide.with(this).load(a.imageUrl).into(binding.backgroundImage)

        // 网址链接
        binding.reservationValue.movementMethod = LinkMovementMethod.getInstance()
        binding.reservationValue.text = Html.fromHtml("<a href='${a.reservationUrl}'>Reservation</a>")
    }
}