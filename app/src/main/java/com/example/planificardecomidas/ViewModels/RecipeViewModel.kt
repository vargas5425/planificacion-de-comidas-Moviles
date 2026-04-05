package com.example.planificardecomidas.ViewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.example.planificardecomidas.models.*

class RecipeViewModel : ViewModel() {

    var recetas = mutableStateListOf<Recipe>()
    private set

    var planSemanal = mutableStateMapOf(
        "Lunes" to null as Recipe?,
        "Martes" to null,
        "Miércoles" to null,
        "Jueves"    to null,
        "Viernes"   to null,
        "Sábado"    to null,
        "Domingo"   to null
    )
        private set

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

        val cantidades = mutableMapOf<String, Int>()
        val unidades = mutableMapOf<String, String>()

        planSemanal.values.filterNotNull()
            .forEach { receta ->
                receta.ingredients.forEach { ingrediente ->
                    val nombre = ingrediente.name
                    val numero = ingrediente.quantity.filter { it.isDigit() }.toIntOrNull() ?: 0
                    val unidad = ingrediente.quantity.filter { !it.isDigit() }.trim()

                    cantidades[nombre] = (cantidades[nombre] ?: 0) + numero
                    unidades[nombre] = unidad
                }
            }

        return cantidades.map { (nombre, cantidad) ->
            Ingredient(
                name = nombre,
                quantity = "$cantidad ${unidades[nombre]}".trim()
            )
        }
    }
}