package com.example.planificardecomidas.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificardecomidas.models.*

class RecipeViewModel : ViewModel() {

    val recetas = mutableStateListOf<Recipe>()
    val planSemanal = mutableStateMapOf(
        "Lunes"     to null as Recipe?,
        "Martes"    to null,
        "Miércoles" to null,
        "Jueves"    to null,
        "Viernes"   to null,
        "Sábado"    to null,
        "Domingo"   to null
    )
    val checkedIngredients = mutableStateMapOf<String, Boolean>()

    fun addReceta(recipe: Recipe) {
        recetas.add(recipe)
    }

    fun asignarReceta(day: String, receta: Recipe?) {
        planSemanal[day] = receta
    }

    fun toggleIngredient(name: String) {
        checkedIngredients[name] = !(checkedIngredients[name] ?: false)
    }

    fun getShoppingList(): List<Ingredient> {
        val result = mutableMapOf<String, Ingredient>()

        planSemanal.values.filterNotNull().forEach { recipe ->
            recipe.ingredients.forEach { ing ->
                val existing = result[ing.name]
                if (existing != null) {
                    val cantActual = existing.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0
                    val cantNueva = ing.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0
                    val unit = existing.quantity.filter { !it.isDigit() }.trim()
                    result[ing.name] = existing.copy(quantity = "${cantActual + cantNueva} $unit".trim())
                } else {
                    result[ing.name] = ing.copy()
                }
            }
        }
        return result.values.toList()
    }
}