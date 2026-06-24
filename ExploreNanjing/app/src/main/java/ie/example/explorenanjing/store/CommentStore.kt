package ie.example.explorenanjing.store

import ie.example.explorenanjing.models.CommentModel

interface CommentStore {
    fun findForAttraction(attractionId: Long): List<CommentModel>
    fun create(comment: CommentModel)
    fun delete(comment: CommentModel)
    fun findAll(): List<CommentModel>
}