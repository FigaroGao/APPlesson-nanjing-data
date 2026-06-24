package ie.example.explorenanjing.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ie.example.explorenanjing.adapters.ImageCarouselAdapter
import ie.example.explorenanjing.databinding.ActivityLoginBinding
import ie.example.explorenanjing.main.MainApp

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var app: MainApp
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var carouselRunnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp

        // 背景轮播 (假设从attractionStore获取图片URL，或硬编码示例)
        val imageUrls = listOf("https://example.com/img1.jpg", "https://example.com/img2.jpg") // 从API动态获取
        binding.viewPager.adapter = ImageCarouselAdapter(this, imageUrls)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        carouselRunnable = Runnable {
            binding.viewPager.currentItem = (binding.viewPager.currentItem + 1) % imageUrls.size
            handler.postDelayed(carouselRunnable, 3000) // 每3秒轮播
        }
        handler.postDelayed(carouselRunnable, 3000)

        binding.btnVisitor.setOnClickListener {
            startActivity(Intent(this, AttractionListActivity::class.java))
        }

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, AttractionListActivity::class.java))
        }

        binding.btnGoogle.setOnClickListener {
            startActivity(Intent(this, AttractionListActivity::class.java))
        }

        binding.btnApple.setOnClickListener {
            startActivity(Intent(this, AttractionListActivity::class.java))
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(carouselRunnable)
        super.onDestroy()
    }
}