package com.example.planificardecomidas.models

data class Ingredient(
    val name: String,
    val quantity: String,
    var checked: Boolean = false
)