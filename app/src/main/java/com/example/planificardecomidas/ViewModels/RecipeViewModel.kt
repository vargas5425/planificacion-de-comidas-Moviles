package com.example.planificardecomidas.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.planificardecomidas.models.*

class RecipeViewModel : ViewModel() {

    var recetas = mutableStateListOf<Recipe>()

    var planSemanal = mutableMapOf(
        "Lunes" to null as Recipe?,
        "Martes" to null,
        "Miércoles" to null,
        "Jueves" to null,
        "Viernes" to null,
        "Sábado" to null,
        "Domingo" to null
    )

    fun addReceta(recipe: Recipe) {
        recetas.add(recipe)
    }

    fun asignarReceta(day: String, receta: Recipe) {
        planSemanal[day] = receta
    }

    fun getShoppingList(): List<Ingredient> {

        val result = mutableMapOf<String, Ingredient>()
        planSemanal.values.filterNotNull().forEach { recipe ->

            recipe.ingredients.forEach { ing ->

                val existing = result[ing.name]

                if (existing != null) {

                    // sumar cantidades (solo números)
                    val cantidadActual = existing.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0
                    val nuevaCantidad = ing.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0

                    val unit = existing.quantity.filter { !it.isDigit() }

                    val total = cantidadActual + nuevaCantidad

                    result[ing.name] = Ingredient(
                        name = ing.name,
                        quantity = "$total $unit".trim()
                    )

                } else {
                    result[ing.name] = Ingredient(
                        name = ing.name,
                        quantity = ing.quantity
                    )
                }
            }
        }

        return result.values.toList()
    }
}