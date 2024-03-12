package com.example.a7minutesworkout.Dataclass


// Getter and Setter
class ExerciseModel(
    private var id: Int,
    private var name: String,
    private var image: Int,
    private var isCompleted: Boolean,
    private var isSelected: Boolean
) {
    fun getId(): Int {
        return id
    }

    fun setId(pId: Int) {
        this.id = pId
    }

    fun getName(): String {
        return name
    }

    fun setName(pName: String) {
        this.name = pName
    }

    fun getImage(): Int {
        return image
    }

    fun setImage(pImage: Int) {
        this.image = pImage
    }

    fun getIsCompleted(): Boolean {
        return isCompleted
    }

    fun setIsCompleted(pIsCompleted: Boolean) {
        this.isCompleted = pIsCompleted
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(pIsSelected: Boolean) {
        this.isSelected = pIsSelected
    }
}