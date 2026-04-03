package com.example.planificardecomidas.models

data class Recipe(
    val name: String,
    val ingredients: MutableList<Ingredient>
)