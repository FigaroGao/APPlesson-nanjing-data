package ie.example.explorenanjing.models

data class CommentModel(
    var id: Long = 0L,
    var attractionId: Long = 0L,
    var userName: String = "Visitor",
    var ratingCategory: String = "", // Excellent/Good/Ordinary/Bad
    var text: String = "",
    var timestamp: Long = System.currentTimeMillis(),
    var imageUrl: String = "" // 可选评论图片
)