package ie.example.explorenanjing.store

import ie.example.explorenanjing.models.AttractionModel

interface AttractionStore {
    suspend fun findAll(): List<AttractionModel>
    fun findById(id: Long): AttractionModel?
    fun create(attraction: AttractionModel)
    fun update(attraction: AttractionModel)
    fun delete(attraction: AttractionModel)
}