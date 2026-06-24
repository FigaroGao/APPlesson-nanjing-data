package ie.example.explorenanjing.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionModel(
    var id: Long = 0L,
    var name: String = "",
    var description: String = "",
    var address: String = "",
    var openingHours: String = "",
    var level: String = "",
    var priceInfo: String = "",
    var imageUrl: String = "",
    var rating: Float = 0f,
    var reservationUrl: String = "" // 添加网址
) : Parcelable